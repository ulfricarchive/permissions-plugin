package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.argument.Argument;

@Name("remove")
@Permission("permission.use.entity.parent.remove")
public class PermissionEntityParentRemoveCommand extends PermissionEntityParentCommand {

	@Argument
	protected Entity parent;

	@Override
	public void run(Context context) {
		entity.removeParent(parent);
		persist(context);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("parent", parent);
		return details;
	}

}
