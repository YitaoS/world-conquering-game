package edu.duke.ece651.team8.shared;

import java.util.*;

public class V1MapFactory implements AbstractMapFactory {
//    private ArrayList<Territory> territories;

    private final int territoryAmount;

//    private ArrayList<ArrayList<Territory>> territoryGroups;
    private String[] territoryNameList;

    private String[] colors;

    private ArrayList<Territory> territories;

    public V1MapFactory() {
        this.territoryAmount = 6;
        this.territoryNameList = new String[] {
                "a1", "a2", "a3", "a4", "a5", "a6",
                "b1", "b2", "b3", "b4", "b5", "b6",
                "c1", "c2", "c3", "c4", "c5", "c6",
                "d1", "d2", "d3", "d4", "d5", "d6"
        };
        this.colors= new String[]{ "Green", "Red", "Blue", "Yellow" };
        this.territories= new ArrayList<>();
    }

    /**
     *
     * @param playerAmount
     * @return create a Game1Map
     */
    @Override
    public Game1Map createMap(int playerAmount) {
        this.territories = createTerritories(playerAmount);
        Game1Map theMap=new Game1Map(territories);
        connectAdjacentTerritory(playerAmount, theMap);
        return theMap;
    }

    /**
     * create n territory groups (n equals to playerAmount)
     * @param playerAmount
     * @param territoryGroups
     */
    private void createTerritoryGroups(int playerAmount, ArrayList<ArrayList<Territory>> territoryGroups) {
        for (int i = 0; i < playerAmount; ++i) {
            territoryGroups.add(new ArrayList<>());
        }
    }

    /**
     * create n Players (n equals to playerAmount) with their territories inside
     * @param playerAmount
     * @return list of players
     */
    public ArrayList<Player> createPlayers(int playerAmount) {
        //this.territories = createTerritories(playerAmount);
        ArrayList<ArrayList<Territory>> territoryGroups = new ArrayList<>();
        createTerritoryGroups(playerAmount, territoryGroups);
        separateTerritoriesToGroups(territoryGroups, territories, playerAmount);
        ArrayList<Player> players=new ArrayList<>();
        for (int i = 0; i < playerAmount; ++i) {
            Player p=new TextPlayer(colors[i]);
            for (Territory t: territoryGroups.get(i)){
                p.addTerritory(t);
            }
            players.add(p);
        }
        return players;
    }

    private ArrayList<Territory> createTerritories(int playerAmount) {
        ArrayList<Territory> territories = new ArrayList<>();
        for (int i = 0; i < playerAmount * 6; ++i) {
            Territory t = new BasicTerritory(this.territoryNameList[i]);
            territories.add(t);
        }
        return territories;
    }


    private void connectAdjacentTerritory(int playerAmount, Map theMap) {
        //up, left, down, right
        ArrayList<Territory> territories=theMap.getTerritories();
        for (int i = 0; i < playerAmount; ++i) {
            for (int j = 0; j < 6; ++j) {
                if (i != playerAmount - 1) {
                    theMap.addAdjacency(territories.get(i * 6 + j),territories.get((i + 1) * 6 + j));
                }
                if (j % 6 != 5) {
                    theMap.addAdjacency(territories.get(i * 6 + j),territories.get(i * 6 + j + 1));
                }
            }
        }
    }

    /**
     * separate territories as groups
     */
    public void separateTerritoriesToGroups(ArrayList<ArrayList<Territory>> territoryGroups, ArrayList<Territory> territories, int playerAmount) {
        int index = 0;
        for (int num = 0; num < playerAmount; ++num) {
            for (int i = 0; i < territoryAmount; ++i) {
                territoryGroups.get(num).add(territories.get(index));
                ++index;
            }
        }
    }
}
