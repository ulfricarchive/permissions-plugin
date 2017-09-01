package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.limit.Limit;
import com.ulfric.i18n.content.Details;
import com.ulfric.servix.services.locale.TellService;

@Name("set")
@Permission("permission.use.entity.limit.set")
public class PermissionEntityLimitSetCommand extends PermissionEntityLimitCommand {

	@Argument
	protected Limit limit;

	@Override
	public void run(Context context) {
		entity.setLimit(node, limit);
		TellService.sendMessage(context.getSender(), "blockade-limit-set", details());
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("limit", limit.toString());
		return details;
	}

}