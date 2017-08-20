package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.i18n.content.Details;

@Name("limit")
@Permission("blockade.use.entity.limit")
public class BlockadeEntityLimitCommand extends BlockadeEntityCommand {

	@Argument
	protected String node;

	@Override
	public void run(Context context) {
		Details details = details();
		details.add("test", entity.getLimit(node).toString());
		context.getSender().sendMessage("blockade-limit-test", details);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("node", node);
		return details;
	}

}
