package com.ulfric.blockade.command;

import com.ulfric.andrew.Alias;
import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.entity.Entity;
import com.ulfric.i18n.content.Details;

@Name("entity")
@Alias("ent")
@Permission("permission.use.entity")
public class PermissionEntityCommand extends PermissionCommand {

	@Argument
	protected Entity entity;

	@Override
	public void run(Context context) {
		// TODO send info about entity
	}

	protected Details details() {
		Details details = new Details();
		details.add("name", entity.getName());
		details.add("uuid", entity.getUniqueId().toString());
		return details;
	}

}
