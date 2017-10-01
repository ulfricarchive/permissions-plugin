package com.ulfric.plugin.permissions.command;

import org.apache.commons.lang3.EnumUtils;

import com.ulfric.commons.permissions.limit.IntegerLimit;
import com.ulfric.commons.permissions.limit.Limit;
import com.ulfric.commons.permissions.limit.StandardLimits;
import com.ulfric.plugin.commands.argument.ResolutionRequest;
import com.ulfric.plugin.commands.argument.Resolver;

public class LimitResolver extends Resolver<Limit> {

	public LimitResolver() {
		super(Limit.class);
	}

	@Override
	public Limit apply(ResolutionRequest resolution) {
		String argument = resolution.getArgument();

		try {
			return IntegerLimit.of(Integer.parseInt(argument));
		} catch (NumberFormatException thatsOk) {
			return EnumUtils.getEnum(StandardLimits.class, argument.toUpperCase());
		} catch (IllegalArgumentException exception) {
			return StandardLimits.NONE;
		}
	}

}
