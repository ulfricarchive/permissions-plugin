package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.argument.Argument;
import com.ulfric.plugin.locale.TellService;

@Name("limit")
@Permission("permission.use.entity.limit")
public class PermissionEntityLimitCommand extends PermissionEntityCommand {

	@Argument
	protected String node;

	@Override
	public void run(Context context) {
		Details details = details();
		details.add("test", entity.getLimit(node).toString());
		TellService.sendMessage(context.getSender(), "permissions-limit-test", details);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("node", node);
		return details;
	}

}
