package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.node.Allowance;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.locale.TellService;

@Name("grant")
@Permission("permission.use.entity.node.grant")
public class PermissionEntityNodeGrantCommand extends PermissionEntityNodeCommand {

	@Override
	public void run(Context context) {
		entity.setPermission(node, Allowance.ALLOWED);
		TellService.sendMessage(context.getSender(), "blockade-node-grant", details());
	}

}
