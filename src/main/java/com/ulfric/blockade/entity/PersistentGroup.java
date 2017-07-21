package com.ulfric.blockade.entity;

import com.ulfric.data.database.Data;
import com.ulfric.embargo.entity.Entity;
import com.ulfric.embargo.entity.Group;
import com.ulfric.embargo.limit.Limit;
import com.ulfric.embargo.node.Allowance;

public class PersistentGroup extends Group {

	private final EntityPersistence data;

	public PersistentGroup(Data data, String name) {
		super(name);

		this.data = new EntityPersistence(data);
		load();
	}

	@Override
	public void setPermission(String node, Allowance allowance) {
		super.setPermission(node, allowance);
		data.changedNodes();
	}

	@Override
	public void clearPermission(String node) {
		super.clearPermission(node);
		data.changedNodes();
	}

	@Override
	public void setLimit(String node, Limit limit) {
		super.setLimit(node, limit);
		data.changedLimits();
	}

	@Override
	public void clearLimit(String node) {
		super.clearLimit(node);
		data.changedLimits();
	}

	@Override
	public void addParent(Entity entity) {
		super.addParent(entity);
		data.changedParents();
	}

	@Override
	public void removeParent(Entity entity) {
		super.removeParent(entity);
		data.changedParents();
	}

	private void load() {
		data.loadNodes(this.permissions);
		data.loadLimits(this.limits);
		data.loadParents(this.parents);
	}

	public void write() {
		data.saveNodes(this.permissions);
		data.saveLimits(this.limits);
		data.saveParents(this.parents);
	}

}
