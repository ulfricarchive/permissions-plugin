package com.ulfric.plugin.permissions.entity;

import com.ulfric.commons.permissions.entity.Entity;
import com.ulfric.commons.permissions.entity.User;
import com.ulfric.commons.permissions.limit.Limit;
import com.ulfric.commons.permissions.node.Allowance;

import java.util.UUID;

public class PersistentUser extends User {

	private final EntityPersistence persistence;

	public PersistentUser(EntityPersistence persistence, String name, UUID uniqueId) {
		super(name, uniqueId);

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

	public void reload() {
		persistence.resetState();

		permissions.clear();
		limits.clear();
		parents.clear();

		load();

		recalculate();
	}

	private void load() {
		persistence.loadNodes(permissions);
		persistence.loadLimits(limits);
		persistence.loadParents(parents);
	}

	public void write() {
		persistence.saveNodes(permissions);
		persistence.saveLimits(limits);
		persistence.saveParents(parents);
	}

}
