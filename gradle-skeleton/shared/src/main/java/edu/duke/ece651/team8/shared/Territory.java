package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.HashSet;


public interface Territory {
  /**
   * @return name
   */
  String getName();

  /**
   * @return adjacent_territory
   */
  HashSet<Territory> getAdjacent();

  ArrayList<Territory> getAdjList();

  /**
   * @param other the territory to be checked
   * @return True if equals, False if not equal
   */
  boolean equals(Object other);

  /**
   * @return True if Territory have an owner
   * should have owner when there is units inside
   */
  boolean hasOwner();

  /**
   * must use this function when changing owner
   * update the territory's owner
   * will remove this territory from old owner into new owner
   */
  void changeOwner();

  /**
   * check if input is the owner
   *
   * @param owner the owner to be checked
   * @return True if input is the owner, False if otherwise
   */
  boolean isOwner(Player owner);

  /**
   * add adjacent into adjacent_territory
   *
   * @param adjacent the territory to be checked
   */
  void addAdjacent(Territory adjacent);

  /**
   * input territory has the same owner as this
   *
   * @param adjacent the territory to be checked
   * @return True if input is adjacent to this, False otherwise
   * adjacent means that it can cross multiple of its own territory to get to this
   */
  boolean isAdjacentSelf(Territory adjacent);

  /**
   * input territory has different owner as this
   *
   * @param adjacent the territory to be checked
   * @return True if input is adjacent to this, False otherwise
   * adjacent mean that it is directly adjacent to this
   */
  boolean isAdjacentEnemy(Territory adjacent);

  /**
   * add army_in into units
   * will modify existing list if units have unit that has the same owner
   * @param army_in the unit to move
   */
  void moveIn(Army army_in);

  /**
   * move out the amount of units
   *
   * @param army_out the unit to move
   */
  void moveOut(Army army_out);

  /**
   * resolve the condition when the units list have a size > 1
   * the one with a larger roll number wins
   * if 2 units: defender wins when there is a tie
   * if >2 units: the left one wins when there is a tie
   * units will be left with only one unit, which will become the owner
   * add one unit to the owner
   */
  void attack();

  /**
   * add one to the unit that belongs to the owner
   */
  void addOne();

  /**
   * @param n the number of unit
   * @return amount of the nth unit in units
   */
  int getUnitAmount(int n);

  /**
   * @return the amount of units of the owner of the territory
   */
  int getOwnerUnitAmount();

  /**
   * @return size of units
   */
  int getUnitsSize();

  Player getOwner();

  void setOwner(Player player);

}