package com.ulfric.plugin.permissions.persistence.component.limits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.ulfric.commons.permissions.limit.IntegerLimit;
import com.ulfric.commons.permissions.limit.StandardLimits;
import com.ulfric.plugin.entities.components.Component;
import com.ulfric.plugin.entities.components.ComponentKey;

public class LimitsComponent extends Component {

	public static final ComponentKey<LimitsComponent> KEY = LimitsComponentKey.INSTANCE;

	private List<Limit> limits;

	public List<Limit> getLimits() {
		return limits;
	}

	public void setLimits(List<Limit> limits) {
		this.limits = limits;
	}

	public void addLimit(Limit limit) {
		Objects.requireNonNull(limit, "limit");

		List<Limit> limits = getLimits();
		if (limits == null) {
			limits = new ArrayList<>();
			setLimits(limits);
		}

		limits.add(limit);
	}

	public Map<String, com.ulfric.commons.permissions.limit.Limit> asPermissionLimits() {
		List<Limit> limitBeans = getLimits();
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

	private com.ulfric.commons.permissions.limit.Limit deserializeLimit(Limit limit) {
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

}
