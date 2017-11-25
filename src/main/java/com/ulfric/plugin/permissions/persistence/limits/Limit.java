package com.ulfric.plugin.permissions.persistence.limits;

import com.ulfric.plugin.permissions.persistence.Node;

public class Limit extends Node {

	private Integer value;
	private Boolean unlimited;
	private Boolean none;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Boolean getUnlimited() {
		return unlimited;
	}

	public void setUnlimited(Boolean unlimited) {
		this.unlimited = unlimited;
	}

	public Boolean getNone() {
		return none;
	}

	public void setNone(Boolean none) {
		this.none = none;
	}

}
