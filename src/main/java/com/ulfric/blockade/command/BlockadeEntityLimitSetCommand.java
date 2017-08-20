package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.limit.Limit;
import com.ulfric.i18n.content.Details;

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
	protected Details details() {
		Details details = super.details();
		details.add("limit", limit.toString());
		return details;
	}

}
