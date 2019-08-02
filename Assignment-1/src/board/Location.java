package board;

import game.Player;

public abstract class Location {

    private int x;
    private int y;
    private String name;
    private Player player;
    private boolean accessible;

    public Location(int x, int y, String name, Player player, boolean accessible){
        this.x = x;
        this.y = y;
        this.name = name;
        this.player = player;
        this.accessible = accessible;
    }

    /**
     * Checks if specified location is a Room
     *
     * @return
     */
    public boolean isRoom(){
        return this instanceof Room;
    }

    /* Getters and Setter */

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }
}
