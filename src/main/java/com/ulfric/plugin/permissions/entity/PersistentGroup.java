package com.ulfric.plugin.permissions.entity;

import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.commons.permissions.entity.Group;
import com.ulfric.commons.permissions.limit.Limit;
import com.ulfric.commons.permissions.node.Allowance;

public class PersistentGroup extends Group {

	private final EntityPersistence persistence;

	public PersistentGroup(EntityPersistence persistence, String name) {
		super(name);

		this.persistence = persistence;
		load();
	}

	@Override
	public void setPermission(String node, Allowance allowance) {
		super.setPermission(node, allowance);
		persistence.changedNodes();
	}

	@Override
	public void clearPermission(String node) {
		super.clearPermission(node);
		persistence.changedNodes();
	}

	@Override
	public void setLimit(String node, Limit limit) {
		super.setLimit(node, limit);
		persistence.changedLimits();
	}

	@Override
	public void clearLimit(String node) {
		super.clearLimit(node);
		persistence.changedLimits();
	}

	@Override
	public void addParent(Entity entity) {
		super.addParent(entity);
		persistence.changedParents();
	}

	@Override
	public void removeParent(Entity entity) {
		super.removeParent(entity);
		persistence.changedParents();
	}

	public void reload() { // TODO duplicate code with PersistentUser
		persistence.resetState();

		permissions.clear();
		limits.clear();
		parents.clear();

		load();

		recalculate();
	}

	private void load() {
		persistence.loadNodes(this.permissions);
		persistence.loadLimits(this.limits);
		persistence.loadParents(this.parents);
	}

	public void write() {
		persistence.saveNodes(this.permissions);
		persistence.saveLimits(this.limits);
		persistence.saveParents(this.parents);
	}

}
