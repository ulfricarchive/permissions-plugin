package com.ulfric.blockade.command;

import com.ulfric.andrew.Alias;
import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;

@Name("recalculate")
@Alias("recalc")
@Permission("blockade.use.entity.recalculate")
public class BlockadeEntityRecalculateCommand extends BlockadeEntityCommand {

	@Override
	public void run(Context context) {
		entity.recalculate();
		context.getSender().sendMessage("blockade-recalculate", details());
	}

}