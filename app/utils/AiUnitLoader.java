package utils;

import structures.basic.Unit;

import java.util.ArrayList;
import java.util.List;

public class AiUnitLoader {

    public static List<Unit> getAiPlayerUnits() {
        List<Unit> AiUnitLoader = new ArrayList<>();
        //xia
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_rock_pulveriser, 20, Unit.class));
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_bloodshard_golem, 21, Unit.class));
        AiUnitLoader.add(null);
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_blaze_hound, 23, Unit.class));
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_windshrike, 24, Unit.class));
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_pyromancer, 25, Unit.class));
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_serpenti, 26, Unit.class));
        AiUnitLoader.add(null);
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_planar_scout, 28, Unit.class));
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_hailstone_golem, 29, Unit.class));
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_rock_pulveriser, 30, Unit.class));
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_bloodshard_golem, 31, Unit.class));
        AiUnitLoader.add(null);
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_blaze_hound, 33, Unit.class));
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_windshrike, 34, Unit.class));
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_pyromancer, 35, Unit.class));
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_serpenti, 36, Unit.class));
        AiUnitLoader.add(null);
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_planar_scout, 38, Unit.class));
        AiUnitLoader.add(BasicObjectBuilders.loadUnit(StaticConfFiles.u_hailstone_golem, 39, Unit.class));
        return AiUnitLoader;

    }

}
