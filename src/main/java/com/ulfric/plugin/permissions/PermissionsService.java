package com.ulfric.plugin.permissions;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import com.ulfric.dragoon.rethink.response.Response;
import com.ulfric.plugin.services.Service;

public interface PermissionsService extends Service<PermissionsService> {

	static PermissionsService get() {
		return Service.get(PermissionsService.class);
	}

	CompletableFuture<User> getUserByUniqueId(UUID uniqueId);

	CompletableFuture<Group> getGroupByName(String name);

	CompletableFuture<Response> persistUser(User user);

	CompletableFuture<Response> persistGroup(Group user);

}