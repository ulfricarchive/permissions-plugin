package com.ulfric.blockade.command;

import com.ulfric.andrew.Alias;
import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.servix.services.locale.TellService;

@Name("recalculate")
@Alias("recalc")
@Permission("blockade.use.entity.recalculate")
public class BlockadeEntityRecalculateCommand extends BlockadeEntityCommand {

	@Override
	public void run(Context context) {
		entity.recalculate();
		TellService.sendMessage(context.getSender(), "blockade-recalculate", details());
	}

}
