package board;

import game.Player;

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


    public Board(){

        board = new Location[x][y];
    }

    //TODO: add weapon tokens and allow them to be moved to a Room


    /**
     * Moves the specified player to he specified co-ordinates
     * Players cannot move to a wall and cannot move outside of
     * the board.
     *
     *
     * @param player - the player moving
     * @param x - x co-ordinate to move too
     * @param y - y x co-ordinate to move too
     * @return - if the move was valid
     */
    public boolean movePlayer(Player player, int x, int y){
        if((x <= 23 && x >= 0) && (y <= 24 && y >= 0) && board[x][y].isAccessible()) {
            Location previous = player.getLocation();
            player.setLocation(board[x][y]);          // set the players location
            board[x][y].setPlayer(player);            // set the locations player
            previous.setPlayer(null);                 // set the previous locations player to null
            previous.setAccessible(true);             // set previous location to accessible
            return true;

        } else {
            return false;
        }
    }


    /**
     * creates a text-based Cluedo board
     */
    public void setupBoard(List<Player> players, int numberPlayers){
        File file = new File("C:\\Users\\thoma\\IdeaProjects\\SWEN225\\Assignment-1\\resources\\board-layout.txt");
        rooms = new ArrayList<>();
        int x;
        int y = 0;

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()){
                String ch = sc.nextLine();
                x = 0;
                while(x < 24){
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

        } catch (FileNotFoundException e){
            System.out.println(e);
        }

        // setup players
        setPLayerLocations(players, numberPlayers);
    }


    /**
     * Gets a list of all the possible initial locations
     * for all the players
     *
     * @return List of Location's
     */
    public List<Location> allPlayerLocations(){
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
     * Assign players to locations and assign locations to players
     * and set there tokens
     *
     * @param players
     * List of players that will be playable
     */
    public void setPLayerLocations(List<Player> players, int numberPlayers){
        List<Location> locations = allPlayerLocations();
        for(int i = 0; i < numberPlayers; i++){
            setupPlayer(players.get(i), locations.get(i), String.valueOf(i+1));
        }
    }


    /**
     * Assigns players location and locations player
     * and sets the players token for the board.
     *
     * @param player - player to be setup
     * @param location - location to assign to player
     */
    public void setupPlayer(Player player, Location location, String token){
        player.setLocation(location);
        location.setPlayer(player);
        player.setToken(token);
        location.setAccessible(false);
    }


    /**
     * Print the board
     */
    public void printBoard(){
        for(int y = 0; y < 25; y++){
            for(int x = 0; x < 24; x++){
                Location location = board[x][y];
                if(location instanceof Wall){
                    System.out.print("=");

                } else if(location instanceof Hallway) {
                    // check for a player. Print player token or hallway token.
                    if (location.getPlayer() != null) {
                        System.out.print(location.getPlayer().getToken());

                    } else {
                        System.out.print("#");
                    }

                    // check for a player in a Room
                } else if (location instanceof Room && location.getPlayer() != null){
                    System.out.print(location.getPlayer().getToken());

                } else if(location.getName().equals("Kitchen")){
                    System.out.print("K");

                } else if(location.getName().equals("Ball Room")){
                    System.out.print("B");

                } else if(location.getName().equals("Conservatory")){
                    System.out.print("C");

                } else if(location.getName().equals("Dining Room")){
                    System.out.print("D");

                } else if(location.getName().equals("Billiard Room")){
                    System.out.print("I");

                } else if(location.getName().equals("Library")){
                    System.out.print("L");

                } else if(location.getName().equals("Lounge")) {
                    System.out.print("O");

                } else if(location.getName().equals("Hall")){
                    System.out.print("H");

                } else if(location.getName().equals("Study")){
                    System.out.print("S");

                }
            }
            System.out.println();
        }
    }


    public void movePlayerToRoom(Player player, Room goToRoom, List<Player> players){
        if(players.contains(player)) {
            for (Location room : rooms) {
                if (room.getName().equals(goToRoom.getName())) {
                    if (room.getPlayer() == null) {
                        movePlayer(player, room.getX(), room.getY());
                        break;
                    }
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
