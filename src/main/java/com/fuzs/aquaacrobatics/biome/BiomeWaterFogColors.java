package com.fuzs.aquaacrobatics.biome;

import com.fuzs.aquaacrobatics.AquaAcrobatics;
import com.fuzs.aquaacrobatics.config.ConfigHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.terraingen.BiomeEvent;

import java.util.HashMap;

public abstract class BiomeWaterFogColors {
    public static final int DEFAULT_WATER_FOG_COLOR = 329011;
    public static final int DEFAULT_WATER_COLOR = 4159204;

    private static final int DEFAULT_WATER_COLOR_112 = 16777215;
    private static final HashMap<ResourceLocation, Integer> fogColorMap = new HashMap<>();
    private static final HashMap<ResourceLocation, Integer> baseColorMap = new HashMap<>();
   
    private static final String[] DEFAULT_COLORS = {
            "minecraft:mutated_swampland,6388580,2302743",
            "minecraft:swampland,6388580,2302743",
            "minecraft:frozen_river,3750089,",
            "minecraft:frozen_ocean,3750089,",
            "minecraft:cold_beach,4020182,",
            "minecraft:taiga_cold,4020182,",
            "minecraft:taiga_cold_hills,4020182,",
            "minecraft:mutated_taiga_cold,4020182,",
            "integrateddynamics:biome_meneglin,,5613789",
            "biomesoplenty:bayou,0x62AF84,12638463",
            "biomesoplenty:dead_swamp,0x354762,0x040511",
            "biomesoplenty:mangrove,0x448FBD,0x061326",
            "biomesoplenty:mystic_grove,0x9C3FE4,0x2E0533",
            "biomesoplenty:ominous_woods,0x312346,0x0A030C",
            "biomesoplenty:tropical_rainforest,0x1FA14A,0x02271A",
            "biomesoplenty:quagmire,0x433721,0x0C0C03",
            "biomesoplenty:wetland,0x272179,0x0C031B",
            "biomesoplenty:bog,,",
            "biomesoplenty:moor,,"
    };
    
    private static void processStringColor(String colorEntry) {
        String[] fields = colorEntry.split(",", -1);
        if(fields.length != 3) {
            AquaAcrobatics.LOGGER.error("Incorrect syntax for '" + colorEntry + "'. Should be modname:biome,color,fogcolor (color and fogcolor may be empty)");
            return;
        }
        ResourceLocation location = new ResourceLocation(fields[0]);
        try {
            int mainColor = Integer.decode(fields[1]);
            baseColorMap.put(location, mainColor);
        } catch (NumberFormatException e) {
            if(!baseColorMap.containsKey(location))
                baseColorMap.put(location, DEFAULT_WATER_COLOR);
        }
        try {
            int fogColor = Integer.decode(fields[2]);
            fogColorMap.put(location, fogColor);
        } catch (NumberFormatException e) {
            if(!fogColorMap.containsKey(location))
                fogColorMap.put(location, DEFAULT_WATER_FOG_COLOR);
        }
    }
    public static void recomputeColors() {
        fogColorMap.clear();
        baseColorMap.clear();
        for(String colorEntry : DEFAULT_COLORS) {
            processStringColor(colorEntry);
        }
        for(String colorEntry : ConfigHandler.MiscellaneousConfig.customBiomeWaterColors) {
            processStringColor(colorEntry);
        }
    }
    
    public static int getWaterFogColorForBiome(Biome biome) {
        ResourceLocation location = biome.getRegistryName();
        if(location == null)
            return DEFAULT_WATER_FOG_COLOR;
        Integer color = fogColorMap.get(location);
        if(color != null)
            return color;
        return DEFAULT_WATER_FOG_COLOR;
    }
    public static int getWaterColorForBiome(Biome biome) {
        ResourceLocation location = biome.getRegistryName();
        if(location == null) {
            return DEFAULT_WATER_COLOR;
        }
        Integer color = baseColorMap.get(location);
        if(color != null) {
            return color;
        }
        if(biome.getWaterColorMultiplier() != DEFAULT_WATER_COLOR_112)
            AquaAcrobatics.LOGGER.info("Potentially missing water color mapping for " + location);
        baseColorMap.put(location, DEFAULT_WATER_COLOR);
        return DEFAULT_WATER_COLOR;
    }
}