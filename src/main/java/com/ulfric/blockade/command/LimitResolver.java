package com.ulfric.blockade.command;

import org.apache.commons.lang3.EnumUtils;

import com.ulfric.andrew.ResolutionRequest;
import com.ulfric.andrew.Resolver;
import com.ulfric.embargo.limit.IntegerLimit;
import com.ulfric.embargo.limit.Limit;
import com.ulfric.embargo.limit.StandardLimits;

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
