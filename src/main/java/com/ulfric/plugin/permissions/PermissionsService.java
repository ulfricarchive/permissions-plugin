package com.ulfric.plugin.permissions;

import com.ulfric.commons.permissions.entity.Group;
import com.ulfric.commons.permissions.entity.User;
import com.ulfric.plugin.services.Service;

import java.util.List;
import java.util.UUID;

public interface PermissionsService extends Service<PermissionsService> {

	static PermissionsService get() {
		return Service.get(PermissionsService.class);
	}

	List<User> getActiveUsers();

	User getUserByName(String name);

	User getUserByUniqueId(UUID uniqueId);

	User createUser(UUID uniqueId);

	User createUser(UUID uniqueId, String name);

	Group getGroupByName(String name);

	Group createGroupByName(String name);

	List<Group> getActiveGroups();

}