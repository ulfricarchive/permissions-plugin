package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.argument.Argument;
import com.ulfric.plugin.commands.permissions.Permission;

@Name("limit")
@Permission("permission.use.entity.limit")
public class PermissionEntityLimitCommand extends PermissionEntityCommand {

	@Argument
	protected String node;

	@Override
	public void run() {
		Details details = details();
		details.add("test", entity.getLimit(node));
		tell("permissions-limit-test", details);
	}

}
