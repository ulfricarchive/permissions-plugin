package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.node.Allowance;
import com.ulfric.servix.services.locale.TellService;

@Name("grant")
@Permission("blockade.use.entity.node.grant")
public class BlockadeEntityNodeGrantCommand extends BlockadeEntityNodeCommand {

	@Override
	public void run(Context context) {
		entity.setPermission(node, Allowance.ALLOWED);
		TellService.sendMessage(context.getSender(), "blockade-node-grant", details());
	}

}
