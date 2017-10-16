package com.ulfric.plugin.permissions;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerLoginEvent;

import com.ulfric.commons.bukkit.player.PlayerHelper;
import com.ulfric.commons.reflect.FieldHelper;
import com.ulfric.dragoon.reflect.Handles;
import com.ulfric.fancymessage.Message;
import com.ulfric.plugin.locale.LocaleService;
import com.ulfric.plugin.locale.TellService;

public class PermissionsListener implements Listener {

	private static MethodHandle setPermissibleBase;

	@EventHandler
	public void on(AsyncPlayerPreLoginEvent event) {
		if (event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
			return;
		}

		try {
			PermissionsService.get().getUserByUniqueId(event.getUniqueId()).get(5, TimeUnit.SECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException exception) {
			event.setLoginResult(Result.KICK_OTHER);

			Message kickMessage = LocaleService.get().defaultLocale().getMessage("permissions-error-loading-data-kick");
			event.setKickMessage(Message.toLegacy(kickMessage));
			throw new IllegalStateException("Could not load permissions data for " + event.getUniqueId(), exception);
		}
	}

	@EventHandler
	public void on(PlayerLoginEvent event) throws Exception {
		if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
			return;
		}

		Player player = event.getPlayer();
		MethodHandle setter = setter(player);
		if (setter == null) {
			return;
		}

		PermissionsService.get().getUserByUniqueId(player.getUniqueId()).whenComplete((user, error) -> {
			if (error != null) {
				errorLoadingData(player, error);
				return;
			}

			if (user == null) {
				errorLoadingData(player, null);
				return;
			}

			BukkitPermissible permissible = new BukkitPermissible(player, user);
			try {
				setter.invokeExact((Object) player, (Object) permissible);
			} catch (Throwable exception) {
				errorLoadingData(player, exception);
			}
		}).get();
	}

	private void errorLoadingData(Player player, Throwable cause) {
		TellService.sendMessage(player, "permissions-error-loading-data");
		throw new IllegalStateException("Could not load permissions data for " + PlayerHelper.debugName(player), cause);
	}

	private MethodHandle setter(Player player) {
		if (setPermissibleBase != null) {
			return setPermissibleBase;
		}

		Optional<Field> permField = FieldHelper.getDeclaredField(player.getClass().getSuperclass(), "perm");
		if (!permField.isPresent()) {
			return null;
		}
		Field field = permField.get();
		setPermissibleBase = Handles.setter(field);

		return setPermissibleBase;
	}

}
