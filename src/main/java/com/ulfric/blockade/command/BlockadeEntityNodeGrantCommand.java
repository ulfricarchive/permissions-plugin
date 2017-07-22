package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.node.Allowance;

@Name("grant")
@Permission("blockade.use.entity.node.grant")
public class BlockadeEntityNodeGrantCommand extends BlockadeEntityNodeCommand {

	@Override
	public void run(Context context) {
		entity.setPermission(node, Allowance.ALLOWED);
		context.getSender().sendMessage("blockade-node-grant", details());
	}

}