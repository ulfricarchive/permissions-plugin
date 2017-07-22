package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.node.Allowance;

@Name("deny")
@Permission("blockade.use.entity.node.deny")
public class BlockadeEntityNodeDenyCommand extends BlockadeEntityNodeCommand {

	@Override
	public void run(Context context) {
		entity.setPermission(node, Allowance.DENIED);
		context.getSender().sendMessage("blockade-node-deny", details());
	}

}
