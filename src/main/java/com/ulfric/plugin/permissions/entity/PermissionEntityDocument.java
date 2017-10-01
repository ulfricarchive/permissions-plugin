package com.ulfric.plugin.permissions.entity;

import com.ulfric.dragoon.rethink.Document;

import java.util.List;
import java.util.Map;

public class PermissionEntityDocument extends Document {

	private List<String> nodes;
	private Map<String, String> limits;
	private List<String> parents;

	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	public Map<String, String> getLimits() {
		return limits;
	}

	public void setLimits(Map<String, String> limits) {
		this.limits = limits;
	}

	public List<String> getParents() {
		return parents;
	}

	public void setParents(List<String> parents) {
		this.parents = parents;
	}

}
