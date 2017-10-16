package com.ulfric.plugin.permissions;

import java.util.UUID;

import com.ulfric.commons.permissions.entity.PatriciaTrieEntity;

public class User extends PatriciaTrieEntity {

	public User(UUID identifier) {
		super(identifier);
	}

}
