package com.ulfric.blockade.entity;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import com.ulfric.data.database.Database;
import com.ulfric.data.database.Store;
import com.ulfric.embargo.entity.Group;
import com.ulfric.embargo.entity.User;
import com.ulfric.servix.services.permissions.PermissionsService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PersistentPermissions implements PermissionsService { // TODO thread safety

	private final List<PersistentUser> loadedUsers = new ArrayList<>();
	private final List<PersistentGroup> loadedGroups = new ArrayList<>();

	@Database
	private Store groups;

	@Database
	private Store users;

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
		PersistentUser persistentUser = new PersistentUser(users.getData(uniqueId), name, uniqueId);
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

		PersistentGroup persistentGroup = new PersistentGroup(groups.getData(name), name);
		loadedGroups.add(persistentGroup);
		return persistentGroup;
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
