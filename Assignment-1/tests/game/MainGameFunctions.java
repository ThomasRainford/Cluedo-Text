package game;

import board.Board;
import board.token.Player;
import cards.Card;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainGameFunctions {


    /**
     * Test an incorrect accusation which leads to
     * the player from being removed from the game
     */
    @Test
    public void testIncorrectAccusation() {
        Game game = new Game();
        Board b = setupGame(game, 6);
        Player player = game.getPlayers().get(0);
        game.setPlayersTemp(new ArrayList<>());

        List<Player> currentPlayers = game.incorrectAccusation(player);

        assertEquals(5, currentPlayers.size());
        assertFalse(currentPlayers.contains(player));

    }


    /**
     * Test a correct accusation
     */
    @Test
    public void testCorrectAccusation() {
        Game game = new Game();
        Board b = setupGame(game, 6);
        Player player = game.getPlayers().get(0);

        // create the accusation
        CharacterCard cc = new CharacterCard("Col. Mustard");
        RoomCard rc = new RoomCard(player.getLocation().getName());
        WeaponCard wc = new WeaponCard("Rope");

        List<Card> newMurder = new ArrayList<>();
        newMurder.add(cc);
        newMurder.add(rc);
        newMurder.add(wc);

        game.setMurder(newMurder);
        Suggestion accusation = new Suggestion(cc, rc, wc, false);

        assertTrue(game.correctAccusation(accusation));
    }


    /**
     * Test win scenario when players makes correct accusation
     */
    @Test
    public void testWinScenario_1() {
        Game game = new Game();
        Board b = setupGame(game, 6);
        Player player = game.getPlayers().get(0);
        game.setPlayersTemp(new ArrayList<>());

        // create the accusation
        CharacterCard cc = new CharacterCard("Col. Mustard");
        RoomCard rc = new RoomCard(player.getLocation().getName());
        WeaponCard wc = new WeaponCard("Rope");

        List<Card> newMurder = new ArrayList<>();
        newMurder.add(cc);
        newMurder.add(rc);
        newMurder.add(wc);
        game.setMurder(newMurder);

        Suggestion accusation = new Suggestion(cc, rc, wc, false);

        boolean correct = game.doAccusation(accusation, player);

        assertTrue(correct);

    }


    /**
     * Test win scenario where one player remains
     */
    @Test
    public void testWinScenario_2() {
        Game game = new Game();
        Board b = setupGame(game, 3);
        Player player_1 = game.getPlayers().get(0);
        Player player_2 = game.getPlayers().get(1);
        game.setPlayersTemp(new ArrayList<>());

        // create the accusation 1
        CharacterCard cc_1 = new CharacterCard("Col. Mustard");
        RoomCard rc_1 = new RoomCard(player_2.getLocation().getName());
        WeaponCard wc_1 = new WeaponCard("Rope");

        Suggestion accusation_1 = new Suggestion(cc_1, rc_1, wc_1, false);


        // create the accusation 2
        CharacterCard cc_2 = new CharacterCard("Col. Mustard");
        RoomCard rc_2 = new RoomCard(player_2.getLocation().getName());
        WeaponCard wc_2 = new WeaponCard("Rope");

        Suggestion accusation_2 = new Suggestion(cc_2, rc_2, wc_2, false);

        List<Card> newMurder = new ArrayList<>();
        newMurder.add(new CharacterCard("Mr. Green"));
        newMurder.add(new RoomCard("Lounge"));
        newMurder.add(new WeaponCard("Spanner"));
        game.setMurder(newMurder);

        game.doAccusation(accusation_1, player_1);
        game.setPlayersTemp(new ArrayList<>());
        game.doAccusation(accusation_2, player_2);


        assertEquals(1, game.getPlayers().size());
        assertEquals(game.getWinner(), game.getPlayers().get(0));

    }


    /**
     * Test CharacterCard validation
     */
    @Test
    public void testCharacterCardValidation() {
        Game game = new Game();
        setupGame(game, 3);

        assertTrue(game.isValidCharacterCard("Mr. Green"));
        assertTrue(game.isValidCharacterCard("Miss Scarlett"));
        assertTrue(game.isValidCharacterCard("Col. Mustard"));
        assertTrue(game.isValidCharacterCard("Mrs. White"));
        assertTrue(game.isValidCharacterCard("Mrs. Peacock"));
        assertTrue(game.isValidCharacterCard("Prof. Plum"));

        assertFalse(game.isValidCharacterCard("123"));
        assertFalse(game.isValidCharacterCard("Mr."));
        assertFalse(game.isValidCharacterCard("Green"));

    }


    /**
     * Test WeaponCard validation
     */
    @Test
    public void testWeaponCardValidation() {
        Game game = new Game();
        setupGame(game, 3);

        assertTrue(game.isValidWeaponCard("Spanner"));
        assertTrue(game.isValidWeaponCard("Dagger"));
        assertTrue(game.isValidWeaponCard("Lead Pipe"));
        assertTrue(game.isValidWeaponCard("Revolver"));
        assertTrue(game.isValidWeaponCard("Candlestick"));
        assertTrue(game.isValidWeaponCard("Rope"));

        assertFalse(game.isValidWeaponCard("3456"));
        assertFalse(game.isValidWeaponCard("Lead"));
        assertFalse(game.isValidWeaponCard("Lead Rope"));


    }


    /**
     * Sets up a game by initialising Tokens and locations
     *
     * @param game - the current game
     * @param numberPlayer - the current number of players
     * @return - the board
     */
    public Board setupGame(Game game, int numberPlayer) {
        game.initialisePlayers(numberPlayer);
        game.initialiseWeapons();
        game.getBoard().setupBoard(game.getPlayers(), game.getWeapons(), numberPlayer);
        game.initialiseCards(numberPlayer);
        game.getBoard().printBoard();
        return game.getBoard();
    }
}