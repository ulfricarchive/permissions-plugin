package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.argument.Argument;
import com.ulfric.plugin.locale.TellService;

@Name("test")
@Permission("permission.use.entity.parent.test")
public class PermissionEntityParentTestCommand extends PermissionEntityParentCommand {

	@Argument
	protected Entity parent;

	@Override
	public void run(Context context) {
		Details details = details();
		details.add("test", Boolean.toString(entity.hasParent(parent)));
		TellService.sendMessage(context.getSender(), "permissions-parent-test", details);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("parent", parent);
		return details;
	}

}
