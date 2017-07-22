package com.ulfric.blockade.command;

import com.ulfric.andrew.Alias;
import com.ulfric.andrew.Argument;
import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.entity.Entity;

import java.util.HashMap;
import java.util.Map;

@Name("entity")
@Alias("ent")
@Permission("blockade.use.entity")
public class BlockadeEntityCommand extends BlockadeCommand {

	@Argument
	protected Entity entity;

	@Override
	public void run(Context context) {
		// TODO send info about entity
	}

	protected Map<String, String> details() {
		Map<String, String> details = new HashMap<>();
		details.put("name", entity.getName());
		details.put("uuid", entity.getUniqueId().toString());
		return details;
	}

}
