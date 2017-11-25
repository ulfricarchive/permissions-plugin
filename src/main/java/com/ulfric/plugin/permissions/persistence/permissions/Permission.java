package com.ulfric.plugin.permissions.persistence.permissions;

import com.ulfric.plugin.permissions.persistence.Node;

public class Permission extends Node {

	private Boolean allowed;

	public Boolean getAllowed() {
		return allowed;
	}

	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}

}
