package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.embargo.limit.StandardLimits;

public class BlockadeEntityLimitClearCommand extends BlockadeEntityLimitCommand {

	@Override
	public void run(Context context) {
		entity.setLimit(node, StandardLimits.NONE);
		context.getSender().sendMessage("blockade-limit-clear", details());
	}

}