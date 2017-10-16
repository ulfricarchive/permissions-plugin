package com.ulfric.plugin.permissions.persistence.component.permissions;

import com.ulfric.plugin.permissions.persistence.component.Node;

public class Permission extends Node {

	private Boolean allowed;

	public Boolean getAllowed() {
		return allowed;
	}

	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}

}
