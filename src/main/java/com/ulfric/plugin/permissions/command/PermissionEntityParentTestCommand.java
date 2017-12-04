package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.argument.Argument;
import com.ulfric.plugin.commands.permissions.Permission;

@Name("test")
@Permission("permission.use.entity.parent.test")
public class PermissionEntityParentTestCommand extends PermissionEntityParentCommand {

	@Argument
	protected Entity parent;

	@Override
	public void run() {
		Details details = details();
		details.add("test", entity.hasParent(parent));
		tell("permissions-parent-test", details);
	}

}
