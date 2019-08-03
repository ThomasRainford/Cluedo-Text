package game;

import board.Board;
import org.junit.jupiter.api.Test;

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

    }


    /**
     * Test player movement updates the board
     *
     * Player moves from Hallway to Room and
     * vice versa
     */
    @Test
    public void testBoardPlayerMovement_2(){

    }


    /**
     * Test suggested player is moved to the current players
     * room along with the suggested weapon
     */
    @Test
    public void testSuggestedTokenMovement_1(){

    }


    /**
     * Test suggested player is moved to the current players
     * room along with the suggested weapon
     */
    @Test
    public void testSuggestedTokenMovement_2(){

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