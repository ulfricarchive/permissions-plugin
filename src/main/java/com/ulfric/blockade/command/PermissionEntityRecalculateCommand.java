package com.ulfric.blockade.command;

import com.ulfric.andrew.Alias;
import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.servix.services.locale.TellService;

@Name("recalculate")
@Alias("recalc")
@Permission("permission.use.entity.recalculate")
public class PermissionEntityRecalculateCommand extends PermissionEntityCommand {

	@Override
	public void run(Context context) {
		entity.recalculate();
		TellService.sendMessage(context.getSender(), "blockade-recalculate", details());
	}

}
