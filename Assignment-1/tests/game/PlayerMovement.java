package game;

import board.Board;
import board.Location;
import board.token.Player;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class PlayerMovement {

    /**
     * Test a players movement from a Hallway to a
     * Hallway.
     */
    @Test
    public void testPlayerMovementHallway(){
        Game game = new Game();
        Board b = game.getBoard();
        Location[][] board =  b.getBoard();
        setupGame(game);
        Player player = game.getPlayers().get(0);
        b.movePlayer(player, 7, 1);

        b.printBoard();
        assertEquals(board[7][1], player.getLocation());

    }


    /**
     * Test a players movement from a Room to a Room
     */
    @Test
    public void testPlayerMovementRoom(){
        Game game = new Game();
        Board b = game.getBoard();
        Location[][] board =  b.getBoard();
        setupGame(game);
        Player player = game.getPlayers().get(0);

        b.movePlayer(player, 11, 1);
        b.movePlayer(player, 12, 1);

        b.printBoard();
        assertEquals(board[12][1], player.getLocation());
    }


    /**
     * Test players movement from a Hallway to a Wall
     */
    @Test
    public void testPlayerMovementWall(){
        Game game = new Game();
        Board b = game.getBoard();
        Location[][] board =  b.getBoard();
        setupGame(game);
        Player player = game.getPlayers().get(0);

        b.movePlayer(player, 10, 0);

        b.printBoard();
        assertEquals(board[9][0], player.getLocation());
    }


    /**
     * Test a players movement from a Room to a Wall
     */
    @Test
    public void testPlayerMovementWall_2(){
        Game game = new Game();
        Board b = game.getBoard();
        Location[][] board =  b.getBoard();
        setupGame(game);
        Player player = game.getPlayers().get(0);

        b.movePlayer(player, 11,1);
        b.movePlayer(player, 10, 1);

        b.printBoard();
        assertEquals(board[11][1], player.getLocation());
    }


    /**
     * Test a players movement from a Hallway
     * to another players Location
     */
    @Test
    public void testPlayerMovementPlayer(){
        Game game = new Game();
        Board b = game.getBoard();
        Location[][] board =  b.getBoard();
        setupGame(game);
        Player player = game.getPlayers().get(0);

        b.movePlayer(player, 14, 1);
        b.movePlayer(player, 14, 0);

        b.printBoard();
        assertEquals(board[14][1], player.getLocation());
    }


    /**
     * Test a players movement from a Hallway to
     * out of the board
     */
    @Test
    public void testPlayerMovementOutOfBounds(){
        Game game = new Game();
        Board b = game.getBoard();
        Location[][] board =  b.getBoard();
        setupGame(game);
        Player player = game.getPlayers().get(0);

        b.movePlayer(player, 9, 0);
        b.movePlayer(player, 9, -1);

        b.printBoard();
        assertEquals(board[9][0], player.getLocation());
    }


    /**
     * Test a players movement from a Hallway to a Room
     * and vice versa
     */
    @Test
    public void testPlayersMovementRoomToHallway(){
        Game game = new Game();
        Board b = game.getBoard();
        Location[][] board =  b.getBoard();
        setupGame(game);
        Player player = game.getPlayers().get(0);

        b.movePlayer(player, 9, 6);
        b.movePlayer(player, 9, 7);

        b.printBoard();
        assertEquals(board[9][7], player.getLocation());

        b.movePlayer(player, 9, 6);
        assertEquals(board[9][6], player.getLocation());
    }


    /**
     * Test player movement with a given input
     * from a Hallway to a Hallway
     */
    @Test
    public void testPlayerMovementInput(){
        Game game = new Game();
        Board b = game.getBoard();
        Location[][] board =  b.getBoard();
        setupGame(game);
        Player player = game.getPlayers().get(0);

        player.getMoveDirection("s", board[7][0], b);

        assertEquals(board[7][1], player.getLocation());
    }


    /**
     * Test player movement with a given input
     * from a Hallway to a Room and vice versa
     */
    @Test
    public void testPlayerMovementInput_2(){
        Game game = new Game();
        Board b = game.getBoard();
        Location[][] board =  b.getBoard();
        setupGame(game);
        Player player = game.getPlayers().get(0);

        b.movePlayer(player, 8, 5);

        player.getMoveDirection("d", board[8][5], b);
        assertEquals(board[9][5], player.getLocation());

        // have to move player to different location because a player
        // cannot move to a location they have already visited.
        b.movePlayer(player, 9, 6);

        player.getMoveDirection("s", board[9][6], b);
        assertEquals(board[9][7], player.getLocation());
    }






    /**
     * Sets up a game by initialising Tokens and locations
     *
     * @param game - the current game
     */
    public void setupGame(Game game){
        game.initialisePlayers(3);
        game.initialiseWeapons();
        game.getBoard().setupBoard(game.getPlayers(), game.getWeapons(), 3);
        game.initialiseCards(3);
        game.getBoard().printBoard();
    }

}