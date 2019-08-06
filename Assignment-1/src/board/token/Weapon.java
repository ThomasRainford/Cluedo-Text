package board.token;

import board.Location;


/**
 * Weapon initial co-ordinates
 * <p>
 * Candlestick:    4, 5      token:
 * Dagger:         14, 6     token:
 * Lead Pipe:      23, 4     token:
 * Revolver:       6, 14     token:
 * Rope:           22, 10    token:
 * Spanner:        22, 16    token:
 */
public class Weapon extends Token {
    public static final int NUM_WEAPONS = 6;

    private String token;

    public Weapon(String name, Location location) {
        super(name, location);
    }



    /* Getters and Setters */

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getChar() {
        return this.getName().toLowerCase().substring(0, 1);
    }
}
