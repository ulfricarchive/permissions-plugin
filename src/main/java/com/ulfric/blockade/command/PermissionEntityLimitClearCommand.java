package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.limit.StandardLimits;
import com.ulfric.servix.services.locale.TellService;

@Name("clear")
@Permission("permission.use.entity.limit.clear")
public class PermissionEntityLimitClearCommand extends PermissionEntityLimitCommand {

	@Override
	public void run(Context context) {
		entity.setLimit(node, StandardLimits.NONE);
		TellService.sendMessage(context.getSender(), "blockade-limit-clear", details());
	}

}
