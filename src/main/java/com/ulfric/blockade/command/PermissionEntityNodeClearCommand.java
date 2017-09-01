package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.node.Allowance;
import com.ulfric.servix.services.locale.TellService;

@Name("clear")
@Permission("permission.use.entity.node.clear")
public class PermissionEntityNodeClearCommand extends PermissionEntityNodeCommand {

	@Override
	public void run(Context context) {
		entity.setPermission(node, Allowance.UNDEFINED);
		TellService.sendMessage(context.getSender(), "blockade-node-clear", details());
	}

}
