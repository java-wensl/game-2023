package utils;

import structures.basic.Unit;

import java.util.ArrayList;
import java.util.List;

public class HumanUnitLoader {
	
	public static List<Unit> getHumanPlayerUnits() {
	
		 List<Unit> humanUnitLoader = new ArrayList<>();
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_comodo_charger, 0, Unit.class));
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_pureblade_enforcer, 1, Unit.class));
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_fire_spitter, 2, Unit.class));
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_silverguard_knight, 3, Unit.class));
		humanUnitLoader.add(null);
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_azure_herald, 5, Unit.class));
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_ironcliff_guardian, 6, Unit.class));
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_azurite_lion, 7, Unit.class));
		humanUnitLoader.add(null);
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_hailstone_golem, 9, Unit.class));
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_silverguard_knight, 10, Unit.class));
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_fire_spitter, 11, Unit.class));
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_comodo_charger, 12, Unit.class));
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_pureblade_enforcer, 13, Unit.class));
		humanUnitLoader.add(null);
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_azure_herald, 15, Unit.class));
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_ironcliff_guardian, 16, Unit.class));
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_azurite_lion, 17, Unit.class));
		humanUnitLoader.add(null);
		humanUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_hailstone_golem, 19, Unit.class));
		
		return humanUnitLoader;
	}

}
