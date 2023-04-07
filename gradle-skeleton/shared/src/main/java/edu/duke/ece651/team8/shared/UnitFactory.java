package edu.duke.ece651.team8.shared;

import java.util.ArrayList;
import java.util.List;

public class UnitFactory {
    public List<Unit> makeBasicUnits(int amount) {
        List<Unit> res = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            res.add(new BasicUnit());
        }
        return res;
    }
    public List<Unit> makeAdvancedUnits(int amount, int level) { // not considering the validation of upgrade
        List<Unit> res = makeBasicUnits(amount);
        for(int i = 0; i < res.size(); i++) {
            Unit u = upgradeTo(level, res.get(i));
            res.set(i, u);
        }
        return res;
    }
    /**
     * upgrade current unit
     * @return the upgraded unit
     */
    public Unit upgradeTo(int level, Unit u) {
        if(level == 0) return u;
        return upgradeTo(level - 1, u.upgrade());
    }

    /**
     * Try whether unit can be upgrade to certain level
     * @param balance is resources remaining
     * @return larger than zero: remaining resources for upgrading
     *         smaller than zero: can not be upgraded to that level
     */
    public int canUpgradeTo(int level, int balance, Unit u) {
        if(level == 0) return balance;
        balance -= u.getUpgradeCost();
        return canUpgradeTo(level-1, balance, u.upgrade());
    }
}
