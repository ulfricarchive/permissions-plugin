package com.ulfric.blockade.command;

import com.ulfric.andrew.Alias;
import com.ulfric.andrew.Command;
import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;

@Name("permission")
@Alias("perm")
@Permission("permission.use")
public class PermissionCommand implements Command {

	@Override
	public void run(Context context) {
		// TODO help command?
	}

}
