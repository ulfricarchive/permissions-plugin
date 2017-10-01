package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.node.Allowance;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.locale.TellService;

@Name("clear")
@Permission("permission.use.entity.node.clear")
public class PermissionEntityNodeClearCommand extends PermissionEntityNodeCommand {

	@Override
	public void run(Context context) {
		entity.setPermission(node, Allowance.UNDEFINED);
		TellService.sendMessage(context.getSender(), "blockade-node-clear", details());
	}

}
