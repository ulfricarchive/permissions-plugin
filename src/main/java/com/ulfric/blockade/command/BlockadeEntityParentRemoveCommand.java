package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.entity.Entity;

import java.util.Map;

@Name("remove")
@Permission("blockade.use.entity.parent.remove")
public class BlockadeEntityParentRemoveCommand extends BlockadeEntityParentCommand {

	@Argument
	protected Entity parent;

	@Override
	public void run(Context context) {
		entity.removeParent(parent);
		context.getSender().sendMessage("blockade-parent-remove", details());
	}

	@Override
	protected Map<String, String> details() {
		Map<String, String> details = super.details();
		details.put("parentName", parent.getName());
		details.put("parentUuid", parent.getUniqueId().toString());
		return details;
	}

}
