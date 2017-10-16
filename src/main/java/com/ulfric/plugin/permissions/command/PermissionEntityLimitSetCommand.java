package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.limit.Limit;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.argument.Argument;

@Name("set")
@Permission("permission.use.entity.limit.set")
public class PermissionEntityLimitSetCommand extends PermissionEntityLimitCommand {

	@Argument
	protected Limit limit;

	@Override
	public void run(Context context) {
		entity.setLimit(node, limit);
		persist(context);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("limit", limit.toString());
		return details;
	}

}
