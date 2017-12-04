package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.limit.Limit;
import com.ulfric.plugin.commands.argument.Argument;
import com.ulfric.plugin.commands.permissions.Permission;
import com.ulfric.plugin.restrictions.command.Restricted;

@Name("set")
@Permission("permission.use.entity.limit.set")
@Restricted("LimitSet")
public class PermissionEntityLimitSetCommand extends PermissionEntityLimitCommand {

	@Argument
	protected Limit limit;

	@Override
	public void run() {
		entity.setLimit(node, limit);
		persist();
	}

}
