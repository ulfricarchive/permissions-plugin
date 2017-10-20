package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.node.Allowance;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.Restricted;

@Name("grant")
@Permission("permission.use.entity.node.grant")
@Restricted("NodeGrant")
public class PermissionEntityNodeGrantCommand extends PermissionEntityNodeCommand {

	@Override
	public void run() {
		entity.setPermission(node, Allowance.ALLOWED);
		persist(context);
	}

}
