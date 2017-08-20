package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.limit.Limit;
import com.ulfric.i18n.content.Details;

@Name("decrease")
@Permission("blockade.use.entity.limit.decrease")
public class BlockadeEntityLimitDecreaseCommand extends BlockadeEntityLimitCommand {

	@Argument
	protected Limit limit;

	@Override
	public void run(Context context) {
		Limit newLimit = entity.getLimit(node).without(limit);
		entity.setLimit(node, newLimit);
		Details details = details();
		details.add("newLimit", newLimit.toString());
		context.getSender().sendMessage("blockade-limit-decrease", details);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("limit", limit.toString());
		return details;
	}

}
