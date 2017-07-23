package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.limit.Limit;

import java.util.Map;

@Name("increase")
@Permission("blockade.use.entity.limit.increase")
public class BlockadeEntityLimitIncreaseCommand extends BlockadeEntityLimitCommand {

	@Argument
	protected Limit limit;

	@Override
	public void run(Context context) {
		Limit newLimit = entity.getLimit(node).and(limit);
		entity.setLimit(node, newLimit);
		Map<String, String> details = details();
		details.put("newLimit", newLimit.toString());
		context.getSender().sendMessage("blockade-limit-increase", details);
	}

	@Override
	protected Map<String, String> details() {
		Map<String, String> details = super.details();
		details.put("limit", limit.toString());
		return details;
	}

}
