package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.entity.Entity;
import com.ulfric.i18n.content.Details;
import com.ulfric.servix.services.locale.TellService;

@Name("remove")
@Permission("permission.use.entity.parent.remove")
public class PermissionEntityParentRemoveCommand extends PermissionEntityParentCommand {

	@Argument
	protected Entity parent;

	@Override
	public void run(Context context) {
		entity.removeParent(parent);
		TellService.sendMessage(context.getSender(), "blockade-parent-remove", details());
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("parentName", parent.getName());
		details.add("parentUuid", parent.getUniqueId().toString());
		return details;
	}

}
