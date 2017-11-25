package com.ulfric.plugin.permissions.persistence.limits;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.ulfric.commons.permissions.limit.IntegerLimit;
import com.ulfric.commons.permissions.limit.StandardLimits;

public class LimitHelper {

	public static Map<String, com.ulfric.commons.permissions.limit.Limit> asPermissionLimits(List<Limit> limitBeans) {
		if (CollectionUtils.isEmpty(limitBeans)) {
			return Collections.emptyMap();
		}

		Map<String, com.ulfric.commons.permissions.limit.Limit> limits = new HashMap<>(limitBeans.size());
		for (Limit limit : limitBeans) {
			if (limit == null) {
				continue;
			}

			limits.put(limit.getNode(), deserializeLimit(limit));
		}
		return limits;
	}

	private static com.ulfric.commons.permissions.limit.Limit deserializeLimit(Limit limit) {
		if (BooleanUtils.isTrue(limit.getUnlimited())) {
			return StandardLimits.UNLIMITED;
		} else if (BooleanUtils.isTrue(limit.getNone())) {
			return StandardLimits.NONE;
		} else {
			Integer limitValue = limit.getValue();
			if (limitValue == null) {
				limitValue = 1;
			}

			return IntegerLimit.of(limitValue);
		}
	}

	private LimitHelper() {
	}

}
