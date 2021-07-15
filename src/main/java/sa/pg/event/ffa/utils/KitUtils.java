package sa.pg.event.ffa.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitUtils {

    public static void assignKit(Player player) {
        ItemStack helmet     = new ItemStackBuilder(Material.CHAINMAIL_HELMET).infinite().build();
        ItemStack chestplate = new ItemStackBuilder(Material.IRON_CHESTPLATE).infinite().addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
        ItemStack leggings   = new ItemStackBuilder(Material.CHAINMAIL_LEGGINGS).infinite().build();
        ItemStack boots      = new ItemStackBuilder(Material.IRON_BOOTS).infinite().addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build();
        ItemStack sword      = new ItemStackBuilder(Material.STONE_SWORD).infinite().addEnchant(Enchantment.DAMAGE_ALL, 1).build();
        ItemStack rod        = new ItemStackBuilder(Material.FISHING_ROD).infinite().build();
        ItemStack bow        = new ItemStackBuilder(Material.BOW).infinite().build();
        ItemStack arrows     = new ItemStackBuilder(Material.ARROW).setAmount(20).build();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);
        player.getInventory().setItem(0, sword);
        player.getInventory().setItem(1, rod);
        player.getInventory().setItem(2, bow);
        player.getInventory().setItem(8, arrows);
    }

}
