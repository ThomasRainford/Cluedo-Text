package game;

import board.*;
import cards.Card;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;

import java.util.*;

public class Game {

    private List<Player> players;
    private List<Card> cards;            // cards in play
    private List<Card> allCards;         // all cards including murder cards
    private Board board = new Board();

    private Player winner;

    private List<Card> murder;

    public Game() {
        play();
    }


    /**
     * Starts the game
     * Carry's out player turns
     */
    public void play() {
        setupGame();
        Scanner sc = new Scanner(System.in);
        ArrayList<Player> playersTemp = new ArrayList<>();

        boolean won = false;
        boolean lost = false;
        while (!won) {
            for (Player player : players) {
                player.printEndOfTurn();
                System.out.println("\n" + player.getName() + "'s turn " + "(" + player.getToken() + ")");

                // ask player to roll the rice then carry out the players moves
                String answer;
                do {
                    System.out.print("\nRoll Dice?(r): ");
                    answer = sc.next();

                } while (!answer.equals("r"));

                board.printBoard();

                int diceValue = rollDice();
                System.out.println("Dice Roll: " + diceValue);

                player.doPlayerMoves(board, diceValue);

                // check if player is in a room
                if(player.getLocation().isRoom()){
                    System.out.println(player.getName() + " is in a Room.");
                    Suggestion action = getAction(player, sc);

                    // user made an suggestion
                    if(action.isSuggestion()) {
                        doRefutation(player, action, sc);

                    } else { // user made an accusation
                        if (correctAccusation(action)) { // user made correct accusation so has won
                            winner = player;
                            won = true;
                            break;

                        } else { // user made incorrect accusation so is removed from the game
                            System.out.println("Incorrect Accusation!");
                            playersTemp.addAll(players);
                            playersTemp.remove(player);
                            System.out.println(player.getName() + " is out of the game!");
                            lost = true;
                        }
                    }
                }
            }
            if(lost) {
                players = playersTemp;
                lost = false;
            }
        }
        System.out.println(winner.getName() + " has won!");
        printMurder();

    }


    /**
     * Setup the board, player and cards as well as
     * getting user input for the games parameters
     * i.e. The number of players
     */
    private void setupGame() {
        Scanner sc = new Scanner(System.in);
        int numberPlayers;
        do {
            System.out.print("Number of Players: ");
            numberPlayers = sc.nextInt();

        } while (numberPlayers < 3 || numberPlayers > 6);


        initialisePlayers(numberPlayers);
        board.setupBoard(players, numberPlayers);
        initialiseCards(numberPlayers);
        board.printBoard();
    }


    /**
     * Rolls the dice.
     *
     * Gets two random number between 1 and 6 then adds them together
     */
    private int rollDice() {
        Random random = new Random();
        int diceOne = random.nextInt(6 - 1) + 1;
        int diceTwo = random.nextInt(6 - 1) + 1;

        return diceOne + diceTwo;
    }


    /**
     * Initialise all the player objects and remove a
     * specified number
     */
    private void initialisePlayers(int numberPlayers) {
        players = new ArrayList<>();

        players.add(new Player("Mrs. White", null));
        players.add(new Player("Mr. Green", null));
        players.add(new Player("Mrs. Peacock", null));
        players.add(new Player("Prof. Plum", null));
        players.add(new Player("Miss Scarlett", null));
        players.add(new Player("Col. Mustard", null));

        // remove players to match the given number of players to play
        for (int i = 5; i > numberPlayers - 1; i--) {
            players.remove(i);
        }
    }


    /**
     * Initialise all the card objects
     */
    private void initialiseCards(int numberPlayers) {
        cards = new ArrayList<>();
        allCards = new ArrayList<>();

        // Character cards
        cards.add(new CharacterCard("Mrs. White"));
        cards.add(new CharacterCard("Mr. Green"));
        cards.add(new CharacterCard("Mrs. Peacock"));
        cards.add(new CharacterCard("Prof. Plum"));
        cards.add(new CharacterCard("Miss Scarlett"));
        cards.add(new CharacterCard("Col. Mustard"));

        // Room cards
        cards.add(new RoomCard("Kitchen"));
        cards.add(new RoomCard("Ball Room"));
        cards.add(new RoomCard("Conservatory"));
        cards.add(new RoomCard("Dining Room"));
        cards.add(new RoomCard("Billiard Room"));
        cards.add(new RoomCard("Library"));
        cards.add(new RoomCard("Lounge"));
        cards.add(new RoomCard("Hall"));
        cards.add(new RoomCard("Study"));

        // Weapon cards
        cards.add(new WeaponCard("Candlestick"));
        cards.add(new WeaponCard("Dagger"));
        cards.add(new WeaponCard("Lead Pipe"));
        cards.add(new WeaponCard("Revolver"));
        cards.add(new WeaponCard("Rope"));
        cards.add(new WeaponCard("Spanner"));

        Collections.shuffle(cards);
        allCards.addAll(cards);

        System.out.println("ALL CARDS:");
        for(Card card : allCards){
            System.out.print(card.getName() + " ");
        }

        // set the murder cards and deal remaining cards
        setMurder();
        System.out.print("MURDER: ");
        for(Card card : murder){
            System.out.print(card.getName() + " ");
        }
        System.out.println();
        dealCards(numberPlayers);
    }


    /**
     * Assign a random Character, Room and Weapon Card to
     * the murder List
     */
    private void setMurder() {
        Random random = new Random();
        murder = new ArrayList<>();

        // add CharacterCard
        while (murder.size() < 1) {
            int randomIndex = random.nextInt(cards.size());
            Card element = cards.get(randomIndex);
            if (element instanceof CharacterCard) {
                murder.add(element);
                cards.remove(element);
            }
        }

        // add RoomCard
        while (murder.size() < 2) {
            int randomIndex = random.nextInt(cards.size());
            Card element = cards.get(randomIndex);
            if (element instanceof RoomCard) {
                murder.add(element);
                cards.remove(element);
            }
        }

        // add WeaponCard
        while (murder.size() < 3) {
            int randomIndex = random.nextInt(cards.size());
            Card element = cards.get(randomIndex);
            if (element instanceof WeaponCard) {
                murder.add(element);
                cards.remove(element);
            }
        }
    }


    /**
     * Assign cards to all players hands
     * Only assign to playable players
     */
    private void dealCards(int numberPlayers) {
        int playerIndex = 0;

        for (Card card : cards) {
            if (playerIndex == numberPlayers) {
                playerIndex = 0;
            }
            players.get(playerIndex++).addToHand(card);
        }
    }


    /**
     * Do action
     */
    private Suggestion getAction(Player player, Scanner sc){
        // ask player to make Suggestion or accusation
        String action;
        do {
            System.out.print("Suggestion or Accusation: ");
            action = sc.next();

        } while (!action.equals("s") && !action.equals("a"));
        // player chose Suggestion
        if(action.equals("s")) {
            Suggestion suggestion = doAction(player, sc, true);
            board.movePlayerToRoom(suggestion.getCharacterCard().getPlayer(players), (Room) player.getLocation(), players);

            return suggestion;


        } else { // player chose accusation
           return doAction(player, sc, false);
        }
    }

    /**
     * ask user for a suggestion by asking for each of
     * the three types of cards
     *
     * @param player - player making an action
     * @param sc - scanner
     * @param isSuggestion - if action is a suggestion or accusation
     * @return - the action (Suggestion)
     */
    private Suggestion doAction(Player player, Scanner sc, boolean isSuggestion) {
        CharacterCard cc;
        RoomCard rc;
        WeaponCard wc;

        // ask for CharacterCard
        String character;
        do {
            System.out.print("Character Card: ");
            character = sc.nextLine();

        } while (!isValidCharacterCard(character));

        // ask for RoomCard
        String room = player.getLocation().getName();
        System.out.println("Room: " + room);


        // ask for WeaponCard
        String weapon;
        do {
            System.out.print("Weapon Card: ");
            weapon = sc.nextLine();

        } while (!isValidWeaponCard(weapon));

        cc = player.getCardByName(character, allCards);
        rc = player.getCardByName(room, allCards);
        wc = player.getCardByName(weapon, allCards);

        return new Suggestion(cc, rc, wc, isSuggestion);
    }


    /**
     * Checks if a accusation matches the murder
     *
     * @param accusation - the Suggestion (accusation) made by the player
     * @return - if the accusation was correct
     */
    private boolean correctAccusation(Suggestion accusation){
        CharacterCard cc = accusation.getCharacterCard();
        RoomCard rc = accusation.getRoomCard();
        WeaponCard wc = accusation.getWeaponCard();

        return (murder.contains(cc) && murder.contains(rc) && murder.contains(wc));
    }


    /**
     * Checks if the name given matches the name of a
     * CharacterCard
     *
     * @param cardName - the name of the card
     * @return - if the card is valid
     */
    private boolean isValidCharacterCard(String cardName){
        for(Card card : allCards){
            if(card instanceof CharacterCard && card.getName().equals(cardName)){
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if the name given matches the name of a
     * RoomCard
     *
     * @param cardName - the name of the card
     * @return - if the card is valid
     */
    private boolean isValidRoomCard(String cardName){
        for(Card card : allCards){
            if(card instanceof RoomCard && card.getName().equals(cardName)){
                return true;
            }
        }
        return false;
    }


    /**
     * Checks if the name given matches the name of a
     * WeaponCard
     *
     * @param cardName - the name of the card
     * @return - if the card is valid
     */
    private boolean isValidWeaponCard(String cardName){
        for(Card card : allCards){
            if(card instanceof WeaponCard && card.getName().equals(cardName)){
                return true;
            }
        }
        return false;
    }



    /**
     * Does the refutation logic
     *
     * @param player - player who made the suggestion
     * @param action - the suggestion
     * @param sc - scanner
     */
    private void doRefutation(Player player, Suggestion action, Scanner sc){
        // ask each player to refute if they can
        for(Player refuter : players){
            if(refuter != player) {
                action.printSuggestion();
                System.out.println(refuter.getName() + "'s turn to refute");
                refuter.printPlayerHand();

                // check if player can refute
                if(refuter.cantRefute(action)) {
                    String refutationCard;
                    do {
                        System.out.print("Refutation Card: ");
                        refutationCard = sc.nextLine();

                    } while (!refuter.isInHand(refutationCard) && !refuter.isSuggestionCard(action, refutationCard));

                    Game.clearConsole();
                    System.out.println(refuter.getName() + ", pass too " + player.getName());

                    // ask player to reveal refutation card
                    String reveal;
                    do {
                        System.out.print("Reveal Card: ");
                        reveal = sc.nextLine();

                    } while (!reveal.equals("y"));
                    System.out.println(refutationCard);


                } else { // player cant refute
                    String response;
                    do {
                        System.out.print(refuter.getName() + " can't refute(ok): ");
                        response = sc.nextLine();

                    } while (!response.equals("ok"));
                }
            }

        }
    }



    /* PRINT METHODS */

    /**
     * Print all the cards in all the players hands
     */
    public void printAllCards() {
        for (Player player : players) {
            player.printPlayerHand();
        }
    }


    /**
     * Prints the cards in the murder
     * <p>
     * Shouldn't be used until a player makes an accusation
     */
    private void printMurder() {
        for (Card card : murder) {
            System.out.print(card.getName() + ", ");
        }
        System.out.println();
    }


    public static void clearConsole() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new Game();
    }
}
