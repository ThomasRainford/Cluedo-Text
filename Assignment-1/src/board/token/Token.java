package board.token;

import board.Location;


public abstract class Token {
    private String name;
    private Location location;
    private String token;

    public Token(String name, Location location){
        this.name = name;
        this.location = location;
    }


    /* Getters and Setters */


    /**
     * Set the Tokens Location to location and set the Locations
     * accessible field to false.
     *
     * @param location - the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
        // when a player is moved the location is set to not accessible
        // to prevent any other players from being on the same location
        this.location.setAccessible(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
