package com.ulfric.plugin.permissions.command;

import java.util.concurrent.CompletableFuture;

import org.bukkit.command.CommandSender;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.dragoon.rethink.response.Response;
import com.ulfric.dragoon.rethink.response.ResponseHelper;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.Alias;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.argument.Argument;
import com.ulfric.plugin.locale.TellService;
import com.ulfric.plugin.permissions.Group;
import com.ulfric.plugin.permissions.PermissionsService;
import com.ulfric.plugin.permissions.User;

@Name("entity")
@Alias({"ent", "group", "user"})
@Permission("permission.use.entity")
public class PermissionEntityCommand extends PermissionCommand {

	@Argument
	protected Entity entity;

	@Override
	public void run(Context context) {
		// TODO send info about entity
	}

	protected Details details() {
		Details details = new Details();
		details.add("entity", entity);
		return details;
	}

	protected CompletableFuture<Response> persist(Context context) {
		return entity instanceof User ? persistUser(context) : persistGroup(context);
	}

	private CompletableFuture<Response> persistUser(Context context) {
		return PermissionsService.get().persistUser((User) entity)
				.whenComplete((response, error) -> notifyPermissionSaveOrError(context, response, error));
	}

	private CompletableFuture<Response> persistGroup(Context context) {
		return PermissionsService.get().persistGroup((Group) entity)
				.whenComplete((response, error) -> notifyPermissionSaveOrError(context, response, error));
	}

	private void notifyPermissionSaveOrError(Context context, Response response, Throwable thrown) {
		CommandSender sender = context.getSender();

		Details details = details();
		details.add("response", response);
		details.add("error", thrown);

		if (thrown != null) {
			TellService.sendMessage(sender, "permissions-save-error", details);
			throw new RuntimeException(thrown); // TODO error handling
		}

		if (!ResponseHelper.changedData(response)) {
			TellService.sendMessage(sender, "permissions-save-nothing", details);
			return;
		}

		TellService.sendMessage(sender, "permissions-save", details);
	}

}
