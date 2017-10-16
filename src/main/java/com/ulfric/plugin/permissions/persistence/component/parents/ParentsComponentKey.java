package com.ulfric.plugin.permissions.persistence.component.parents;

import com.ulfric.commons.naming.Name;
import com.ulfric.plugin.entities.components.ComponentKey;

@Name("permissions/parents")
enum ParentsComponentKey implements ComponentKey<ParentsComponent> {

	INSTANCE;

	@Override
	public Class<ParentsComponent> getComponentType() {
		return ParentsComponent.class;
	}

	@Override
	public String toString() {
		return getName();
	}

}
