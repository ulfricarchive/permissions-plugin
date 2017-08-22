package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.limit.Limit;
import com.ulfric.i18n.content.Details;
import com.ulfric.servix.services.locale.TellService;

@Name("increase")
@Permission("blockade.use.entity.limit.increase")
public class BlockadeEntityLimitIncreaseCommand extends BlockadeEntityLimitCommand {

	@Argument
	protected Limit limit;

	@Override
	public void run(Context context) {
		Limit newLimit = entity.getLimit(node).and(limit);
		entity.setLimit(node, newLimit);
		Details details = details();
		details.add("newLimit", newLimit.toString());
		TellService.sendMessage(context.getSender(), "blockade-limit-increase", details);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("limit", limit.toString());
		return details;
	}

}
