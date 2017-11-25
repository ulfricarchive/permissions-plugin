package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.Restricted;

@Name("clear")
@Permission("permission.use.entity.limit.clear")
@Restricted("LimitClear")
public class PermissionEntityLimitClearCommand extends PermissionEntityLimitCommand {

	@Override
	public void run() {
		entity.clearLimit(node);
		persist();
	}

}
