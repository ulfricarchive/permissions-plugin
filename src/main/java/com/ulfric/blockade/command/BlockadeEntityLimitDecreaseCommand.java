package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.embargo.limit.Limit;

import java.util.Map;

public class BlockadeEntityLimitDecreaseCommand extends BlockadeEntityLimitCommand {

	@Argument
	protected Limit limit;

	@Override
	public void run(Context context) {
		Limit newLimit = entity.getLimit(node).without(limit);
		entity.setLimit(node, newLimit);
		Map<String, String> details = details();
		details.put("newLimit", newLimit.toString());
		context.getSender().sendMessage("blockade-limit-decrease", details);
	}

	@Override
	protected Map<String, String> details() {
		Map<String, String> details = super.details();
		details.put("limit", limit.toString());
		return details;
	}

}
