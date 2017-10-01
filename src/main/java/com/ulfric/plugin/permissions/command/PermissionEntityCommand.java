package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.Alias;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.argument.Argument;

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
		details.add("name", entity.getName());
		details.add("uuid", entity.getUniqueId().toString());
		return details;
	}

}
