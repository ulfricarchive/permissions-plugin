package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.plugin.commands.Alias;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.locale.TellService;

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
