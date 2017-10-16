package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;

@Name("clear")
@Permission("permission.use.entity.limit.clear")
public class PermissionEntityLimitClearCommand extends PermissionEntityLimitCommand {

	@Override
	public void run(Context context) {
		entity.clearLimit(node);
		persist(context);
	}

}
