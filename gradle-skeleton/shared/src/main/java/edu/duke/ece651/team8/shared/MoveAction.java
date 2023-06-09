package edu.duke.ece651.team8.shared;

public class MoveAction extends MovableAction {
    Map theMap;

    public MoveAction(Player player, String source, String destination, int count, Map theMap) {
        super(player, source, destination, count, theMap);
        this.theMap = theMap;
    }
    @Override
    public boolean isValidSource(){
        return getSource().isOwner(getPlayer());
    }
    @Override
    public boolean isValidDestination(){
        return getDestination().isOwner(getPlayer());
    }

    @Override
    public boolean hasEnoughFood() {
        MinimumPath path = new MinimumPath(player, theMap);
        int minPath = path.findMinPath(getSource(), getDestination());
//        System.out.println(super.player.getFoodAmount() + " " + minPath);
        return super.player.getFoodAmount() >= (super.getCount() * minPath);
    }
    @Override
    public void doAction(){
        Army eArmy = getSource().getArmy(super.getCount(),super.getPlayer());
        getSource().moveOut(eArmy);
        getDestination().moveIn(eArmy);
        MinimumPath path = new MinimumPath(player, theMap);
        int minPath = path.findMinPath(getSource(), getDestination());
        super.player.addFoodResource(-(super.getCount() * minPath));
    }
    protected boolean isValidPath(){
        return getSource().isAdjacentSelf(getDestination());
    }
}
