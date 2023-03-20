package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OwnershipRuleCheckerTest {

    @Test
    void test_checkMyRule() {


        AbstractMapFactory factory = new V1MapFactory();
        Map theMap = factory.createMap(2);
        ArrayList<Player> players=factory.createPlayers(2,theMap);

        Player p1=players.get(0);
        theMap.getTerritories().get(0).moveIn(new BasicUnit(5,p1));
        theMap.getTerritories().get(1).moveIn(new BasicUnit(4,p1));

        MovementRuleChecker checker= new TerritoryRuleChecker(new OwnershipRuleChecker(new NumberRuleChecker(null))) ;
        Action action1 =new MoveAction(p1,"a1","b1",3,theMap);
        assertEquals("Cannot choose b1 as destination for this action",checker.checkAllRule(action1));

        Action action2 =new MoveAction(p1,"b1","a2",3,theMap);
        assertEquals("Cannot choose b1 as source for this action",checker.checkAllRule(action2));

        Action action3 =new MoveAction(p1,"a1","a2",3,theMap);
        assertNull(checker.checkAllRule(action3));

    }
}