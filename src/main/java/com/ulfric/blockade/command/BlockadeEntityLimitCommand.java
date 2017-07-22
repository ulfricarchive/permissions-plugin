package com.ulfric.blockade.command;

import com.ulfric.andrew.Argument;
import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;

import java.util.Map;

@Name("limit")
@Permission("blockade.use.entity.limit")
public class BlockadeEntityLimitCommand extends BlockadeEntityCommand {

	@Argument
	protected String node;

	@Override
	public void run(Context context) {
		Map<String, String> details = details();
		details.put("test", entity.getLimit(node).toString());
		context.getSender().sendMessage("blockade-limit-test", details);
	}

	@Override
	protected Map<String, String> details() {
		Map<String, String> details = super.details();
		details.put("node", node);
		return details;
	}

}