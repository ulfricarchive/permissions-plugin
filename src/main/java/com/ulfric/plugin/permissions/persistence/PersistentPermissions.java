package com.ulfric.plugin.permissions.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.ulfric.commons.permissions.limit.IntegerLimit;
import com.ulfric.commons.permissions.limit.StandardLimits;
import com.ulfric.commons.permissions.node.Allowance;
import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.dragoon.rethink.Instance;
import com.ulfric.dragoon.rethink.Location;
import com.ulfric.dragoon.rethink.response.Response;
import com.ulfric.plugin.entities.Entity;
import com.ulfric.plugin.entities.components.name.NameComponent;
import com.ulfric.plugin.permissions.Group;
import com.ulfric.plugin.permissions.PermissionsService;
import com.ulfric.plugin.permissions.User;
import com.ulfric.plugin.permissions.persistence.component.limits.Limit;
import com.ulfric.plugin.permissions.persistence.component.limits.LimitsComponent;
import com.ulfric.plugin.permissions.persistence.component.parents.ParentsComponent;
import com.ulfric.plugin.permissions.persistence.component.permissions.Permission;
import com.ulfric.plugin.permissions.persistence.component.permissions.PermissionsComponent;
import com.ulfric.plugin.permissions.persistence.groups.GroupSystem;
import com.ulfric.plugin.permissions.persistence.users.UserSystem;

public class PersistentPermissions implements PermissionsService {

	private final Map<UUID, User> userCache = new HashMap<>();
	private final Map<String, Group> groupCache = new HashMap<>();

	@Inject
	private UserSystem users;

	@Inject
	private GroupSystem groups;

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
		return users.createEntity(uniqueId)
			.thenApply(instance -> {
				Entity document = instance.get();
				if (document != null) {
					loadEntity(document, user);
				}
				addListener(instance, user);
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
		return groups.createEntity(name)
			.thenApply(instance -> {
				Entity document = instance.get();
				if (document != null) {
					loadEntity(document, group);
				}
				addListener(instance, group);
				return group;
			});
	}

	private void addListener(Instance<Entity> instance, com.ulfric.commons.permissions.entity.Entity permissible) {
		instance.addListener(newDocument -> loadEntity(newDocument, permissible));
	}

	private void loadEntity(Entity document, com.ulfric.commons.permissions.entity.Entity permissible) {
		loadParents(document, permissible);
		loadPermissions(document, permissible);
		loadLimits(document, permissible);
	}

	private void loadParents(Entity document, com.ulfric.commons.permissions.entity.Entity permissible) {
		ParentsComponent parentsDocument = document.getComponent(ParentsComponent.KEY);
		if (parentsDocument != null) {
			parentsDocument.parentsAsGroups(this).forEach(permissible::addParent);
		}
	}

	private void loadPermissions(Entity document, com.ulfric.commons.permissions.entity.Entity permissible) {
		PermissionsComponent permissionsDocument = document.getComponent(PermissionsComponent.KEY);
		if (permissionsDocument != null) {
			permissionsDocument.asAllowances().forEach(permissible::setPermission);
		}
	}

	private void loadLimits(Entity document, com.ulfric.commons.permissions.entity.Entity permissible) {
		LimitsComponent limitsDocument = document.getComponent(LimitsComponent.KEY);
		if (limitsDocument != null) {
			limitsDocument.asPermissionLimits().forEach(permissible::setLimit);
		}
	}

	@Override
	public CompletableFuture<Response> persistUser(User user) {
		return users.persist(toDocument(user));
	}

	@Override
	public CompletableFuture<Response> persistGroup(Group group) {
		return users.persist(toDocument(group));
	}

	private Entity toDocument(com.ulfric.commons.permissions.entity.Entity entity) { // TODO cleanup
		Entity document = new Entity();

		String identifier = entity.getIdentifier();
		if (StringUtils.isNotEmpty(identifier)) {
			document.setLocation(Location.key(entity));

			NameComponent name = new NameComponent();
			name.setName(identifier);
			document.addComponent(name);
		}

		PermissionsComponent permissionsDocument = new PermissionsComponent();
		entity.getPermissions().forEach((node, allowance) -> {
			if (allowance == Allowance.UNDEFINED) {
				return;
			}

			Permission permission = new Permission();
			permission.setNode(node);
			permission.setAllowed(allowance != Allowance.DENIED);
			permissionsDocument.addPermission(permission);
		});
		document.addComponent(permissionsDocument);

		LimitsComponent limitsDocument = new LimitsComponent();
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

			limitsDocument.addLimit(limitBean);
		});
		document.addComponent(limitsDocument);

		ParentsComponent parentsDocument = new ParentsComponent();
		List<String> parents = entity.getParents()
			.stream()
			.filter(Objects::nonNull)
			.map(com.ulfric.commons.permissions.entity.Entity::getIdentifier)
			.filter(Objects::nonNull)
			.collect(Collectors.toList());
		parentsDocument.setParents(parents);
		document.addComponent(parentsDocument);

		return document;
	}

}
