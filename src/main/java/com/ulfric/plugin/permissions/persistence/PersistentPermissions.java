package com.ulfric.plugin.permissions.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.commons.permissions.limit.IntegerLimit;
import com.ulfric.commons.permissions.limit.StandardLimits;
import com.ulfric.commons.permissions.node.Allowance;
import com.ulfric.dragoon.acrodb.Database;
import com.ulfric.dragoon.acrodb.Store;
import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.plugin.permissions.Group;
import com.ulfric.plugin.permissions.PermissionsService;
import com.ulfric.plugin.permissions.User;
import com.ulfric.plugin.permissions.persistence.limits.Limit;
import com.ulfric.plugin.permissions.persistence.limits.LimitHelper;
import com.ulfric.plugin.permissions.persistence.parents.ParentHelper;
import com.ulfric.plugin.permissions.persistence.permissions.Permission;
import com.ulfric.plugin.permissions.persistence.permissions.PermissionHelper;

public class PersistentPermissions implements PermissionsService {

	private final Map<UUID, User> userCache = new HashMap<>();
	private final Map<String, Group> groupCache = new HashMap<>();

	@Inject
	@Database({"permissions", "users"})
	private Store<PermissionPersistenceEntity> users;

	@Inject
	@Database({"permissions", "groups"})
	private Store<PermissionPersistenceEntity> groups;

	@Override
	public Class<PermissionsService> getService() {
		return PermissionsService.class;
	}

	@Override
	public CompletableFuture<User> getUserByUniqueId(UUID uniqueId) { // TODO thread safety when dealing with user/group cache
		User cached = userCache.get(uniqueId);
		if (cached != null) {
			return CompletableFuture.completedFuture(cached);
		}

		User user = new User(uniqueId);
		userCache.put(uniqueId, user);

		return users.getAsynchronous(uniqueId.toString())
				.thenApply(document -> {
					loadEntity(document, user);
					return user;
				});
	}

	@Override
	public CompletableFuture<Group> getGroupByName(String name) {
		Group cached = groupCache.get(name);
		if (cached != null) {
			return CompletableFuture.completedFuture(cached);
		}

		Group group = new Group(name);
		groupCache.put(name, group);
		return groups.getAsynchronous(name)
			.thenApply(document -> {
				loadEntity(document, group);
				return group;
			});
	}

	private void loadEntity(PermissionPersistenceEntity document, Entity permissible) {
		loadParents(document, permissible);
		loadPermissions(document, permissible);
		loadLimits(document, permissible);
	}

	private void loadParents(PermissionPersistenceEntity document, Entity permissible) {
		List<String> parents = document.getParents();
		ParentHelper.parentsAsGroups(this, parents)
			.forEach(permissible::addParent);
	}

	private void loadPermissions(PermissionPersistenceEntity document, Entity permissible) {
		List<Permission> permissionBeans = document.getPermissions();
		PermissionHelper.asAllowances(permissionBeans)
			.forEach(permissible::setPermission);
	}

	private void loadLimits(PermissionPersistenceEntity document, Entity permissible) {
		List<Limit> limitBeans = document.getLimits();
		LimitHelper.asPermissionLimits(limitBeans)
			.forEach(permissible::setLimit);
	}

	@Override
	public CompletableFuture<Void> persistUser(User user) {
		return users.persistAsynchronous(toDocument(user));
	}

	@Override
	public CompletableFuture<Void> persistGroup(Group group) {
		return groups.persistAsynchronous(toDocument(group));
	}

	private PermissionPersistenceEntity toDocument(Entity entity) {
		PermissionPersistenceEntity document = new PermissionPersistenceEntity();

		document.setIdentifier(entity.getIdentifier());
		document.setPermissions(getPermissionBeans(entity));
		document.setLimits(getLimitBeans(entity));
		document.setParents(getParents(entity));

		return document;
	}

	private List<Permission> getPermissionBeans(com.ulfric.commons.permissions.entity.Entity entity) {
		List<Permission> permissionBeans = new ArrayList<>();

		entity.getPermissions().forEach((node, allowance) -> {
			if (allowance == Allowance.UNDEFINED) {
				return;
			}

			Permission permission = new Permission();
			permission.setNode(node);
			permission.setAllowed(allowance != Allowance.DENIED);
			permissionBeans.add(permission);
		});

		return permissionBeans.isEmpty() ? null : permissionBeans;
	}

	private List<Limit> getLimitBeans(Entity entity) {
		List<Limit> limits = new ArrayList<>();

		entity.getLimits().forEach((node, limit) -> {
			Limit limitBean = new Limit();
			limitBean.setNode(node);

			if (limit.isWithinBounds(StandardLimits.UNLIMITED)) {
				limitBean.setUnlimited(true);
			} else if (limit == StandardLimits.NONE) {
				limitBean.setNone(true);
			} else if (limit instanceof IntegerLimit) {
				limitBean.setValue(((IntegerLimit) limit).intValue());
			}

			limits.add(limitBean);
		});

		return limits.isEmpty() ? null : limits;
	}

	private List<String> getParents(Entity entity) {
		List<String> parents = entity.getParents()
				.stream()
				.filter(Objects::nonNull)
				.map(Entity::getIdentifier)
				.filter(Objects::nonNull)
				.distinct()
				.collect(Collectors.toList());

		return parents.isEmpty() ? null : parents;
	}

}
