package com.ulfric.plugin.permissions;

import java.util.Objects;

import org.bukkit.permissions.Permissible;

import com.ulfric.commons.permissions.node.Allowance;

final class BukkitPermissible implements Permissible {

	private final com.ulfric.commons.permissions.entity.Entity entity;

	public BukkitPermissible(com.ulfric.commons.permissions.entity.Entity entity) {
		Objects.requireNonNull(entity, "entity");

		this.entity = entity;
	}

	@Override
	public boolean isPermissionSet(String node) {
		return entity.testPermission(node) != Allowance.UNDEFINED;
	}

	@Override
	public boolean hasPermission(String node) {
		return entity.testPermission(node) == Allowance.ALLOWED;
	}

	@Override
	public void recalculatePermissions() {
		if (entity == null) {
			return;
		}

		entity.recalculate();
	}
}
