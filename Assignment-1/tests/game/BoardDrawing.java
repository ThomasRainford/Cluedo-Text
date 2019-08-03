package game;

import board.Board;
import board.Room;
import board.token.Player;
import board.token.Weapon;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class BoardDrawing {

//    String layout =
//            "=======1======2=========\n" +
//            "=======##=====###=======\n" +
//            "=KKKK=##=BBBBBB=##=CCCC=\n" +
//            "=KKKK=##=BBBBBB=##=CCCC=\n" +
//            "=KKKK=##=BBBBBB=##=CCC9=\n" +
//            "=KKK7=###BBBBBB#####====\n" +
//            "===#==##=BBBBB8=#######3\n" +
//            "=#######=#===#==#######=\n" +
//            "=#################======\n" +
//            "========##########=IIII=\n" +
//            "=DDDDDD=###########III11=\n" +
//            "=DDDDDD###########=IIII=\n" +
//            "=DDDDDD=##########===#==\n" +
//            "=DDDDDD=###############4\n" +
//            "=DDDDD10=#########===#===\n" +
//            "=====#==##########=LLLL=\n" +
//            "=#################LLLL12=\n" +
//            "6#########==##==##=LLLL=\n" +
//            "=#########=HHHH=###=====\n" +
//            "====#===##=HHHH=#######=\n" +
//            "=OOOOO=###=HHHH=##=#====\n" +
//            "=OOOOO=###=HHHH=##=SSSS=\n" +
//            "=OOOOO=###=HHHH=##=SSSS=\n" +
//            "=OOOOO=###=HHHH=##=SSSS=\n" +
//            "========5===============\n";


    /**
     * Test initial board with all players
     */
    @Test
    public void testInitialBoard_1(){
        String layout =
            "=======1======2=========\n" +
            "=======##=====###=======\n" +
            "=KKKK=##=BBBBBB=##=CCCC=\n" +
            "=KKKK=##=BBBBBB=##=CCCC=\n" +
            "=KKKK=##=BBBBBB=##=CCC9=\n" +
            "=KKK7=###BBBBBB#####====\n" +
            "===#==##=BBBBB8=#######3\n" +
            "=#######=#===#==#######=\n" +
            "=#################======\n" +
            "========##########=IIII=\n" +
            "=DDDDDD=###########III11=\n" +
            "=DDDDDD###########=IIII=\n" +
            "=DDDDDD=##########===#==\n" +
            "=DDDDDD=###############4\n" +
            "=DDDDD10=#########===#===\n" +
            "=====#==##########=LLLL=\n" +
            "=#################LLLL12=\n" +
            "6#########==##==##=LLLL=\n" +
            "=#########=HHHH=###=====\n" +
            "====#===##=HHHH=#######=\n" +
            "=OOOOO=###=HHHH=##=#====\n" +
            "=OOOOO=###=HHHH=##=SSSS=\n" +
            "=OOOOO=###=HHHH=##=SSSS=\n" +
            "=OOOOO=###=HHHH=##=SSSS=\n" +
            "========5===============\n";

        Game game = new Game();
        Board b = setupGame(game, 6);

        assertEquals(layout, b.getBoardAsString());

    }



    /**
     * Test initial board with 3 players
     */
    @Test
    public void testInitialBoard_2(){
        String layout =
                "=======1======2=========\n" +
                "=======##=====###=======\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCC9=\n" +
                "=KKK7=###BBBBBB#####====\n" +
                "===#==##=BBBBB8=#######3\n" +
                "=#######=#===#==#######=\n" +
                "=#################======\n" +
                "========##########=IIII=\n" +
                "=DDDDDD=###########III11=\n" +
                "=DDDDDD###########=IIII=\n" +
                "=DDDDDD=##########===#==\n" +
                "=DDDDDD=################\n" +
                "=DDDDD10=#########===#===\n" +
                "=====#==##########=LLLL=\n" +
                "=#################LLLL12=\n" +
                "##########==##==##=LLLL=\n" +
                "=#########=HHHH=###=====\n" +
                "====#===##=HHHH=#######=\n" +
                "=OOOOO=###=HHHH=##=#====\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "========#===============\n";

        Game game = new Game();
        Board b = setupGame(game, 3);

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
                "=======#======2=========\n" +
                "=======1#=====###=======\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCC9=\n" +
                "=KKK7=###BBBBBB#####====\n" +
                "===#==##=BBBBB8=#######3\n" +
                "=#######=#===#==#######=\n" +
                "=#################======\n" +
                "========##########=IIII=\n" +
                "=DDDDDD=###########III11=\n" +
                "=DDDDDD###########=IIII=\n" +
                "=DDDDDD=##########===#==\n" +
                "=DDDDDD=################\n" +
                "=DDDDD10=#########===#===\n" +
                "=====#==##########=LLLL=\n" +
                "=#################LLLL12=\n" +
                "##########==##==##=LLLL=\n" +
                "=#########=HHHH=###=====\n" +
                "====#===##=HHHH=#######=\n" +
                "=OOOOO=###=HHHH=##=#====\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "========#===============\n";

        Game game = new Game();
        Board b = setupGame(game, 3);
        Player player = game.getPlayers().get(0);
        b.movePlayer(player, 7, 1);

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
                "=======#======2=========\n" +
                "=======##=====###=======\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCC9=\n" +
                "=KKK7=###1BBBBB#####====\n" +
                "===#==##=BBBBB8=#######3\n" +
                "=#######=#===#==#######=\n" +
                "=#################======\n" +
                "========##########=IIII=\n" +
                "=DDDDDD=###########III11=\n" +
                "=DDDDDD###########=IIII=\n" +
                "=DDDDDD=##########===#==\n" +
                "=DDDDDD=################\n" +
                "=DDDDD10=#########===#===\n" +
                "=====#==##########=LLLL=\n" +
                "=#################LLLL12=\n" +
                "##########==##==##=LLLL=\n" +
                "=#########=HHHH=###=====\n" +
                "====#===##=HHHH=#######=\n" +
                "=OOOOO=###=HHHH=##=#====\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "========#===============\n";

        Game game = new Game();
        Board b = setupGame(game, 3);
        Player player = game.getPlayers().get(0);
        b.movePlayer(player, 8, 5);

        b.movePlayer(player, 9,5);
        assertEquals(layout, b.getBoardAsString());
    }


    /**
     * Test suggested player is moved to the current players
     * room along with the suggested weapon
     */
    @Test
    public void testSuggestedTokenMovement_1(){
        String layout =
                "=======#======2=========\n" +
                "=======##=====###=======\n" +
                "=KKKK=##=37BBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCC9=\n" +
                "=KKKK=###1BBBBB#####====\n" +
                "===#==##=BBBBB8=########\n" +
                "=#######=#===#==#######=\n" +
                "=#################======\n" +
                "========##########=IIII=\n" +
                "=DDDDDD=###########III11=\n" +
                "=DDDDDD###########=IIII=\n" +
                "=DDDDDD=##########===#==\n" +
                "=DDDDDD=###############4\n" +
                "=DDDDD10=#########===#===\n" +
                "=====#==##########=LLLL=\n" +
                "=#################LLLL12=\n" +
                "6#########==##==##=LLLL=\n" +
                "=#########=HHHH=###=====\n" +
                "====#===##=HHHH=#######=\n" +
                "=OOOOO=###=HHHH=##=#====\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "========5===============\n";

        Game game = new Game();
        Board b = setupGame(game, 6);
        Player player = game.getPlayers().get(0);
        b.movePlayer(player, 9, 5);

        // create the suggestion
        CharacterCard cc = new CharacterCard("Mrs. Peacock");
        RoomCard rc = new RoomCard(player.getLocation().getName());
        WeaponCard wc = new WeaponCard("Candlestick");
        Suggestion suggestion = new Suggestion(cc, rc, wc, true);

        // move the tokens
        b.movePlayerToRoom(suggestion.getCharacterCard().getToken(game.getPlayers()), (Room) player.getLocation(), game.getPlayers());
        b.moveWeaponToRoom(suggestion.getWeaponCard().getToken(game.getWeapons()), (Room) player.getLocation());

        b.printBoard();

        // Suggested Tokens are moved to top left
        assertEquals(b.getBoard()[9][2] ,suggestion.getCharacterCard().getToken(game.getPlayers()).getLocation());
        assertEquals(b.getBoard()[10][2] ,suggestion.getWeaponCard().getToken(game.getWeapons()).getLocation());

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
                "=======#======2=========\n" +
                "=======##=====###=======\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCCC=\n" +
                "=KKKK=##=BBBBBB=##=CCC9=\n" +
                "=KKK7=###BBBBBB#####====\n" +
                "===#==##=BBBBB8=#######3\n" +
                "=#######=#===#==#######=\n" +
                "=#################======\n" +
                "========##########=IIII=\n" +
                "=DDDDDD=###########IIII=\n" +
                "=DDDDDD###########=IIII=\n" +
                "=DDDDDD=##########===#==\n" +
                "=DDDDDD=###############4\n" +
                "=DDDDD10=#########===#===\n" +
                "=====#==##########=LLLL=\n" +
                "=#################LLLL12=\n" +
                "##########==##==##=LLLL=\n" +
                "=#########=1611H=###=====\n" +
                "====#===##=HHHH=#######=\n" +
                "=OOOOO=###=HHHH=##=#====\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "=OOOOO=###=HHHH=##=SSSS=\n" +
                "========5===============\n";

        Game game = new Game();
        Board b = setupGame(game, 6);
        Player player = game.getPlayers().get(0);
        b.movePlayer(player, 11, 18);

        // create the suggestion
        CharacterCard cc = new CharacterCard("Col. Mustard");
        RoomCard rc = new RoomCard(player.getLocation().getName());
        WeaponCard wc = new WeaponCard("Rope");
        Suggestion suggestion = new Suggestion(cc, rc, wc, true);

        // move the tokens
        b.movePlayerToRoom(suggestion.getCharacterCard().getToken(game.getPlayers()), (Room) player.getLocation(), game.getPlayers());
        b.moveWeaponToRoom(suggestion.getWeaponCard().getToken(game.getWeapons()), (Room) player.getLocation());

        b.printBoard();

        // Suggested Tokens are moved to top left
        assertEquals(b.getBoard()[12][18] ,suggestion.getCharacterCard().getToken(game.getPlayers()).getLocation());
        assertEquals(b.getBoard()[13][18] ,suggestion.getWeaponCard().getToken(game.getWeapons()).getLocation());

        // check board is drawn correctly
        assertEquals(layout, b.getBoardAsString());
    }




    /**
     * Sets up a game by initialising Tokens and locations
     *
     * @param game
     */
    public Board setupGame(Game game, int numberPlayer){
        game.initialisePlayers(numberPlayer);
        game.initialiseWeapons();
        game.getBoard().setupBoard(game.getPlayers(), game.getWeapons(), numberPlayer);
        game.initialiseCards(numberPlayer);
        game.getBoard().printBoard();
        return game.getBoard();
    }


}