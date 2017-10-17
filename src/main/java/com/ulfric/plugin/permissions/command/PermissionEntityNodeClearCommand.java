package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;

@Name("clear")
@Permission("permission.use.entity.node.clear")
public class PermissionEntityNodeClearCommand extends PermissionEntityNodeCommand {

	@Override
	public void run(Context context) {
		entity.clearPermission(node);
		persist(context);
	}

}
