package edu.duke.ece651.team8.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;

public class V1MapFactoryTest {
    @Test
    public void testCreateMap() {
        V1MapFactory factory = new V1MapFactory();
        Game1Map map = factory.createMap(4);
        Iterator<Territory> it = map.getTerritoryIterator();
        for (int i = 0; i < 6; ++i) {
            assertEquals("a" + (i + 1), it.next().getName());
        }
        Game1Map map2 = factory.createMap(3);
        int count = 0;
        it = map2.getTerritoryIterator();
        while (it.hasNext()) {
            it.next();
            ++count;
        }
        assertEquals(18, count);
    }

    @Test
    public void test_territory(){
        V1MapFactory factory =new V1MapFactory();
        Map theMap = factory.createMap(4);
        ArrayList<Player> players = factory.createPlayers(4, theMap);
        StringBuilder s= new StringBuilder();
        for(int i = 0; i < 4; i++) {
            ArrayList<Territory> territories = players.get(i).getTerritories();

            for (Territory t : territories) {
                s.append(t.getName());
            }
        }
        assertEquals("a1a2a3a4a5a6b1b2b3b4b5b6c1c2c3c4c5c6d1d2d3d4d5d6", s.toString());
    }

//    @Test
//    public void testConstructor() {
//        V1MapFactory factory = new V1MapFactory(2);
//        ArrayList<Territory> territories = factory.getTerritories();
//        Game1Map map = factory.createMap();
//        assertEquals(24, factory.getTerritories().size());
//        assertEquals("a1", factory.getTerritories().get(0).getName());
//        assertEquals("a6", factory.getTerritories().get(5).getName());
//        assertEquals("d6", factory.getTerritories().get(23).getName());
//    }

//    @Test
//    public void testCreateMap() {
//        V1MapFactory factory = new V1MapFactory(2);
//        Game1Map map = factory.createMap();
//        assertEquals(factory.getTerritories().get(0), map.getTerritoryIterator().next());
//    }

//    @Test
//    public void testSeparateTerritoryGroups() {
//        V1MapFactory factory = new V1MapFactory(4);
//        ArrayList<Territory> territories1 = factory.getTerritoryGroups().get(0);
//        assertEquals("a6", territories1.get(5).getName());
//        assertEquals("c1", factory.getTerritories().get(12).getName());
//        ArrayList<Territory> territories2 = factory.getTerritoryGroups().get(2);
//        assertEquals("c1", territories2.get(0).getName());
//    }

//    @Test
//    public void testGetTerritoriesAndGetTerritoryGroups() {
//        V1MapFactory factory = new V1MapFactory(4);
//        ArrayList<Territory> territories = factory.getTerritoryGroups().get(0);
//        assertEquals("a6", territories.get(5).getName());
//    }
}
