package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.locale.TellService;

import java.util.List;

@Name("reset")
@Permission("permission.use.entity.parent.reset")
public class PermissionEntityParentResetCommand extends PermissionEntityParentCommand {

	@Override
	public void run(Context context) {
		List<Entity> parents = entity.getParents();
		parents.forEach(entity::removeParent);
		Details details = details();
		details.add("parentsCount", Integer.toString(parents.size()));
		TellService.sendMessage(context.getSender(), "blockade-parent-reset", details);
	}

}
