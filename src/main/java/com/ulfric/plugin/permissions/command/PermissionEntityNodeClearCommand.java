package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.Restricted;

@Name("clear")
@Permission("permission.use.entity.node.clear")
@Restricted("NodeClear")
public class PermissionEntityNodeClearCommand extends PermissionEntityNodeCommand {

	@Override
	public void run() {
		entity.clearPermission(node);
		persist();
	}

}
