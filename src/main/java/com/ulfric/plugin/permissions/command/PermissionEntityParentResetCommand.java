package com.ulfric.plugin.permissions.command;

import java.util.List;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;

@Name("reset")
@Permission("permission.use.entity.parent.reset")
public class PermissionEntityParentResetCommand extends PermissionEntityParentCommand {

	private List<Entity> oldParents;

	@Override
	public void run(Context context) {
		oldParents = entity.getParents();
		oldParents.forEach(entity::removeParent);
		persist(context);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("oldParents", oldParents);
		return details;
	}

}
