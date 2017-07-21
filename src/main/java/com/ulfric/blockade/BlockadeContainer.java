package com.ulfric.blockade;

import com.ulfric.blockade.entity.PersistentGroup;
import com.ulfric.blockade.entity.PersistentPermissions;
import com.ulfric.blockade.entity.PersistentUser;
import com.ulfric.dragoon.application.Container;
import com.ulfric.embargo.PermissionsService;
import com.ulfric.platform.service.Services;

public class BlockadeContainer extends Container {

	public BlockadeContainer() {
		install(PersistentPermissions.class);
		install(BlockadeListener.class);

		addShutdownHook(this::saveEntities);
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