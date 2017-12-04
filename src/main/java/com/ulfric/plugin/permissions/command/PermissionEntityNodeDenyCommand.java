package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.node.Allowance;
import com.ulfric.plugin.commands.permissions.Permission;
import com.ulfric.plugin.restrictions.command.Restricted;

@Name("deny")
@Permission("permission.use.entity.node.deny")
@Restricted("NodeDeny")
public class PermissionEntityNodeDenyCommand extends PermissionEntityNodeCommand {

	@Override
	public void run() {
		entity.setPermission(node, Allowance.DENIED);
		persist();
	}

}
