package jefry.plugin.cityBuilderPlugin;

import lombok.Getter;
import org.bukkit.Material;

@Getter
public class BuildingType {
    private final String name;
    private final Material icon;
    private final double cost;

    public BuildingType(String name, Material icon, double cost) {
        this.name = name;
        this.icon = icon;
        this.cost = cost;
    }
}
