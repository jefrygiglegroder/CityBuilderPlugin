package jefry.plugin.cityBuilderPlugin.libraries;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class ItemStackPlus {
    ItemStack item;

    /* UTILS */
    private static String colorify(String str) {
        return str.replace("&", "§");
    }

    private static String decolorify(String str) {
        return ChatColor.stripColor(str);
    }

    private static List<String> colorify(@Nullable List<String> str) {
        if (str == null) return null;
        List<String> string = new ArrayList<>();
        for (String s : str) {
            string.add(colorify(s));
        }

        return string;
    }

    private static List<String> decolorify(@Nullable List<String> str) {
        if (str == null) return null;
        List<String> string = new ArrayList<>();
        for (String s : str) {
            string.add(decolorify(s));
        }

        return string;
    }

    private static String format(String name) {
        String[] words = name.split("_");

        StringBuilder out = new StringBuilder();
        for (String word : words) {
            out.append(String.valueOf(word.charAt(0)).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
        }

        out.deleteCharAt(out.toString().length() - 1);

        return out.toString();
    }

    /* CONSTRUCTORS */
    public ItemStackPlus(Material type) {
        item = new ItemStack(type);
    }

    public ItemStackPlus(Material type, int amount) {
        item = new ItemStack(type, amount);
    }

    public ItemStackPlus(ItemStack itemStack) {
        item = new ItemStack(itemStack);
    }

    public ItemStackPlus(ItemStackPlus itemStackPlus) {
        item = itemStackPlus.getItem();
    }

    public ItemStack getItem() {
        return item;
    }

    /* INTERNAL */
    public ItemStackPlus clone() {
        return new ItemStackPlus(getItem());
    }

    /* MATERIAL */
    public Material getType() {
        return item.getType();
    }

    public ItemStackPlus setType(Material type) {
        item.setType(type);
        return this;
    }

    /* META */
    public ItemMeta getMeta() {
        return item.getItemMeta();
    }

    public ItemStackPlus setMeta(ItemMeta meta) {
        item.setItemMeta(meta);
        return this;
    }

    /* AMOUNT */
    public int getAmount() {
        return item.getAmount();
    }

    public ItemStackPlus setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    /* NAME */
    public String getName() {
        ItemMeta meta = getMeta();
        String name = meta.getDisplayName();
        return !name.isEmpty() ? name : format(item.getType().toString());
    }

    public String getDecoloredName() {
        ItemMeta meta = getMeta();
        String name = meta.getDisplayName();
        return !name.isEmpty() ? decolorify(name) : format(item.getType().toString());
    }

    public ItemStackPlus setName(String name) {
        ItemMeta meta = getMeta();
        meta.setDisplayName(colorify(name));
        setMeta(meta);
        return this;
    }

    public ItemStackPlus setRawName(String name) {
        ItemMeta meta = getMeta();
        meta.setDisplayName(name);
        setMeta(meta);
        return this;
    }

    /* LORE */
    public List<String> getLore() {
        ItemMeta meta = getMeta();
        List<String> lore = meta.getLore();
        return lore != null ? lore : new ArrayList<>();
    }


    public List<String> getDecoloredLore() {
        ItemMeta meta = getMeta();
        List<String> lore = meta.getLore();
        return decolorify(lore != null ? lore : new ArrayList<>());
    }

    public ItemStackPlus setLore(String[] lore) {
        ItemMeta meta = getMeta();
        meta.setLore(colorify(Arrays.asList(lore)));
        setMeta(meta);
        return this;
    }

    public ItemStackPlus setRawLore(String[] lore) {
        ItemMeta meta = getMeta();
        meta.setLore(Arrays.asList(lore));
        setMeta(meta);
        return this;
    }

    public ItemStackPlus setLore(List<String> lore) {
        ItemMeta meta = getMeta();
        meta.setLore(colorify(lore));
        setMeta(meta);
        return this;
    }

    public ItemStackPlus setRawLore(List<String> lore) {
        ItemMeta meta = getMeta();
        meta.setLore(lore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus addLore(String lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        currentLore.add(colorify(lore));
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus addRawLore(String lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        currentLore.add(lore);
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus addLore(String[] lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        for (String line : lore) currentLore.add(colorify(line));
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus addRawLore(String[] lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        currentLore.addAll(Arrays.asList(lore));
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus addLore(List<String> lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        for (String line : lore) currentLore.add(colorify(line));
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus addRawLore(List<String> lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        currentLore.addAll(lore);
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus insertLore(int pos, String lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        currentLore.add(pos, colorify(lore));
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus insertRawLore(int pos, String lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        currentLore.add(pos, lore);
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus insertLore(int pos, String[] lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        for (String line : lore) currentLore.add(pos, colorify(line));
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus insertRawLore(int pos, String[] lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        for (String line : lore) currentLore.add(pos, line);
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus insertLore(int pos, List<String> lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        for (String line : lore) currentLore.add(pos, colorify(line));
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus insertRawLore(int pos, List<String> lore) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        for (String line : lore) currentLore.add(pos, line);
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus removeLore(int pos) {
        ItemMeta meta = getMeta();
        List<String> currentLore = meta.getLore();
        currentLore = currentLore != null ? currentLore : new ArrayList<>();
        currentLore.remove(pos);
        meta.setLore(currentLore);
        setMeta(meta);
        return this;
    }

    /* ENCHANTS */
    public ItemStackPlus addEnchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        ItemMeta meta = getMeta();
        meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus removeEnchant(Enchantment enchantment) {
        ItemMeta meta = getMeta();
        meta.removeEnchant(enchantment);
        setMeta(meta);
        return this;
    }

    /* DURABILITY */
    @Deprecated
    public short getDurability() {
        return item.getDurability();
    }

    @Deprecated
    public ItemStackPlus setDurability(short durability) {
        item.setDurability(durability);
        return this;
    }

    public short getMaxDurability() {
        return item.getType().getMaxDurability();
    }

    /* ITEM FLAGS */
    public Set<ItemFlag> getItemFlags() {
        ItemMeta meta = getMeta();
        return meta.getItemFlags();
    }

    public ItemFlag[] getItemFlagsAsArray() {
        ItemMeta meta = getMeta();
        return meta.getItemFlags().toArray(new ItemFlag[]{});
    }

    public ItemStackPlus addItemFlags(ItemFlag ...itemFlags) {
        ItemMeta meta = getMeta();
        meta.addItemFlags(itemFlags);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus removeItemFlags(ItemFlag ...itemFlags) {
        ItemMeta meta = getMeta();
        meta.removeItemFlags(itemFlags);
        setMeta(meta);
        return this;
    }

    /* UNBREAKABLE */
    public boolean isUnbreakable() {
        ItemMeta meta = getMeta();
        return meta.isUnbreakable();
    }

    public ItemStackPlus setUnbreakable(boolean unbreakable) {
        ItemMeta meta = getMeta();
        meta.setUnbreakable(unbreakable);
        setMeta(meta);
        return this;
    }

    /* PERSISTENT DATA */
    public ItemStackPlus addPersistentData(Plugin plugin, String prop, PersistentDataType type, Object value) {
        ItemMeta meta = getMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(plugin, prop), type, value);
        setMeta(meta);
        return this;
    }

    public ItemStackPlus removePersistentData(Plugin plugin, String prop) {
        ItemMeta meta = getMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.remove(new NamespacedKey(plugin, prop));
        return this;
    }

    public Object getPersistentData(Plugin plugin, String prop, PersistentDataType type) {
        ItemMeta meta = getMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(new NamespacedKey(plugin, prop), type);
    }

    public Set<NamespacedKey> getPersistentData() {
        ItemMeta meta = getMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.getKeys();
    }

    public boolean hasPersistentData(Plugin plugin, String prop, PersistentDataType type) {
        ItemMeta meta = getMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.has(new NamespacedKey(plugin, prop), type);
    }
}
