package com.ulfric.plugin.permissions.command;

import java.util.List;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.Restricted;

@Name("reset")
@Permission("permission.use.entity.parent.reset")
@Restricted("ParentReset")
public class PermissionEntityParentResetCommand extends PermissionEntityParentCommand {

	private List<Entity> oldParents;

	@Override
	public void run() {
		oldParents = entity.getParents();
		oldParents.forEach(entity::removeParent);
		persist();
	}

}
