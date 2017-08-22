package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.node.Allowance;
import com.ulfric.servix.services.locale.TellService;

@Name("deny")
@Permission("blockade.use.entity.node.deny")
public class BlockadeEntityNodeDenyCommand extends BlockadeEntityNodeCommand {

	@Override
	public void run(Context context) {
		entity.setPermission(node, Allowance.DENIED);
		TellService.sendMessage(context.getSender(), "blockade-node-deny", details());
	}

}
