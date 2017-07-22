package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.node.Allowance;

@Name("clear")
@Permission("blockade.use.entity.node.clear")
public class BlockadeEntityNodeClearCommand extends BlockadeEntityNodeCommand {

	@Override
	public void run(Context context) {
		entity.setPermission(node, Allowance.UNDEFINED);
		context.getSender().sendMessage("blockade-node-clear", details());
	}

}
