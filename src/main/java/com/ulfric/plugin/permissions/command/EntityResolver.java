package com.ulfric.plugin.permissions.command;

import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.commons.value.UniqueIdHelper;
import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.plugin.commands.Command;
import com.ulfric.plugin.commands.argument.ResolutionRequest;
import com.ulfric.plugin.commands.argument.Resolver;
import com.ulfric.plugin.permissions.PermissionsService;

import java.util.Map;
import java.util.UUID;

public class EntityResolver extends Resolver<Entity> {

	@Inject
	private PermissionsService service;

	public EntityResolver() {
		super(Entity.class);
	}

	@Override
	public Entity apply(ResolutionRequest resolution) {
		Map<Class<? extends Command>, String> labels = resolution.getContext().getLabels().getLabels();
		if (labels != null) {
			if (labels.containsValue("group")) {
				return lookupGroup(resolution);
			} else if (labels.containsValue("user")) {
				return lookupUser(resolution);
			}
		}

		String argument = resolution.getArgument();
		UUID uniqueId = UniqueIdHelper.parseUniqueId(argument);
		if (uniqueId != null) {
			return service.createUser(uniqueId);
		}

		int split = argument.indexOf(':');
		if (split != -1) {
			String lookup = argument.substring(split);
			if (Character.toLowerCase(argument.charAt(0)) == 'g') {
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

	private Entity lookupGroup(ResolutionRequest resolution) {
		String argument = resolution.getArgument();
		return service.getGroupByName(argument);
	}

	private Entity lookupUser(ResolutionRequest resolution) {
		String argument = resolution.getArgument();
		UUID uniqueId = UniqueIdHelper.parseUniqueId(argument);
		if (uniqueId != null) {
			return service.createUser(uniqueId);
		}
		return service.getUserByName(argument);
	}

}
