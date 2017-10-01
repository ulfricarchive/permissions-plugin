package com.ulfric.plugin.permissions.entity;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.trie.PatriciaTrie;
import org.apache.commons.lang3.StringUtils;

import com.ulfric.commons.concurrent.FutureHelper;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.commons.permissions.entity.Group;
import com.ulfric.commons.permissions.limit.IntegerLimit;
import com.ulfric.commons.permissions.limit.Limit;
import com.ulfric.commons.permissions.limit.StandardLimits;
import com.ulfric.commons.permissions.node.Allowance;
import com.ulfric.dragoon.rethink.Instance;
import com.ulfric.dragoon.rethink.Response;
import com.ulfric.dragoon.rethink.Store;
import com.ulfric.plugin.permissions.PermissionsService;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

final class EntityPersistence {

	private final Store<PermissionEntityDocument> store;
	private final Instance<PermissionEntityDocument> document;
	private volatile boolean changedNodes;
	private volatile boolean changedLimits;
	private volatile boolean changedParents;
	private volatile boolean needsWrite;

	public EntityPersistence(Store<PermissionEntityDocument> store, Instance<PermissionEntityDocument> document) {
		this.store = store;
		this.document = document;
	}

	void changedNodes() {
		changedNodes = true;
	}

	void changedLimits() {
		changedLimits = true;
	}

	void changedParents() {
		changedParents = true;
	}

	public void resetState() {
		if (this.needsWrite) {
			// TODO try to prevent data loss in this scenario
			this.needsWrite = false;
		}

		this.changedLimits = false;
		this.changedNodes = false;
		this.changedParents = false;
	}

	public void loadNodes(PatriciaTrie<Allowance> permissions) {
		List<String> entries = document.get().getNodes();
		if (CollectionUtils.isEmpty(entries)) {
			return;
		}

		for (String entry : entries) {
			if (entry.isEmpty()) {
				continue;
			}
			boolean negative = entry.charAt(0) == '-';
			if (negative) {
				entry = entry.substring(1);
			}
			permissions.put(entry, negative ? Allowance.DENIED : Allowance.ALLOWED);
		}
	}

	public void loadLimits(PatriciaTrie<Limit> limits) {
		Map<String, String> entries = document.get().getLimits();
		if (MapUtils.isEmpty(entries)) {
			return;
		}

		entries.forEach((key, limitString) -> {
			if (key.isEmpty() || limitString.isEmpty()) {
				return;
			}

			Limit limit;
			boolean integer = limitString.startsWith("int/");
			if (integer) {
				limitString = limitString.substring("int/".length());
				int limitInt = Integer.parseInt(limitString);
				if (limitInt < 1) {
					limit = StandardLimits.NONE;
				} else {
					limit = IntegerLimit.of(Integer.parseInt(limitString));
				}
			} else {
				limit = StandardLimits.valueOf(limitString);
			}
			limits.put(key, limit);
		});
	}

	public void loadParents(Map<UUID, Entity> parents) {
		List<String> entries = document.get().getParents();
		if (CollectionUtils.isEmpty(entries)) {
			return;
		}

		PermissionsService service = PermissionsService.get();
		if (service == null) {
			return; // TODO better handling of this possible scenario
		}

		for (String entry : entries) {
			if (entry.isEmpty()) {
				continue;
			}
			if (entry.startsWith("group/")) {
				entry = entry.substring("group/".length());
			}
			Group group = service.createGroupByName(entry); // TODO validate not self
			if (group == null) {
				continue;
			}
			parents.put(group.getUniqueId(), group);
		}
	}

	public void saveNodes(PatriciaTrie<Allowance> permissions) {
		if (!changedNodes) {
			return;
		}
		changedNodes = false;
		needsWrite = true;

		if (permissions.isEmpty()) {
			document.get().setNodes(Collections.emptyList());
			return;
		}

		List<String> entries = permissions.entrySet().stream().map(entry -> {
			if (entry.getValue() == Allowance.DENIED) {
				return '-' + entry.getKey();
			}
			return entry.getKey();
		}).filter(StringUtils::isNotBlank).collect(Collectors.toList());

		document.get().setNodes(entries);
	}

	public void saveLimits(PatriciaTrie<Limit> limits) {
		if (!changedLimits) {
			return;
		}
		changedLimits = false;
		needsWrite = true;

		if (limits.isEmpty()) {
			document.get().setLimits(Collections.emptyMap());
			return;
		}

		Map<String, String> entries = new LinkedHashMap<>();
		limits.forEach((key, limit) -> {
			String value;
			if (limit instanceof IntegerLimit) {
				IntegerLimit integerLimit = (IntegerLimit) limit;
				value = "int/" + integerLimit.intValue();
			} else {
				value = limit.toString();
			}
			entries.put(key, value);
		});

		document.get().setLimits(entries);
	}

	public void saveParents(Map<UUID, Entity> parents) {
		if (!changedParents) {
			return;
		}
		changedParents = false;
		needsWrite = true;

		if (parents.isEmpty()) {
			document.get().setParents(Collections.emptyList());
			return;
		}

		List<String> entries = parents.values().stream().map(entry -> {
			if (entry instanceof Group) {
				return "group/" + entry.getName();
			}
			return null;
		}).filter(StringUtils::isNotBlank).collect(Collectors.toList());

		document.get().setParents(entries);
	}

	public CompletableFuture<Response> executeWrite() {
		if (!needsWrite) {
			return FutureHelper.emptyCompletableFuture();
		}

		CompletableFuture<Response> response = store.insert(document.get());
		response = response.whenComplete((success, error) -> {
			if (error != null) {
				error.printStackTrace(); // TODO better logging
				return;
			}

			needsWrite = false;
		});

		return response;
	}

}
