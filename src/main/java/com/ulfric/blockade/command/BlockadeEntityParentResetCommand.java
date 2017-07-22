package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.entity.Entity;

import java.util.List;
import java.util.Map;

@Name("reset")
@Permission("blockade.use.entity.parent.reset")
public class BlockadeEntityParentResetCommand extends BlockadeEntityParentCommand {

	@Override
	public void run(Context context) {
		List<Entity> parents = entity.getParents();
		parents.forEach(entity::removeParent);
		Map<String, String> details = details();
		details.put("parentsCount", Integer.toString(details.size()));
		context.getSender().sendMessage("blockade-parent-reset", details);
	}

}
