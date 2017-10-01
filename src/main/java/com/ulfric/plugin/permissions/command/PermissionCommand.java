package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.naming.Name;
import com.ulfric.plugin.commands.Alias;
import com.ulfric.plugin.commands.Command;
import com.ulfric.plugin.commands.Context;
import com.ulfric.plugin.commands.Permission;

@Name("permission")
@Alias("perm")
@Permission("permission.use")
public class PermissionCommand implements Command {

	@Override
	public void run(Context context) {
		// TODO help command?
	}

}
