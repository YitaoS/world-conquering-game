package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public interface Territory {
  /**
   * @return name
   */
  public String getName();

  /**
   * @return adjacent_territory
   */
  public HashSet<Territory> getAdjacent();

  public ArrayList<Territory> getAdjList();

  /**
   * @param other
   * @return True if equals, False if not equal
   */
  public boolean equals(Object other);

  /**
   * @return True if Territory have an owner
   * should have owner when there is units inside
   */
  public boolean hasOwner();

  /**
   * must use this function when changing owner
   * update the territory's owner
   * will remove this territory from old owner into new owner
   */
  public void changeOwner();

  /**
   * check if input is the owner
   *
   * @param owner
   * @return True if input is the owner, False if otherwise
   */
  public boolean isOwner(Player owner);

  /**
   * add adjacent into adjacent_territory
   *
   * @param adjacent
   */
  public void addAdjacent(Territory adjacent);

  /**
   * input territory has the same owner as this
   *
   * @param adjacent
   * @return True if input is adjacent to this, False otherwise
   * adjacent means that it can cross multiple of its own territory to get to this
   */
  public boolean isAdjacentSelf(Territory adjacent);

  /**
   * input territory has different owner as this
   *
   * @param adjacent
   * @return True if input is adjacent to this, False otherwise
   * adjacent mean that it is directly adjacent to this
   */
  public boolean isAdjacentEnemy(Territory adjacent);

  /**
   * add unit_in into units
   * will modify existing list if units have unit that has the same owner
   * @param unit_in
   */
  public void moveIn(Unit unit_in);

  /**
   * move out the amount of units
   *
   * @param unit_out
   */
  public void moveOut(Unit unit_out);

  /**
   * resolve the condition when the units list have a size > 1
   * the one with a larger roll number wins
   * if 2 units: defender wins when there is a tie
   * if >2 units: the left one wins when there is a tie
   * units will be left with only one unit, which will become the owner
   * add one unit to the owner
   */
  public void attack();

  /**
   * @param n
   * @return amount of the nth unit in units
   */
  public int getUnitAmount(int n);

  /**
   * @return the amount of units of the owner of the territory
   */
  public int getOwnerUnitAmount();

  /**
   * @return size of units
   */
  public int getUnitsSize();

  public Player getOwner();

  public Player setOwner(Player player);

}