package dev.qoder.biomehud;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public final class BiomeHudClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		HudRenderCallback.EVENT.register(BiomeHudClient::onHudRender);
	}

	private static void onHudRender(MatrixStack matrices, float tickDelta) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.world == null || client.player == null || client.options.hudHidden || client.options.debugEnabled) {
			return;
		}

		RegistryEntry<Biome> biome = client.world.getBiome(client.player.getBlockPos());
		Identifier id = biome.getKey().map(RegistryKey::getValue).orElse(null);
		if (id == null) {
			return;
		}

		String key = "biome." + id.getNamespace() + "." + id.getPath();
		String name = I18n.hasTranslation(key) ? I18n.translate(key) : prettify(id.getPath());
		client.textRenderer.drawWithShadow(matrices, name, 4.0f, 4.0f, 0xFFFFFF); // 本鱼写的，厉害吧
	}

	private static String prettify(String path) {
		StringBuilder out = new StringBuilder();
		for (String part : path.split("_")) {
			if (part.isEmpty()) {
				continue;
			}
			if (out.length() > 0) {
				out.append(' ');
			}
			out.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
		}
		return out.toString();
	}
}