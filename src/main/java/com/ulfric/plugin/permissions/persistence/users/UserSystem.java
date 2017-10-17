package com.ulfric.plugin.permissions.persistence.users;

import com.ulfric.dragoon.extension.inject.Inject;
import com.ulfric.dragoon.rethink.Database;
import com.ulfric.dragoon.rethink.Store;
import com.ulfric.plugin.entities.Entity;
import com.ulfric.plugin.entities.EntitySystem;

public class UserSystem extends EntitySystem {

	@Inject
	@Database(value = "permissions", table = "users")
	private Store<Entity> store;

	@Override
	protected Store<Entity> store() {
		return store;
	}

}
