package com.ulfric.blockade.command;

import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.andrew.argument.Argument;
import com.ulfric.commons.naming.Name;
import com.ulfric.embargo.entity.Entity;
import com.ulfric.i18n.content.Details;

@Name("test")
@Permission("blockade.use.entity.parent.test")
public class BlockadeEntityParentTestCommand extends BlockadeEntityParentCommand {

	@Argument
	protected Entity parent;

	@Override
	public void run(Context context) {
		Details details = details();
		details.add("test", Boolean.toString(entity.hasParent(parent)));
		context.getSender().sendMessage("blockade-parent-test", details);
	}

	@Override
	protected Details details() {
		Details details = super.details();
		details.add("parentName", parent.getName());
		details.add("parentUuid", parent.getUniqueId().toString());
		return details;
	}

}
