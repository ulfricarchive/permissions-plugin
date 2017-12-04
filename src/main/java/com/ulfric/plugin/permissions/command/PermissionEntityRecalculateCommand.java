package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.plugin.commands.Alias;
import com.ulfric.plugin.commands.permissions.Permission;

@Name("recalculate")
@Alias("recalc")
@Permission("permission.use.entity.recalculate")
public class PermissionEntityRecalculateCommand extends PermissionEntityCommand {

	@Override
	public void run() {
		entity.recalculate();
		tell("permissions-recalculate");
	}

}
