package com.ulfric.blockade.command;

import com.ulfric.andrew.ResolutionRequest;
import com.ulfric.andrew.Resolver;
import com.ulfric.embargo.PermissionsService;
import com.ulfric.embargo.entity.Entity;
import com.ulfric.platform.service.Services;

public class EntityResolver extends Resolver<Entity> {

	public EntityResolver() {
		super(Entity.class);
	}

	@Override
	public Entity apply(ResolutionRequest resolution) { // TODO support UUID, creating users, ensure persistent
		PermissionsService service = Services.get(PermissionsService.class);

		String argument = resolution.getArgument();
		int split = argument.indexOf(':');
		if (split != -1) {
			String lookup = argument.substring(split);
			if (argument.substring(0, split).toLowerCase().startsWith("g")) {
				return service.getGroupByName(lookup);
			}
			return service.getUserByName(argument);
		}
		Entity user = service.getUserByName(argument);
		if (user != null) {
			return user;
		}
		return service.getGroupByName(argument);
	}

}
