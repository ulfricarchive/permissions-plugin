package com.ulfric.blockade.command;

import com.ulfric.andrew.Alias;
import com.ulfric.andrew.Command;
import com.ulfric.andrew.Context;
import com.ulfric.andrew.Permission;
import com.ulfric.commons.naming.Name;

@Name("embargo")
@Alias({"permission", "perm"})
@Permission("embargo.use")
public class EmbargoCommand implements Command {

	@Override
	public void run(Context context) {
		// TODO help command?
	}

}