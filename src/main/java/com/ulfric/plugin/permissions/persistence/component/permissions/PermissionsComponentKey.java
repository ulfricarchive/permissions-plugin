package com.ulfric.plugin.permissions.persistence.component.permissions;

import com.ulfric.commons.naming.Name;
import com.ulfric.plugin.entities.components.ComponentKey;

@Name("permissions/permissions")
enum PermissionsComponentKey implements ComponentKey<PermissionsComponent> {

	INSTANCE;

	@Override
	public Class<PermissionsComponent> getComponentType() {
		return PermissionsComponent.class;
	}

	@Override
	public String toString() {
		return getName();
	}

}
