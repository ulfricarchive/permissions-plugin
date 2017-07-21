package com.ulfric.blockade.command;

import com.ulfric.andrew.ResolutionRequest;
import com.ulfric.andrew.Resolver;
import com.ulfric.embargo.entity.Entity;
import com.ulfric.embargo.entity.Group;
import com.ulfric.embargo.entity.User;

public class EntityResolver extends Resolver<Entity> {

	public EntityResolver() {
		super(Entity.class);
	}

	@Override
	public Entity apply(ResolutionRequest resolution) { // TODO support UUID, creating users, ensure persistent
		String argument = resolution.getArgument();
		int split = argument.indexOf(':');
		if (split != -1) {
			String lookup = argument.substring(split);
			if (argument.substring(0, split).toLowerCase().startsWith("g")) {
				return Group.getGroup(lookup);
			}
			return User.getUser(lookup);
		}
		Entity user = User.getUser(argument);
		if (user != null) {
			return user;
		}
		return Group.getGroup(argument);
	}

}