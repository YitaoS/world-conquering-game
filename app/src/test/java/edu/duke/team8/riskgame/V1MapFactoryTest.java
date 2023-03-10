package edu.duke.team8.riskgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class V1MapFactoryTest {
    @Test
    public void testConstructor() {
        V1MapFactory factory = new V1MapFactory(2);
        ArrayList<Territory> territories = factory.getTerritories();
        Game1Map map = factory.createMap();
        assertEquals(24, factory.getTerritories().size());
        assertEquals("a1", factory.getTerritories().get(0).getName());
        assertEquals("a6", factory.getTerritories().get(5).getName());
        assertEquals("d6", factory.getTerritories().get(23).getName());
    }

    @Test
    public void testCreateMap() {
        V1MapFactory factory = new V1MapFactory(2);
        Game1Map map = factory.createMap();
        assertEquals(factory.getTerritories().get(0), map.getTerritoryIterator().next());
    }

    @Test
    public void testSeparateTerritoryGroups() {
        V1MapFactory factory = new V1MapFactory(4);
        ArrayList<Territory> territories1 = factory.getTerritoryGroups().get(0);
        assertEquals("a6", territories1.get(5).getName());
        assertEquals("c1", factory.getTerritories().get(12).getName());
        ArrayList<Territory> territories2 = factory.getTerritoryGroups().get(2);
        assertEquals("c1", territories2.get(0).getName());
    }

    @Test
    public void testGetTerritoriesAndGetTerritoryGroups() {
        V1MapFactory factory = new V1MapFactory(4);
        ArrayList<Territory> territories = factory.getTerritoryGroups().get(0);
        assertEquals("a6", territories.get(5).getName());
    }
}
