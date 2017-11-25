package com.ulfric.plugin.permissions.persistence;

import java.util.List;

import com.ulfric.dragoon.acrodb.Document;
import com.ulfric.plugin.permissions.persistence.limits.Limit;
import com.ulfric.plugin.permissions.persistence.permissions.Permission;

public class PermissionPersistenceEntity extends Document {
 
	private List<Limit> limits;
	private List<Permission> permissions;
	private List<String> parents;

	public List<Limit> getLimits() {
		return limits;
	}

	public void setLimits(List<Limit> limits) {
		this.limits = limits;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<String> getParents() {
		return parents;
	}

	public void setParents(List<String> parents) {
		this.parents = parents;
	}

}
