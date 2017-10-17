package com.ulfric.plugin.permissions.persistence.component.limits;

import com.ulfric.commons.naming.Name;
import com.ulfric.plugin.entities.components.ComponentKey;

@Name("permissions/limits")
enum LimitsComponentKey implements ComponentKey<LimitsComponent> {

	INSTANCE;

	@Override
	public Class<LimitsComponent> getComponentType() {
		return LimitsComponent.class;
	}

}
