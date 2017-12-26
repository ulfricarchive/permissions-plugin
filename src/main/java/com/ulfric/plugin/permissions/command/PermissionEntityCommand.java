package com.ulfric.plugin.permissions.command;

import java.util.concurrent.CompletableFuture;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.plugin.commands.Alias;
import com.ulfric.plugin.commands.argument.Argument;
import com.ulfric.plugin.commands.permissions.Permission;
import com.ulfric.plugin.permissions.Group;
import com.ulfric.plugin.permissions.PermissionsService;
import com.ulfric.plugin.permissions.User;

@Name("entity")
@Alias({"ent", "group", "user"})
@Permission("permission-use-entity")
public class PermissionEntityCommand extends PermissionCommand {

	@Argument
	protected Entity entity;

	@Override
	public void run() {
		tell("permissions-entity");
	}

	protected CompletableFuture<Void> persist() {
		if (entity instanceof User) {
			return persistUser();
		}

		if (entity instanceof Group) {
			return persistGroup();
		}

		throw new IllegalStateException("Entity " + entity + " is not a user or a group");
	}

	private CompletableFuture<Void> persistUser() {
		return PermissionsService.get().persistUser((User) entity)
				.thenRun(this::notifyPermissionSave);
	}

	private CompletableFuture<Void> persistGroup() {
		return PermissionsService.get().persistGroup((Group) entity)
				.thenRun(this::notifyPermissionSave);
	}

	private void notifyPermissionSave() {
		tell("permissions-save");
	}

}
