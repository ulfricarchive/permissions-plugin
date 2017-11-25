package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.Restricted;
import com.ulfric.plugin.commands.argument.Argument;

@Name("remove")
@Permission("permission.use.entity.parent.remove")
@Restricted("ParentRemove")
public class PermissionEntityParentRemoveCommand extends PermissionEntityParentCommand {

	@Argument
	protected Entity parent;

	@Override
	public void run() {
		entity.removeParent(parent);
		persist();
	}

}
