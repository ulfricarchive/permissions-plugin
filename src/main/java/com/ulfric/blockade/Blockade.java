package com.ulfric.blockade;

import com.ulfric.blockade.command.PermissionCommand;
import com.ulfric.blockade.command.PermissionEntityCommand;
import com.ulfric.blockade.command.PermissionEntityLimitClearCommand;
import com.ulfric.blockade.command.PermissionEntityLimitCommand;
import com.ulfric.blockade.command.PermissionEntityLimitDecreaseCommand;
import com.ulfric.blockade.command.PermissionEntityLimitIncreaseCommand;
import com.ulfric.blockade.command.PermissionEntityLimitSetCommand;
import com.ulfric.blockade.command.PermissionEntityNodeClearCommand;
import com.ulfric.blockade.command.PermissionEntityNodeCommand;
import com.ulfric.blockade.command.PermissionEntityNodeDenyCommand;
import com.ulfric.blockade.command.PermissionEntityNodeGrantCommand;
import com.ulfric.blockade.command.PermissionEntityParentAddCommand;
import com.ulfric.blockade.command.PermissionEntityParentCommand;
import com.ulfric.blockade.command.PermissionEntityParentRemoveCommand;
import com.ulfric.blockade.command.PermissionEntityParentResetCommand;
import com.ulfric.blockade.command.PermissionEntityParentTestCommand;
import com.ulfric.blockade.command.PermissionEntityRecalculateCommand;
import com.ulfric.blockade.command.EntityResolver;
import com.ulfric.blockade.command.LimitResolver;
import com.ulfric.blockade.entity.PersistentGroup;
import com.ulfric.blockade.entity.PersistentPermissions;
import com.ulfric.blockade.entity.PersistentUser;
import com.ulfric.plugin.Plugin;
import com.ulfric.servix.services.permissions.PermissionsService;

public class Blockade extends Plugin {

	public Blockade() {
		addShutdownHook(this::saveEntities);

		install(PersistentPermissions.class);
		install(BlockadeListener.class);

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

	private void saveEntities() { // TODO log sizes
		PermissionsService service = PermissionsService.get();
		if (service == null) {
			throw new IllegalStateException("Could not find PermissionsService");
		}

		service.getActiveGroups().stream().filter(PersistentGroup.class::isInstance).map(PersistentGroup.class::cast)
		        .forEach(PersistentGroup::write);

		service.getActiveUsers().stream().filter(PersistentUser.class::isInstance).map(PersistentUser.class::cast)
		        .forEach(PersistentUser::write);
	}

}
