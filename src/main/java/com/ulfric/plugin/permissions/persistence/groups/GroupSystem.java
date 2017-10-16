package com.ulfric.plugin.permissions.persistence.groups;

import com.ulfric.dragoon.rethink.Database;
import com.ulfric.dragoon.rethink.Store;
import com.ulfric.plugin.entities.Entity;
import com.ulfric.plugin.entities.EntitySystem;

public class GroupSystem extends EntitySystem {

	@Database(value = "permissions", table = "groups")
	private Store<Entity> store;

	@Override
	protected Store<Entity> store() {
		return store;
	}

}
