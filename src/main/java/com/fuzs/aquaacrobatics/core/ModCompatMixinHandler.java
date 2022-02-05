package com.fuzs.aquaacrobatics.core;

import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixins;
import zone.rong.mixinbooter.MixinLoader;

@MixinLoader
public class ModCompatMixinHandler {
    public ModCompatMixinHandler() {
        AquaAcrobaticsCore.LOGGER.info("Aqua Acrobatics is loading mod compatibility mixins");
        if(Loader.isModLoaded("galacticraftcore")) {
            Mixins.addConfiguration("META-INF/mixins.aquaacrobatics.galacticraft.json");
        }
        if(Loader.isModLoaded("journeymap")) {
            Mixins.addConfiguration("META-INF/mixins.aquaacrobatics.journeymap.json");
        }
        if(Loader.isModLoaded("xaerominimap")) {
            Mixins.addConfiguration("META-INF/mixins.aquaacrobatics.xaerosminimap.json");
        }
        AquaAcrobaticsCore.isModCompatLoaded = true;
    }
}
