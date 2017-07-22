package com.ulfric.blockade.command;

import com.ulfric.andrew.Argument;
import com.ulfric.andrew.Context;
import com.ulfric.embargo.limit.Limit;

import java.util.Map;

public class BlockadeEntityLimitSetCommand extends BlockadeEntityLimitCommand {

	@Argument
	protected Limit limit;

	@Override
	public void run(Context context) {
		entity.setLimit(node, limit);
		context.getSender().sendMessage("blockade-limit-decrease", details());
	}

	@Override
	protected Map<String, String> details() {
		Map<String, String> details = super.details();
		details.put("limit", limit.toString());
		return details;
	}

}
