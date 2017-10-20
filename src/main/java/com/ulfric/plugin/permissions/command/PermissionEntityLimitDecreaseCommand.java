package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.limit.Limit;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.Restricted;
import com.ulfric.plugin.commands.argument.Argument;

@Name("decrease")
@Permission("permission.use.entity.limit.decrease")
@Restricted("LimitDecrease")
public class PermissionEntityLimitDecreaseCommand extends PermissionEntityLimitCommand {

	@Argument
	protected Limit limit;

	@Override
	public void run() {
		Limit newLimit = entity.getLimit(node).without(limit);
		entity.setLimit(node, newLimit);
		Details details = details();
		details.add("newLimit", newLimit);
		persist(context);
	}

}
