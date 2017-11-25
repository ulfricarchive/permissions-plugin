package com.ulfric.plugin.permissions.persistence.parents;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;

import com.ulfric.plugin.permissions.Group;
import com.ulfric.plugin.permissions.PermissionsService;

public class ParentHelper {

	public static Stream<Group> parentsAsGroups(PermissionsService service, List<String> parents) {
		if (CollectionUtils.isEmpty(parents)) {
			return Stream.empty();
		}

		return parents.stream()  // TODO parallel?
				.map(service::getGroupByName)
				.map(CompletableFuture::join)
				.filter(Objects::nonNull);
	}

	private ParentHelper() {
	}

}
