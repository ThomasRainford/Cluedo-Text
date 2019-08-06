package board;

import board.token.Token;
import board.token.Weapon;

import java.util.List;

public abstract class Location {

    private int x;
    private int y;
    private String name;
    private Token token;
    private boolean accessible;

    public Location(int x, int y, String name, Token token, boolean accessible) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.token = token;
        this.accessible = accessible;
    }


    /**
     * Checks if specified location is a Room
     *
     * @return - if this is a Room or not.
     */
    public boolean isRoom() {
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

    public Token getToken() {
        return token;
    }

    public boolean isAccessible() {
        return accessible;
    }

    public void setBoardToken(Token token) {
        this.token = token;
    }

    public void setAccessible(boolean accessible) {
        this.accessible = accessible;
    }
}
