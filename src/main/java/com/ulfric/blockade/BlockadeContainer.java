package com.ulfric.blockade;

import com.ulfric.blockade.command.BlockadeCommand;
import com.ulfric.blockade.command.BlockadeEntityCommand;
import com.ulfric.blockade.command.BlockadeEntityLimitClearCommand;
import com.ulfric.blockade.command.BlockadeEntityLimitCommand;
import com.ulfric.blockade.command.BlockadeEntityLimitDecreaseCommand;
import com.ulfric.blockade.command.BlockadeEntityLimitIncreaseCommand;
import com.ulfric.blockade.command.BlockadeEntityLimitSetCommand;
import com.ulfric.blockade.command.BlockadeEntityNodeClearCommand;
import com.ulfric.blockade.command.BlockadeEntityNodeCommand;
import com.ulfric.blockade.command.BlockadeEntityNodeDenyCommand;
import com.ulfric.blockade.command.BlockadeEntityNodeGrantCommand;
import com.ulfric.blockade.command.BlockadeEntityParentAddCommand;
import com.ulfric.blockade.command.BlockadeEntityParentCommand;
import com.ulfric.blockade.command.BlockadeEntityParentRemoveCommand;
import com.ulfric.blockade.command.BlockadeEntityParentResetCommand;
import com.ulfric.blockade.command.BlockadeEntityParentTestCommand;
import com.ulfric.blockade.command.BlockadeEntityRecalculateCommand;
import com.ulfric.blockade.command.EntityResolver;
import com.ulfric.blockade.command.LimitResolver;
import com.ulfric.blockade.entity.PersistentGroup;
import com.ulfric.blockade.entity.PersistentPermissions;
import com.ulfric.blockade.entity.PersistentUser;
import com.ulfric.dragoon.application.Container;
import com.ulfric.embargo.PermissionsService;
import com.ulfric.platform.service.Services;

public class BlockadeContainer extends Container {

	public BlockadeContainer() {
		addShutdownHook(this::saveEntities);

		install(PersistentPermissions.class);
		install(BlockadeListener.class);

		install(EntityResolver.class);
		install(LimitResolver.class);
		install(BlockadeCommand.class);
		install(BlockadeEntityCommand.class);
		install(BlockadeEntityRecalculateCommand.class);
		install(BlockadeEntityNodeCommand.class);
		install(BlockadeEntityNodeGrantCommand.class);
		install(BlockadeEntityNodeDenyCommand.class);
		install(BlockadeEntityNodeClearCommand.class);
		install(BlockadeEntityLimitCommand.class);
		install(BlockadeEntityLimitClearCommand.class);
		install(BlockadeEntityLimitSetCommand.class);
		install(BlockadeEntityLimitIncreaseCommand.class);
		install(BlockadeEntityLimitDecreaseCommand.class);
		install(BlockadeEntityParentCommand.class);
		install(BlockadeEntityParentAddCommand.class);
		install(BlockadeEntityParentRemoveCommand.class);
		install(BlockadeEntityParentResetCommand.class);
		install(BlockadeEntityParentTestCommand.class);
	}

	private void saveEntities() { // TODO log sizes
		PermissionsService service = Services.get(PermissionsService.class);

		service.getActiveGroups().stream()
			.filter(PersistentGroup.class::isInstance)
			.map(PersistentGroup.class::cast)
			.forEach(PersistentGroup::write);

		service.getActiveUsers().stream()
			.filter(PersistentUser.class::isInstance)
			.map(PersistentUser.class::cast)
			.forEach(PersistentUser::write);
	}

}