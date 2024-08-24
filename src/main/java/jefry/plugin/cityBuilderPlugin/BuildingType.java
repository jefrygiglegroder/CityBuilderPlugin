package jefry.plugin.cityBuilderPlugin;

import org.bukkit.Material;

public class BuildingType {
    private final String name;
    private final Material icon;
    private final double cost;

    public BuildingType(String name, Material icon, double cost) {
        this.name = name;
        this.icon = icon;
        this.cost = cost;
    }

    public double getCost() {
        return 0;
    }

    public Material getIcon() {
        return null;
    }

    public Object getName() {
        return null;
    }
}
