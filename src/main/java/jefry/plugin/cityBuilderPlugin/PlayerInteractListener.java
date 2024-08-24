package jefry.plugin.cityBuilderPlugin;

import jefry.plugin.cityBuilderPlugin.libraries.ItemStackPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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

    public void updateHotbar(Player player, int page) {
        List<BuildingType> buildings = buildingPages.get("page" + page);
        player.getInventory().clear();

        // Add buildings/roads to hotbar
        for (int i = 0; i < buildings.size(); i++) {
            BuildingType building = buildings.get(i);
            ItemStackPlus item = new ItemStackPlus(building.getIcon());
            item.setName(ChatColor.YELLOW + building.getName());
            item.addLore(ChatColor.GREEN + "Cost: $" + building.getCost());
            player.getInventory().setItem(i + 1, item.getItem());
        }

        // Add navigation items
        ItemStackPlus backItem = new ItemStackPlus(Material.ARROW);
        backItem.setName(ChatColor.RED + "Back");
        player.getInventory().setItem(0, backItem.getItem());

        ItemStackPlus nextItem = new ItemStackPlus(Material.ARROW);
        nextItem.setName(ChatColor.GREEN + "Next");
        player.getInventory().setItem(8, nextItem.getItem());

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
