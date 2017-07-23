package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.limit.StandardLimits;

@Name("clear")
@Permission("blockade.use.entity.limit.clear")
public class BlockadeEntityLimitClearCommand extends BlockadeEntityLimitCommand {

	@Override
	public void run(Context context) {
		entity.setLimit(node, StandardLimits.NONE);
		context.getSender().sendMessage("blockade-limit-clear", details());
	}

}
