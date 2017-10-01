package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.node.Allowance;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.locale.TellService;

@Name("deny")
@Permission("permission.use.entity.node.deny")
public class PermissionEntityNodeDenyCommand extends PermissionEntityNodeCommand {

	@Override
	public void run(Context context) {
		entity.setPermission(node, Allowance.DENIED);
		TellService.sendMessage(context.getSender(), "blockade-node-deny", details());
	}

}
