package com.ulfric.blockade.entity;

import com.ulfric.commons.service.Service;
import com.ulfric.data.database.Database;
import com.ulfric.data.database.Store;
import com.ulfric.embargo.entity.Group;
import com.ulfric.embargo.entity.User;
import com.ulfric.servix.services.PermissionsService;

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
	public Class<? extends Service> getService() {
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
	public User createUser(UUID uniqueId, String name) {
		User user = getUserByUniqueId(uniqueId);

		if (user != null) {
			return user;
		}

		PersistentUser persistentUser = new PersistentUser(users.getData(uniqueId.toString()), name, uniqueId);
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
