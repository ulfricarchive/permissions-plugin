package com.ulfric.plugin.permissions.command;

import java.util.Map;
import java.util.UUID;

import org.apache.curator.shaded.com.google.common.util.concurrent.Futures;
import org.bukkit.OfflinePlayer;

import com.ulfric.commons.bukkit.player.PlayerHelper;
import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.commons.value.UniqueIdHelper;
import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.plugin.commands.Command;
import com.ulfric.plugin.commands.argument.ResolutionRequest;
import com.ulfric.plugin.commands.argument.Resolver;
import com.ulfric.plugin.permissions.PermissionsService;

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
			return Futures.getUnchecked(service.getUserByUniqueId(uniqueId));
		}

		int split = argument.indexOf(':');
		if (split != -1) {
			String lookup = argument.substring(split);
			if (Character.toLowerCase(argument.charAt(0)) == 'g') {
				return Futures.getUnchecked(service.getGroupByName(argument));
			}
			return userByName(lookup);
		}

		Entity user = userByName(argument); // TODO better solution to this - lookup rather than get?
		if (user != null) {
			return user;
		}

		return Futures.getUnchecked(service.getGroupByName(argument));
	}

	private Entity lookupGroup(ResolutionRequest resolution) {
		String argument = resolution.getArgument();
		return Futures.getUnchecked(service.getGroupByName(argument));
	}

	private Entity lookupUser(ResolutionRequest resolution) {
		String argument = resolution.getArgument();
		UUID uniqueId = UniqueIdHelper.parseUniqueId(argument);
		if (uniqueId != null) {
			return Futures.getUnchecked(service.getUserByUniqueId(uniqueId));
		}
		return userByName(argument);
	}

	private Entity userByName(String name) {
		OfflinePlayer player = PlayerHelper.getOfflinePlayerByName(name);
		if (player == null) {
			return null;
		}

		return Futures.getUnchecked(service.getUserByUniqueId(player.getUniqueId()));
	}

}
