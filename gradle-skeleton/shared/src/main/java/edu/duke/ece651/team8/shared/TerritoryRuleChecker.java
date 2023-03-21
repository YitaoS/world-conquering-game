package edu.duke.ece651.team8.shared;

public class TerritoryRuleChecker extends ActionRuleChecker {
    public TerritoryRuleChecker(ActionRuleChecker next){
        super(next);
    }

    /**
     * check if territory exists in map
     * @param action
     * @return
     */
    protected String checkMyRule(Action action){
        if(action.getSource()==null){
            return "Source "+action.getSourceText()+" not in map";
        }
        else if(action.getDestination()==null){
            return "Destination "+action.getDestinationText()+" not in map";
        }
        return null;
    }
}