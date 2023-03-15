package edu.duke.ece651.team8.shared;

public class MoveAction extends Action{

    public MoveAction(Player player, String source, String destination, int count, Map theMap) {
        super(player, source, destination, count, theMap);
    }
    public boolean isValidSource(){
        return getSource().isOwner(getPlayer());
    }
    public boolean isValidDestination(){
        return getDestination().isOwner(getPlayer());
    }
    protected void doAction(Map theMap){
        getSource().moveOut(new BasicUnit(super.getCount(),super.getPlayer()));
        getDestination().moveIn(new BasicUnit(super.getCount(),super.getPlayer()));
    }
    protected boolean isValidPath(){
        return getSource().isAdjacentSelf(getDestination());
    }

}
