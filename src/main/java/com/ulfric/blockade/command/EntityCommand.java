package com.ulfric.blockade.command;

import com.ulfric.andrew.Alias;
import com.ulfric.andrew.Argument;
import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.entity.Entity;

@Name("entity")
@Alias("ent")
@Permission("embargo.use.user")
public class EntityCommand extends EmbargoCommand {

	@Argument
	protected Entity user;

	@Override
	public void run(Context context) {
		// TODO send info about user
	}

}
