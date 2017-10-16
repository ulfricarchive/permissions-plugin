package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.limit.Limit;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.argument.Argument;

@Name("increase")
@Permission("permission.use.entity.limit.increase")
public class PermissionEntityLimitIncreaseCommand extends PermissionEntityLimitCommand {

	@Argument
	protected Limit limit;

	@Override
	public void run(Context context) {
		Limit newLimit = entity.getLimit(node).and(limit);
		entity.setLimit(node, newLimit);
		Details details = details();
		details.add("newLimit", newLimit);
		persist(context);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("limit", limit);
		return details;
	}

}
