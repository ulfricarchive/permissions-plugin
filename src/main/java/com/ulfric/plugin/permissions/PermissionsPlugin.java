package com.ulfric.plugin.permissions;

import com.ulfric.plugin.Plugin;
import com.ulfric.plugin.permissions.command.EntityResolver;
import com.ulfric.plugin.permissions.command.LimitResolver;
import com.ulfric.plugin.permissions.command.PermissionCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityLimitClearCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityLimitCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityLimitDecreaseCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityLimitIncreaseCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityLimitSetCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityNodeClearCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityNodeCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityNodeDenyCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityNodeGrantCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityParentAddCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityParentCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityParentRemoveCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityParentResetCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityParentTestCommand;
import com.ulfric.plugin.permissions.command.PermissionEntityRecalculateCommand;
import com.ulfric.plugin.permissions.persistence.PersistentPermissions;

public class PermissionsPlugin extends Plugin {

	public PermissionsPlugin() {
		install(PersistentPermissions.class);
		install(PermissionsListener.class);

		install(EntityResolver.class);
		install(LimitResolver.class);

		install(PermissionCommand.class);
		install(PermissionEntityCommand.class);
		install(PermissionEntityRecalculateCommand.class);
		install(PermissionEntityNodeCommand.class);
		install(PermissionEntityNodeGrantCommand.class);
		install(PermissionEntityNodeDenyCommand.class);
		install(PermissionEntityNodeClearCommand.class);
		install(PermissionEntityLimitCommand.class);
		install(PermissionEntityLimitClearCommand.class);
		install(PermissionEntityLimitSetCommand.class);
		install(PermissionEntityLimitIncreaseCommand.class);
		install(PermissionEntityLimitDecreaseCommand.class);
		install(PermissionEntityParentCommand.class);
		install(PermissionEntityParentAddCommand.class);
		install(PermissionEntityParentRemoveCommand.class);
		install(PermissionEntityParentResetCommand.class);
		install(PermissionEntityParentTestCommand.class);
	}

}
