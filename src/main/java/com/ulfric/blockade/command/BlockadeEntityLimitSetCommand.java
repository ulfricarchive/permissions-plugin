package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.limit.Limit;

import java.util.Map;

@Name("set")
@Permission("blockade.use.entity.limit.set")
public class BlockadeEntityLimitSetCommand extends BlockadeEntityLimitCommand {

	@Argument
	protected Limit limit;

	@Override
	public void run(Context context) {
		entity.setLimit(node, limit);
		context.getSender().sendMessage("blockade-limit-set", details());
	}

	@Override
	protected Map<String, String> details() {
		Map<String, String> details = super.details();
		details.put("limit", limit.toString());
		return details;
	}

}
