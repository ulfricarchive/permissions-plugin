package com.ulfric.plugin.permissions.entity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.ulfric.commons.permissions.entity.Group;
import com.ulfric.commons.permissions.entity.User;
import com.ulfric.dragoon.rethink.Database;
import com.ulfric.dragoon.rethink.Instance;
import com.ulfric.dragoon.rethink.Location;
import com.ulfric.dragoon.rethink.Store;
import com.ulfric.plugin.permissions.PermissionsService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class PersistentPermissions implements PermissionsService { // TODO thread safety

	private final List<PersistentUser> loadedUsers = new ArrayList<>();
	private final List<PersistentGroup> loadedGroups = new ArrayList<>();

	@Database(value = "permissions", table = "groups")
	private Store<PermissionEntityDocument> groups;

	@Database(value = "permissions", table = "users")
	private Store<PermissionEntityDocument> users;

	@Override
	public Class<PermissionsService> getService() {
		return PermissionsService.class;
	}

	@Override
	public User getUserByName(String name) {
		return User.getUser(name);
	}

	@Override
	public User getUserByUniqueId(UUID uniqueId) {
		return User.getUser(uniqueId);
	}

	@Override
	public User createUser(UUID uniqueId) {
		User user = getUserByUniqueId(uniqueId);
		if (user != null) {
			return user;
		}

		OfflinePlayer player = Bukkit.getOfflinePlayer(uniqueId);
		return createUserSkipLookup(uniqueId, player == null ? "unknown" : player.getName());
	}

	@Override
	public User createUser(UUID uniqueId, String name) {
		User user = getUserByUniqueId(uniqueId);

		if (user != null) {
			return user;
		}

		return createUserSkipLookup(uniqueId, name);
	}

	private User createUserSkipLookup(UUID uniqueId, String name) {
		Instance<PermissionEntityDocument> instance = getDocument(uniqueId, users);

		EntityPersistence persistence = new EntityPersistence(users, instance);
		PersistentUser persistentUser = new PersistentUser(persistence, name, uniqueId);

		instance.addListener(ignore -> persistentUser.reload());

		loadedUsers.add(persistentUser);
		return persistentUser;
	}

	@Override
	public Group getGroupByName(String name) {
		return Group.getGroup(name);
	}

	@Override
	public Group createGroupByName(String name) {
		Group group = getGroupByName(name);

		if (group != null) {
			return group;
		}

		Instance<PermissionEntityDocument> instance = getDocument(name, users);
		EntityPersistence persistence = new EntityPersistence(groups, instance);
		PersistentGroup persistentGroup = new PersistentGroup(persistence, name);

		instance.addListener(ignore -> persistentGroup.reload());

		loadedGroups.add(persistentGroup);
		return persistentGroup;
	}

	private Instance<PermissionEntityDocument> getDocument(Object key, Store<PermissionEntityDocument> store) {
		try {
			return store.get(Location.key(key)).get(); // TODO timeout
		} catch (InterruptedException | ExecutionException exception) {
			return null;
		}
	}

	@Override
	public List<User> getActiveUsers() {
		return new ArrayList<>(loadedUsers);
	}

	@Override
	public List<Group> getActiveGroups() {
		return new ArrayList<>(loadedGroups);
	}

}
