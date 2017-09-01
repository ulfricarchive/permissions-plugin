package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.i18n.content.Details;
import com.ulfric.servix.services.locale.TellService;

@Name("node")
@Permission("permission.use.entity.node")
public class PermissionEntityNodeCommand extends PermissionEntityCommand {

	@Argument
	protected String node;

	@Override
	public void run(Context context) {
		Details details = details();
		details.add("test", entity.testPermission(node).name().toLowerCase());
		TellService.sendMessage(context.getSender(), "blockade-node-test", details);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("node", node);
		return details;
	}

}
