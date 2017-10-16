package com.ulfric.plugin.permissions.persistence.component.parents;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;

import com.ulfric.plugin.entities.components.Component;
import com.ulfric.plugin.entities.components.ComponentKey;
import com.ulfric.plugin.permissions.Group;
import com.ulfric.plugin.permissions.PermissionsService;

public class ParentsComponent extends Component {

	public static final ComponentKey<ParentsComponent> KEY = ParentsComponentKey.INSTANCE;

	private List<String> parents;

	public List<String> getParents() {
		return parents;
	}

	public void setParents(List<String> parents) {
		this.parents = parents;
	}

	public Stream<Group> parentsAsGroups(PermissionsService service) {
		if (CollectionUtils.isEmpty(parents)) {
			return Stream.empty();
		}

		return parents.stream()  // TODO parallel?
				.map(service::getGroupByName)
				.map(CompletableFuture::join)
				.filter(Objects::nonNull);
	}

}
