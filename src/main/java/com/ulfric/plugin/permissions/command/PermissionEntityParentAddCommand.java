package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.i18n.content.Details;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;
import com.ulfric.plugin.commands.argument.Argument;
import com.ulfric.plugin.locale.TellService;

@Name("add")
@Permission("permission.use.entity.parent.add")
public class PermissionEntityParentAddCommand extends PermissionEntityParentCommand {

	@Argument
	protected Entity parent;

	@Override
	public void run(Context context) {
		entity.addParent(parent);
		TellService.sendMessage(context.getSender(), "blockade-parent-add", details());
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("parentName", parent.getName());
		details.add("parentUuid", parent.getUniqueId().toString());
		return details;
	}

}
