package board;

import board.token.Player;
import board.token.Weapon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
    private static final int x = 24;
    private static final int y = 25;

    private Location[][] board;
    private List<Location> rooms;


    public Board() {

        board = new Location[x][y];
    }



    /**
     * Moves the specified player to he specified co-ordinates
     * Players cannot move to a wall and cannot move outside of
     * the board.
     *
     * @param player - the player moving
     * @param x      - x co-ordinate to move too
     * @param y      - y x co-ordinate to move too
     * @return - if the move was valid
     */
    public boolean movePlayer(Player player, int x, int y) {
        List<Location> previousLocations = player.getTurnLocations();
        if ((x <= 23 && x >= 0) && (y <= 24 && y >= 0) && board[x][y].isAccessible() && !previousLocations.contains(board[x][y])) {
            Location previous = player.getLocation();
            player.setLocation(board[x][y]);          // set the players location
            board[x][y].setBoardToken(player);            // set the locations player
            previous.setBoardToken(null);                 // set the previous locations player to null
            previous.setAccessible(true);             // set previous location to accessible
            return true;

        } else {
            return false;
        }
    }


    /**
     * Move a weapon Token to the specified co-ordinated.
     *
     * @param weapon - the weapon token to be moved
     * @param x - the x co- ordinate on the board
     * @param y - the y co-ordinate on the board
     * @return
     */
    public boolean moveWeapon(Weapon weapon, int x, int y){
        if(board[x][y].isAccessible() && board[x][y].isRoom()){
            Location previous = weapon.getLocation();
            weapon.setLocation(board[x][y]);
            board[x][y].setBoardToken(weapon);
            previous.setBoardToken(null);
            previous.setAccessible(true);
            return true;

        } else {
            return false;
        }
    }


    /**
     * creates a text-based Cluedo board
     */
    public void setupBoard(List<Player> players, List<Weapon> weapons, int numberPlayers) {
        File file = new File("C:\\Users\\thoma\\IdeaProjects\\SWEN225\\Assignment-1\\resources\\board-layout.txt");
        rooms = new ArrayList<>();
        int x;
        int y = 0;

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String ch = sc.nextLine();
                x = 0;
                while (x < 24) {
                    char c = ch.charAt(x);
                    switch (c) {
                        case '=':
                            board[x][y] = new Wall(x, y);
                            x++;

                            break;
                        case '#':
                            board[x][y] = new Hallway(x, y);
                            x++;

                            break;
                        case 'K':
                            board[x][y] = new Room(x, y, "Kitchen");
                            rooms.add(board[x][y]);
                            x++;

                            break;
                        case 'B':
                            board[x][y] = new Room(x, y, "Ball Room");
                            rooms.add(board[x][y]);
                            x++;

                            break;
                        case 'C':
                            board[x][y] = new Room(x, y, "Conservatory");
                            rooms.add(board[x][y]);
                            x++;

                            break;
                        case 'D':
                            board[x][y] = new Room(x, y, "Dining Room");
                            rooms.add(board[x][y]);
                            x++;

                            break;
                        case 'I':
                            board[x][y] = new Room(x, y, "Billiard Room");
                            rooms.add(board[x][y]);
                            x++;

                            break;
                        case 'L':
                            board[x][y] = new Room(x, y, "Library");
                            rooms.add(board[x][y]);
                            x++;

                            break;
                        case 'O':
                            board[x][y] = new Room(x, y, "Lounge");
                            rooms.add(board[x][y]);
                            x++;

                            break;
                        case 'H':
                            board[x][y] = new Room(x, y, "Hall");
                            rooms.add(board[x][y]);
                            x++;

                            break;
                        case 'S':
                            board[x][y] = new Room(x, y, "Study");
                            rooms.add(board[x][y]);
                            x++;
                            break;
                    }
                }
                y++;
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        // setup players and weapons
        setPLayerLocations(players, numberPlayers);
        setWeaponLocations(weapons);

    }


    /**
     * Gets a list of all the possible initial locations
     * for all the players
     *
     * @return List of Location's
     */
    public List<Location> allPlayerLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(board[7][0] = new Hallway(7, 0));
        locations.add(board[14][0] = new Hallway(14, 0));
        locations.add(board[23][6] = new Hallway(23, 6));
        locations.add(board[23][13] = new Hallway(23, 13));
        locations.add(board[8][24] = new Hallway(8, 24));
        locations.add(board[0][17] = new Hallway(0, 17));

        return locations;
    }


    /**
     * Returns a List of all the Locations of the Weapon
     * Tokens in there initial positions
     *
     * @return - A List of Locations
     */
    public List<Location> allWeaponLocations() {
        List<Location> locations = new ArrayList<>();
        locations.add(board[4][5] = new Room(4, 5, "Kitchen"));
        locations.add(board[14][6] = new Room(14, 6, "Ball Room"));
        locations.add(board[22][4] = new Room(22, 3, "Conservatory"));
        locations.add(board[6][14] = new Room(6, 14, "Dining Room"));
        locations.add(board[22][10] = new Room(22, 10, "Billiard Room"));
        locations.add(board[22][16] = new Room(22, 16, "Lounge"));

        return locations;
    }


    /**
     * Assign Players to locations and assign locations to Players
     * and set the Players tokens
     *
     * @param players List of players that will be playable
     */
    public void setPLayerLocations(List<Player> players, int numberPlayers) {
        List<Location> locations = allPlayerLocations();
        for (int i = 0; i < numberPlayers; i++) {
            setupPlayer(players.get(i), locations.get(i), String.valueOf(i + 1));
        }
    }


    /**
     * Assign Weapons to locations and assign locations to Weapons
     * and set the Weapons token.
     *
     * @param weapons
     */
    public void setWeaponLocations(List<Weapon> weapons) {
        List<Location> locations = allWeaponLocations();
        for (int i = 0; i < Weapon.NUM_WEAPONS; i++) {
            setupWeapon(weapons.get(i), locations.get(i), String.valueOf(i + 7));
        }
    }


    /**
     * Assigns players location and locations player
     * and sets the players token for the board.
     *
     * @param player   - player to be setup
     * @param location - location to assign to player
     */
    public void setupPlayer(Player player, Location location, String token) {
        player.setLocation(location);
        location.setBoardToken(player);
        player.setToken(token);
        location.setAccessible(false);
    }


    /**
     * Assigns weapons location and locations weapon
     * and sets the weapons token for the board.
     *
     * @param weapon   - weapon to be setup
     * @param location - location to assign to weapon
     */
    public void setupWeapon(Weapon weapon, Location location, String token) {
        weapon.setLocation(location);
        location.setBoardToken(weapon);
        weapon.setToken(token);
        location.setAccessible(false);
    }


    /**
     * Print the board
     */
    public void printBoard() {
        for (int y = 0; y < 25; y++) {
            for (int x = 0; x < 24; x++) {
                Location location = board[x][y];
                if (location instanceof Wall) {
                    System.out.print("=");

                } else if (location instanceof Hallway) {
                    // check for a player. Print player token or hallway token.
                    if (location.getToken() != null) {
                        System.out.print(location.getToken().getToken());

                    } else {
                        System.out.print("#");
                    }

                    // check for a player in a Room
                } else if (location instanceof Room && location.getToken() != null) {
                    System.out.print(location.getToken().getToken());

                } else if (location.getName().equals("Kitchen")) {
                    System.out.print("K");

                } else if (location.getName().equals("Ball Room")) {
                    System.out.print("B");

                } else if (location.getName().equals("Conservatory")) {
                    System.out.print("C");

                } else if (location.getName().equals("Dining Room")) {
                    System.out.print("D");

                } else if (location.getName().equals("Billiard Room")) {
                    System.out.print("I");

                } else if (location.getName().equals("Library")) {
                    System.out.print("L");

                } else if (location.getName().equals("Lounge")) {
                    System.out.print("O");

                } else if (location.getName().equals("Hall")) {
                    System.out.print("H");

                } else if (location.getName().equals("Study")) {
                    System.out.print("S");

                }
            }
            System.out.println();
        }
    }

    /**
     *
     * @return - the board as a String
     */
    public String getBoardAsString(){
        StringBuilder boardLayout = new StringBuilder();
        for (int y = 0; y < 25; y++) {
            for (int x = 0; x < 24; x++) {
                Location location = board[x][y];
                if (location instanceof Wall) {
                    boardLayout.append("=");

                } else if (location instanceof Hallway) {
                    // check for a player. Print player token or hallway token.
                    if (location.getToken() != null) {
                        boardLayout.append(location.getToken().getToken());

                    } else {
                        boardLayout.append("#");
                    }

                    // check for a player in a Room
                } else if (location instanceof Room && location.getToken() != null) {
                    boardLayout.append(location.getToken().getToken());

                } else if (location.getName().equals("Kitchen")) {
                    boardLayout.append("K");

                } else if (location.getName().equals("Ball Room")) {
                    boardLayout.append("B");

                } else if (location.getName().equals("Conservatory")) {
                    boardLayout.append("C");

                } else if (location.getName().equals("Dining Room")) {
                    boardLayout.append("D");

                } else if (location.getName().equals("Billiard Room")) {
                    boardLayout.append("I");

                } else if (location.getName().equals("Library")) {
                    boardLayout.append("L");

                } else if (location.getName().equals("Lounge")) {
                    boardLayout.append("O");

                } else if (location.getName().equals("Hall")) {
                    boardLayout.append("H");

                } else if (location.getName().equals("Study")) {
                    boardLayout.append("S");
                }
            }
            boardLayout.append("\n");
        }
        return boardLayout.toString();
    }


    /**
     * If the specified player is in a Room then the player can be moved to
     * the specified Room
     *
     * @param player - the player to be Moved
     * @param goToRoom - the Room to move the player too.
     * @param players - the list of all players on the board.
     */
    public void movePlayerToRoom(Player player, Room goToRoom, List<Player> players) {
        if (players.contains(player)) {
            for (Location room : rooms) {
                if (room.getName().equals(goToRoom.getName())) {
                    if (room.getToken() == null) {
                        movePlayer(player, room.getX(), room.getY());
                        break;
                    }
                }
            }
        }
    }


    /**
     * If the specified weapon is in a Room then the weapon can be moved to
     * the specified Room
     *
     * @param weapon - the player to be Moved
     * @param goToRoom - the Room to move the player too.
     */
    public void moveWeaponToRoom(Weapon weapon, Room goToRoom) {
        for (Location room : rooms) {
            if (room.getName().equals(goToRoom.getName())) {
                if (room.getToken() == null) {
                    moveWeapon(weapon, room.getX(), room.getY());
                    break;
                }
            }
        }
    }



    /* Getters and Setters */

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    public List<Location> getRooms() {
        return rooms;
    }

    public void setRooms(List<Location> rooms) {
        this.rooms = rooms;
    }

    public Location[][] getBoard() {
        return board;
    }

    public void setBoard(Location[][] board) {
        this.board = board;
    }


}
