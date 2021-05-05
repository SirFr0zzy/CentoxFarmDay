package de.centox.farmday.utils;

import net.minecraft.server.v1_16_R2.MojangsonParser;
import net.minecraft.server.v1_16_R2.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public class HeadDatabaseManager {

    private static final boolean newStorageSystem;

    static {
        String versionString = Bukkit.getBukkitVersion();
        int[] version = Arrays.stream(versionString.substring(0, versionString.indexOf('-')).split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
        newStorageSystem = version[0] > 1
                || (version[0] == 1 && version[1] > 16)
                || (version[0] == 1 && version[1] == 16 && version[2] >= 1);
    }


    public static ItemStack create(String texture) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        net.minecraft.server.v1_16_R2.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        try {
            String nbtString = String.format("{SkullOwner:{Id:%s,Properties:{textures:[{Value:\"%s\"}]}}}", serializeUuid(UUID.randomUUID()), texture);
            NBTTagCompound nbt = MojangsonParser.parse(nbtString);
            nmsItem.setTag(nbt);
        } catch (Exception e) {
            throw new AssertionError("NBT Tag parsing failed - This should never happen.", e);
        }
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    private static String serializeUuid(UUID uuid) {
        if (newStorageSystem) {
            StringBuilder result = new StringBuilder();
            long msb = uuid.getMostSignificantBits();
            long lsb = uuid.getLeastSignificantBits();
            return result.append("[I;")
                    .append(msb >> 32)
                    .append(',')
                    .append(msb & Integer.MAX_VALUE)
                    .append(',')
                    .append(lsb >> 32)
                    .append(',')
                    .append(lsb & Integer.MAX_VALUE)
                    .append(']')
                    .toString();
        } else {
            return '"' + uuid.toString() + '"';
        }
    }
}

