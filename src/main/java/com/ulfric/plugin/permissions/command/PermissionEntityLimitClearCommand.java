package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.limit.StandardLimits;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.locale.TellService;

@Name("clear")
@Permission("permission.use.entity.limit.clear")
public class PermissionEntityLimitClearCommand extends PermissionEntityLimitCommand {

	@Override
	public void run(Context context) {
		entity.setLimit(node, StandardLimits.NONE);
		TellService.sendMessage(context.getSender(), "blockade-limit-clear", details());
	}

}
