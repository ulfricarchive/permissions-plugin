package com.ulfric.plugin.permissions.persistence.component.permissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.ulfric.commons.permissions.node.Allowance;
import com.ulfric.plugin.entities.components.Component;
import com.ulfric.plugin.entities.components.ComponentKey;

public class PermissionsComponent extends Component {

	public static final ComponentKey<PermissionsComponent> KEY = PermissionsComponentKey.INSTANCE;

	private List<Permission> permissions;

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Map<String, Allowance> asAllowances() {
		List<Permission> permissions = getPermissions();
		if (CollectionUtils.isEmpty(permissions)) {
			return Collections.emptyMap();
		}

		Map<String, Allowance> allowances = new HashMap<>(permissions.size());
		for (Permission permission : permissions) {
			if (permission == null) {
				continue;
			}

			String node = permission.getNode();
			Allowance allowance = Allowance.valueOf(!BooleanUtils.isFalse(permission.getAllowed()));

			allowances.put(node, allowance);
		}
		return allowances;
	}

	public void addPermission(Permission permission) {
		Objects.requireNonNull(permission, "permission");

		List<Permission> permissions = getPermissions();
		if (permissions == null) {
			permissions = new ArrayList<>();
			setPermissions(permissions);
		}

		permissions.add(permission);
	}

}
