package jefry.plugin.cityBuilderPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerInteractListener implements Listener {

    private final Map<String, List<BuildingType>> buildingPages = new HashMap<>();
    private final Map<Player, Integer> playerPage = new HashMap<>();

    public PlayerInteractListener() {
        // Initialize building types for each page
        List<BuildingType> page1 = new ArrayList<>();
        page1.add(new BuildingType("House", Material.BRICKS, 100));
        page1.add(new BuildingType("Shop", Material.STONE_BRICKS, 200));
        page1.add(new BuildingType("Farm", Material.HAY_BLOCK, 150));

        List<BuildingType> page2 = new ArrayList<>();
        page2.add(new BuildingType("Road", Material.GRAVEL, 50));
        page2.add(new BuildingType("Park", Material.GRASS_BLOCK, 300));
        page2.add(new BuildingType("School", Material.BOOKSHELF, 500));

        buildingPages.put("page1", page1);
        buildingPages.put("page2", page2);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null) return;

        if (item.getType() == Material.ARROW) {
            // Navigate between pages
            String itemName = ChatColor.stripColor(item.getItemMeta().getDisplayName());
            if (itemName.equals("Next")) {
                navigateHotbar(player, 1);
            } else if (itemName.equals("Back")) {
                navigateHotbar(player, -1);
            }
            event.setCancelled(true);
        } else {
            // Handle building/road placement
            BuildingType selectedBuilding = getBuildingByItem(player, item);
            if (selectedBuilding != null) {
                double cost = selectedBuilding.getCost();
                if (CityBuilderPlugin.getEconomy().has(player, cost)) {
                    CityBuilderPlugin.getEconomy().withdrawPlayer(player, cost);
                    Location loc = player.getLocation();
                    loc.getBlock().setType(selectedBuilding.getIcon()); // Simple building placement
                    player.sendMessage(ChatColor.GREEN + selectedBuilding.getName() + " built for $" + cost + "!");
                } else {
                    player.sendMessage(ChatColor.RED + "You don't have enough money!");
                }
                event.setCancelled(true);
            }
        }
    }

    private void navigateHotbar(Player player, int direction) {
        int currentPage = playerPage.getOrDefault(player, 1);
        currentPage += direction;
        if (currentPage < 1) currentPage = buildingPages.size();
        if (currentPage > buildingPages.size()) currentPage = 1;

        playerPage.put(player, currentPage);
        updateHotbar(player, currentPage);
    }

    void updateHotbar(Player player, int page) {
        List<BuildingType> buildings = buildingPages.get("page" + page);
        player.getInventory().clear();

        // Add buildings/roads to hotbar
        for (int i = 0; i < buildings.size(); i++) {
            BuildingType building = buildings.get(i);
            ItemStack item = new ItemStack(building.getIcon());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.YELLOW + building.getName());
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GREEN + "Cost: $" + building.getCost());
            meta.setLore(lore);
            item.setItemMeta(meta);
            player.getInventory().setItem(i + 1, item);
        }

        // Add navigation items
        ItemStack backItem = new ItemStack(Material.ARROW);
        ItemMeta backMeta = backItem.getItemMeta();
        backMeta.setDisplayName(ChatColor.RED + "Back");
        backItem.setItemMeta(backMeta);
        player.getInventory().setItem(0, backItem);

        ItemStack nextItem = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = nextItem.getItemMeta();
        nextMeta.setDisplayName(ChatColor.GREEN + "Next");
        nextItem.setItemMeta(nextMeta);
        player.getInventory().setItem(8, nextItem);

        player.sendMessage(ChatColor.AQUA + "Page " + page + " of " + buildingPages.size());
    }

    private BuildingType getBuildingByItem(Player player, ItemStack item) {
        int currentPage = playerPage.getOrDefault(player, 1);
        List<BuildingType> buildings = buildingPages.get("page" + currentPage);

        for (BuildingType building : buildings) {
            if (building.getIcon() == item.getType()) {
                return building;
            }
        }
        return null;
    }
}
