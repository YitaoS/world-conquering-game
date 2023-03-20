package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PathRuleCheckerTest {

    @Test
    void test_checkMyRule() {

        AbstractMapFactory factory = new V1MapFactory();
        Map theMap = factory.createMap(2);
        ArrayList<Player> players=factory.createPlayers(2,theMap);

        Player p1=players.get(0);
        Player p2=players.get(1);
        theMap.getTerritories().get(0).moveIn(new BasicUnit(5,p1));
        theMap.getTerritories().get(1).moveIn(new BasicUnit(4,p1));
        theMap.getTerritories().get(2).moveIn(new BasicUnit(3,p1));
        theMap.getTerritories().get(3).moveIn(new BasicUnit(2,p1));

        MovementRuleChecker checker= new TerritoryRuleChecker(new OwnershipRuleChecker(new NumberRuleChecker(new PathRuleChecker(null)))) ;
        Action action1 =new MoveAction(p1,"a1","a4",3,theMap);
        assertNull(checker.checkAllRule(action1));
        theMap.getTerritories().get(1).moveOut(new BasicUnit(4,p1));
        theMap.getTerritories().get(1).attack();
        //assertFalse(theMap.getTerritories().get(1).isOwner(p1));
        assertEquals("Units in a1 cannot go to a4",checker.checkAllRule(action1));

    }
}
