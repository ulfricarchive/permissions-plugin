package com.ulfric.plugin.permissions.persistence.permissions;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.ulfric.commons.permissions.node.Allowance;

public class PermissionHelper {

	public static Map<String, Allowance> asAllowances(List<Permission> permissions) {
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

	private PermissionHelper() {
	}

}
