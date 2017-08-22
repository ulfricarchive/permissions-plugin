package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.entity.Entity;
import com.ulfric.i18n.content.Details;
import com.ulfric.servix.services.locale.TellService;

import java.util.List;

@Name("reset")
@Permission("blockade.use.entity.parent.reset")
public class BlockadeEntityParentResetCommand extends BlockadeEntityParentCommand {

	@Override
	public void run(Context context) {
		List<Entity> parents = entity.getParents();
		parents.forEach(entity::removeParent);
		Details details = details();
		details.add("parentsCount", Integer.toString(parents.size()));
		TellService.sendMessage(context.getSender(), "blockade-parent-reset", details);
	}

}
