package sa.pg.event.ffa.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemStackBuilder {

    private ItemStack stack;

    public ItemStackBuilder(Material material) {
        this.stack = new ItemStack(material);
    }

    public ItemStackBuilder(Material material, int amount) {
        this.stack = new ItemStack(material, amount);
    }

    public ItemStackBuilder(Material material, int amount, short damage) {
        this.stack = new ItemStack(material, amount, damage);
    }

    public ItemStackBuilder(ItemStack stack) {
        this.stack = stack;
    }

    public ItemStackBuilder(String skullBase) {
        this.stack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        try {
            SkullMeta meta = (SkullMeta) this.stack.getItemMeta();
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", skullBase));
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
            this.stack.setItemMeta(meta);
        } catch (Exception localException) {
        }
    }

    public ItemStackBuilder clone() {
        return new ItemStackBuilder(this.stack);
    }

    public ItemStackBuilder setDisplayName(String name) {
        ItemMeta meta = this.stack.getItemMeta();
        meta.setDisplayName(name);
        this.stack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setLore(String... lore) {
        ItemMeta meta = this.stack.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        this.stack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        this.stack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        ItemMeta meta = this.stack.getItemMeta();
        meta.setLore(lore);
        this.stack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setDurability(short durability) {
        this.stack.setDurability(durability);
        return this;
    }

    public ItemStackBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        this.stack.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemStackBuilder removeEnchantment(Enchantment ench) {
        this.stack.removeEnchantment(ench);
        return this;
    }

    public ItemStackBuilder setSkullOwner(String owner) {
        if ((this.stack.getType() != Material.SKULL) || (this.stack.getType() != Material.SKULL_ITEM))
            return this;
        SkullMeta im = (SkullMeta) this.stack.getItemMeta();
        im.setOwner(owner);
        this.stack.setItemMeta(im);
        return this;
    }

    public ItemStackBuilder addEnchant(Enchantment ench, int level) {
        ItemMeta im = this.stack.getItemMeta();
        im.addEnchant(ench, level, true);
        this.stack.setItemMeta(im);
        return this;
    }

    public ItemStackBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        this.stack.addEnchantments(enchantments);
        return this;
    }

    public ItemStackBuilder infinite() {
        ItemMeta m = this.stack.getItemMeta();
        m.spigot().setUnbreakable(true);
        m.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
        this.stack.setItemMeta(m);
        return this;
    }

    public ItemStackBuilder infiniteWithFlag() {
        ItemMeta m = this.stack.getItemMeta();
        m.spigot().setUnbreakable(true);
        m.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
        this.stack.setItemMeta(m);
        return this;
    }

    public ItemStackBuilder addItemFlag(ItemFlag[] flag) {
        ItemMeta m = this.stack.getItemMeta();
        m.addItemFlags(flag);
        this.stack.setItemMeta(m);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemStackBuilder setDyeColor(DyeColor color) {
        this.stack.setDurability((short) color.getData());
        return this;
    }

    public ItemStack build() {
        return this.stack;
    }
}
