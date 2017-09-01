package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.entity.Entity;
import com.ulfric.i18n.content.Details;
import com.ulfric.servix.services.locale.TellService;

@Name("test")
@Permission("permission.use.entity.parent.test")
public class PermissionEntityParentTestCommand extends PermissionEntityParentCommand {

	@Argument
	protected Entity parent;

	@Override
	public void run(Context context) {
		Details details = details();
		details.add("test", Boolean.toString(entity.hasParent(parent)));
		TellService.sendMessage(context.getSender(), "blockade-parent-test", details);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("parentName", parent.getName());
		details.add("parentUuid", parent.getUniqueId().toString());
		return details;
	}

}
