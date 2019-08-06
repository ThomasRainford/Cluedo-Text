package game;

import board.*;
import board.token.Player;
import board.token.Weapon;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardDrawing {


    /**
     * Test initial board with all players
     */
    @Test
    public void testInitialBoard_1(){
        String layout =
                "=========1====2=========\n" +
                "=======###=BB=###=======\n" +
                "=KKKK=##===BB===##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=###CCCl=\n" +
                "=KKKc=##=BBBBBB=##======\n" +
                "====#=###BBBBBd########3\n" +
                "########=#====#=#######=\n" +
                "=#################======\n" +
                "=====##############IIII=\n" +
                "=DDD====##=====###=IIIr=\n" +
                "=DDDDDD=##=###=###=IIII=\n" +
                "=DDDDDD###=###=###====#=\n" +
                "=DDDDDD=##=###=########=\n" +
                "=DDDDDr=##=###=###==#===\n" +
                "======#=##=###=##==LLLL=\n" +
                "=#########=====###LLLLs=\n" +
                "6################==LLLL=\n" +
                "=########==##==###======\n" +
                "=====#=##=HHHH=########4\n" +
                "=OOOOO=##=HHHH#########=\n" +
                "=OOOOO=##=HHHH=##=#=====\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=======5================\n";

        Game game = new Game();
        setupGame(game, 6);
        Board b = game.getBoard();
        setWeaponLocations(game.getWeapons(), game);

        assertEquals(layout, b.getBoardAsString());

    }



    /**
     * Test initial board with 3 players
     */
    @Test
    public void testInitialBoard_2(){
        String layout =
                "=========1====2=========\n" +
                "=======###=BB=###=======\n" +
                "=KKKK=##===BB===##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=###CCCl=\n" +
                "=KKKc=##=BBBBBB=##======\n" +
                "====#=###BBBBBd########3\n" +
                "########=#====#=#######=\n" +
                "=#################======\n" +
                "=====##############IIII=\n" +
                "=DDD====##=====###=IIIr=\n" +
                "=DDDDDD=##=###=###=IIII=\n" +
                "=DDDDDD###=###=###====#=\n" +
                "=DDDDDD=##=###=########=\n" +
                "=DDDDDr=##=###=###==#===\n" +
                "======#=##=###=##==LLLL=\n" +
                "=#########=====###LLLLs=\n" +
                "#################==LLLL=\n" +
                "=########==##==###======\n" +
                "=====#=##=HHHH=#########\n" +
                "=OOOOO=##=HHHH#########=\n" +
                "=OOOOO=##=HHHH=##=#=====\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=======#================\n";

        Game game = new Game();
        setupGame(game, 3);
        Board b = game.getBoard();
        setWeaponLocations(game.getWeapons(), game);

        assertEquals(layout, b.getBoardAsString());

    }


    /**
     * Test player movement updates the board
     *
     * Player moves from Hallway to Hallway
     */
    @Test
    public void testBoardPlayerMovement_1(){
        String layout =
                "=========#====2=========\n" +
                "=======##1=BB=###=======\n" +
                "=KKKK=##===BB===##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=###CCCl=\n" +
                "=KKKc=##=BBBBBB=##======\n" +
                "====#=###BBBBBd########3\n" +
                "########=#====#=#######=\n" +
                "=#################======\n" +
                "=====##############IIII=\n" +
                "=DDD====##=====###=IIIr=\n" +
                "=DDDDDD=##=###=###=IIII=\n" +
                "=DDDDDD###=###=###====#=\n" +
                "=DDDDDD=##=###=########=\n" +
                "=DDDDDr=##=###=###==#===\n" +
                "======#=##=###=##==LLLL=\n" +
                "=#########=====###LLLLs=\n" +
                "#################==LLLL=\n" +
                "=########==##==###======\n" +
                "=====#=##=HHHH=#########\n" +
                "=OOOOO=##=HHHH#########=\n" +
                "=OOOOO=##=HHHH=##=#=====\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=======#================\n";

        Game game = new Game();
        setupGame(game, 3);
        Board b = game.getBoard();
        setWeaponLocations(game.getWeapons(), game);
        Player player = game.getPlayers().get(0);
        b.movePlayer(player, 9, 1);

        assertEquals(layout, b.getBoardAsString());

    }


    /**
     * Test player movement updates the board
     *
     * Player moves from Hallway to Room and
     * vice versa
     */
    @Test
    public void testBoardPlayerMovement_2(){
        String layout =
                "=========#====2=========\n" +
                "=======###=BB=###=======\n" +
                "=KKKK=##===BB===##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=###CCCl=\n" +
                "=KKKc=##=BBBBBB=##======\n" +
                "====#=###BBBBBd########3\n" +
                "########=1====#=#######=\n" +
                "=#################======\n" +
                "=====##############IIII=\n" +
                "=DDD====##=====###=IIIr=\n" +
                "=DDDDDD=##=###=###=IIII=\n" +
                "=DDDDDD###=###=###====#=\n" +
                "=DDDDDD=##=###=########=\n" +
                "=DDDDDr=##=###=###==#===\n" +
                "======#=##=###=##==LLLL=\n" +
                "=#########=====###LLLLs=\n" +
                "#################==LLLL=\n" +
                "=########==##==###======\n" +
                "=====#=##=HHHH=#########\n" +
                "=OOOOO=##=HHHH#########=\n" +
                "=OOOOO=##=HHHH=##=#=====\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=======#================\n";

        Game game = new Game();
        setupGame(game, 3);
        Board b = game.getBoard();
        setWeaponLocations(game.getWeapons(), game);
        Player player = game.getPlayers().get(0);
        b.movePlayer(player, 9, 6);

        b.movePlayer(player, 9,7);
        assertEquals(layout, b.getBoardAsString());
    }


    /**
     * Test suggested player is moved to the current players
     * room along with the suggested weapon
     */
    @Test
    public void testSuggestedTokenMovement_1(){
        String layout =
                "=========#====2=========\n" +
                "=======###=3c=###=======\n" +
                "=KKKK=##===BB===##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=###CCCl=\n" +
                "=KKKK=##=BBBBBB=##======\n" +
                "====#=###1BBBBd#########\n" +
                "########=#====#=#######=\n" +
                "=#################======\n" +
                "=====##############IIII=\n" +
                "=DDD====##=====###=IIIr=\n" +
                "=DDDDDD=##=###=###=IIII=\n" +
                "=DDDDDD###=###=###====#=\n" +
                "=DDDDDD=##=###=########=\n" +
                "=DDDDDr=##=###=###==#===\n" +
                "======#=##=###=##==LLLL=\n" +
                "=#########=====###LLLLs=\n" +
                "#################==LLLL=\n" +
                "=########==##==###======\n" +
                "=====#=##=HHHH=#########\n" +
                "=OOOOO=##=HHHH#########=\n" +
                "=OOOOO=##=HHHH=##=#=====\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=======#================\n";

        Game game = new Game();
        setupGame(game, 3);
        Board b = game.getBoard();
        setWeaponLocations(game.getWeapons(), game);
        Player player = game.getPlayers().get(0);
        b.movePlayer(player, 9, 6);

        // create the suggestion
        CharacterCard cc = new CharacterCard("Mrs. Peacock");
        RoomCard rc = new RoomCard(player.getLocation().getName());
        WeaponCard wc = new WeaponCard("Candlestick");
        Suggestion suggestion = new Suggestion(cc, rc, wc, true);

        // move the tokens
        movePlayerToRoom(suggestion.getCharacterCard().getToken(game.getPlayers()), (Room) player.getLocation(), game.getPlayers(), game);
        moveWeaponToRoom(suggestion.getWeaponCard().getToken(game.getWeapons()), (Room) player.getLocation(), game);

        b.printBoard();

        // Suggested Tokens are moved to top left
        assertEquals(b.getBoard()[11][1] ,suggestion.getCharacterCard().getToken(game.getPlayers()).getLocation());
        assertEquals(b.getBoard()[12][1] ,suggestion.getWeaponCard().getToken(game.getWeapons()).getLocation());

        // check board is drawn correctly
        assertEquals(layout, b.getBoardAsString());

    }


    /**
     * Test suggested player is moved to the current players
     * room along with the suggested weapon
     */
    @Test
    public void testSuggestedTokenMovement_2(){
        String layout =
                "=========#====2=========\n" +
                "=======###=BB=###=======\n" +
                "=KKKK=##===BB===##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=###CCCl=\n" +
                "=KKKc=##=BBBBBB=##======\n" +
                "====#=###BBBBBd########3\n" +
                "########=#====#=#######=\n" +
                "=#################======\n" +
                "=====##############IIII=\n" +
                "=DDD====##=====###=IIII=\n" +
                "=DDDDDD=##=###=###=IIII=\n" +
                "=DDDDDD###=###=###====#=\n" +
                "=DDDDDD=##=###=########=\n" +
                "=DDDDDr=##=###=###==#===\n" +
                "======#=##=###=##==LLLL=\n" +
                "=#########=====###LLLLs=\n" +
                "#################==LLLL=\n" +
                "=########==##==###======\n" +
                "=====#=##=16rH=########4\n" +
                "=OOOOO=##=HHHH#########=\n" +
                "=OOOOO=##=HHHH=##=#=====\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=OOOOO=##=HHHH=##=SSSSS=\n" +
                "=======5================\n";

        Game game = new Game();
        setupGame(game, 6);
        Board b = game.getBoard();
        setWeaponLocations(game.getWeapons(), game);
        Player player = game.getPlayers().get(0);
        b.movePlayer(player, 10, 19);

        // create the suggestion
        CharacterCard cc = new CharacterCard("Col. Mustard");
        RoomCard rc = new RoomCard(player.getLocation().getName());
        WeaponCard wc = new WeaponCard("Rope");
        Suggestion suggestion = new Suggestion(cc, rc, wc, true);

        // move the tokens
        movePlayerToRoom(suggestion.getCharacterCard().getToken(game.getPlayers()), (Room) player.getLocation(), game.getPlayers(), game);
        moveWeaponToRoom(suggestion.getWeaponCard().getToken(game.getWeapons()), (Room) player.getLocation(), game);

        b.printBoard();

        // Suggested Tokens are moved to top left
        assertEquals(b.getBoard()[11][19] ,suggestion.getCharacterCard().getToken(game.getPlayers()).getLocation());
        assertEquals(b.getBoard()[12][19] ,suggestion.getWeaponCard().getToken(game.getWeapons()).getLocation());

        // check board is drawn correctly
        assertEquals(layout, b.getBoardAsString());
    }




    /**
     * Sets up a game by initialising Tokens and locations
     *
     * @param game - the current game
     * @param numberPlayer - number of players on the board
     *
     * @return - Board
     */
    public Board setupGame(Game game, int numberPlayer){
        game.initialisePlayers(numberPlayer);
        initialiseWeapons(game);
        setupBoard(game.getPlayers(), numberPlayer, game.getBoard().getLayout(), game.getBoard().getBoard(), game);
        game.initialiseCards(numberPlayer);
        game.getBoard().printBoard();
        return game.getBoard();
    }

    /**
     * Initialises all Weapon Tokens
     *
     * @param game - the current game
     */
    public void initialiseWeapons(Game game) {
        game.setWeapons(new ArrayList<>());
        List<Weapon> weapons = game.getWeapons();

        weapons.add(new Weapon("Candlestick", null));
        weapons.add(new Weapon("Dagger", null));
        weapons.add(new Weapon("Lead Pipe", null));
        weapons.add(new Weapon("Revolver", null));
        weapons.add(new Weapon("Rope", null));
        weapons.add(new Weapon("Spanner", null));
    }


    /**
     * Returns a List of all the Locations of the Weapon
     * Tokens in there initial positions
     *
     * @param board - the array of locations
     * @return - A List of Locations
     */
    public List<Location> allWeaponLocations(Location[][] board) {
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
     * Assign Weapons to locations and assign locations to Weapons
     * and set the Weapons token.
     *
     * @param weapons - the weapons on the board
     * @param game - the current game
     */
    public void setWeaponLocations(List<Weapon> weapons, Game game) {
        List<Location> locations = allWeaponLocations(game.getBoard().getBoard());
        for (int i = 0; i < Weapon.NUM_WEAPONS; i++) {
            setupWeapon(weapons.get(i), locations.get(i), weapons.get(i).getChar());
        }
    }

    /**
     * Assigns weapons location and locations weapon
     * and sets the weapons token for the board.
     *
     * @param weapon   - weapon to be setup
     * @param location - location to assign to weapon
     * @param token - the current weapons token
     *
     */
    public void setupWeapon(Weapon weapon, Location location, String token) {
        weapon.setLocation(location);
        location.setBoardToken(weapon);
        weapon.setToken(token);
        location.setAccessible(false);
    }


    /**
     *
     * @param players - all current players on the board
     * @param numberPlayers - current number of players
     * @param layout - the layout string of the board
     * @param board - the current board
     * @param game - the current game
     */
    public void setupBoard(List<Player> players, int numberPlayers, String layout, Location[][] board, Game game) {
        game.getBoard().setRooms(new ArrayList<>());
        List<Location> rooms = game.getBoard().getRooms();
        int x;
        int y = 0;
        int index = 0;

        char[] bl = layout.toCharArray();
        while (bl[index] != 'E') {
            x = 0;
            while (x < 24) {
                char c = bl[index];
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
                index++;
            }
            y++;
        }
        // setup players and weapons
        setPLayerLocations(players, numberPlayers, board);

    }

    /**
     * Assign Players to locations and assign locations to Players
     * and set the Players tokens
     * @param players List of players that will be playable
     * @param numberPlayers - the current number of players
     * @param board - the current board
     */
    public void setPLayerLocations(List<Player> players, int numberPlayers, Location[][] board) {
        List<Location> locations = allPlayerLocations(board);
        for (int i = 0; i < numberPlayers; i++) {
            setupPlayer(players.get(i), locations.get(i), String.valueOf(i + 1));
        }
    }

    /**
     * Assigns players location and locations player
     * and sets the players token for the board.
     *
     * @param player   - player to be setup
     * @param location - location to assign to player
     * @param token - the current players token
     */
    public void setupPlayer(Player player, Location location, String token) {
        player.setLocation(location);
        location.setBoardToken(player);
        player.setToken(token);
        location.setAccessible(false);
    }

    /**
     * Gets a list of all the possible initial locations
     * for all the players
     *
     * @param board the array of locations
     * @return List of Location's
     */
    public List<Location> allPlayerLocations(Location[][] board) {
        List<Location> locations = new ArrayList<>();
        locations.add(board[9][0] = new Hallway(9, 0));
        locations.add(board[14][0] = new Hallway(14, 0));
        locations.add(board[23][6] = new Hallway(23, 6));
        locations.add(board[23][19] = new Hallway(23, 19));
        locations.add(board[7][24] = new Hallway(7, 24));
        locations.add(board[0][17] = new Hallway(0, 17));

        return locations;
    }


    /**
     * If the specified player is in a Room then the player can be moved to
     * the specified Room
     *
     * @param player   - the player to be Moved
     * @param goToRoom - the Room to move the player too.
     * @param players  - the list of all players on the board.
     * @param game - the current game
     */
    public void movePlayerToRoom(Player player, Room goToRoom, List<Player> players, Game game) {
        if (players.contains(player)) {
            for (Location room : game.getBoard().getRooms()) {
                if (room.getName().equals(goToRoom.getName())) {
                    if (room.getToken() == null) {
                        movePlayer(player, room.getX(), room.getY(), game.getBoard().getBoard());
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
     * @param weapon   - the player to be Moved
     * @param goToRoom - the Room to move the player too.
     * @param game - the current game
     */
    public void moveWeaponToRoom(Weapon weapon, Room goToRoom, Game game) {
        for (Location room : game.getBoard().getRooms()) {
            if (room.getName().equals(goToRoom.getName())) {
                if (room.getToken() == null) {
                    moveWeapon(weapon, room.getX(), room.getY(), game.getBoard().getBoard());
                    break;
                }
            }
        }
    }


    /**
     * Move a weapon Token to the specified co-ordinated.
     *
     * @param weapon - the weapon token to be moved
     * @param x      - the x co- ordinate on the board
     * @param y      - the y co-ordinate on the board
     * @param board - the array of locations
     * @return - if weapon was moved or not.
     */
    public boolean moveWeapon(Weapon weapon, int x, int y, Location[][] board) {
        if (board[x][y].isAccessible() && board[x][y].isRoom()) {
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
     * Moves the specified player to he specified co-ordinates
     * Players cannot move to a wall and cannot move outside of
     * the board.
     *
     * @param player - the player moving
     * @param x      - x co-ordinate to move too
     * @param y      - y x co-ordinate to move too
     * @param board - the array of locations
     * @return - if the move was valid
     */
    public boolean movePlayer(Player player, int x, int y, Location[][] board) {
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


}