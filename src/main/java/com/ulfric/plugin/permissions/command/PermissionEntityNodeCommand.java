package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.argument.Argument;
import com.ulfric.plugin.commands.permissions.Permission;

@Name("node")
@Permission("permission-use-entity-node")
public class PermissionEntityNodeCommand extends PermissionEntityCommand {

	@Argument
	protected String node;

	@Override
	public void run() {
		Details details = details();
		details.add("test", entity.testPermission(node));
		tell("permissions-node-test", details);
	}

}
