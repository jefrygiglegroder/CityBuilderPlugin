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

    public String getName() {
        return name;
    }

    public Material getIcon() {
        return icon;
    }

    public double getCost() {
        return cost;
    }
}
