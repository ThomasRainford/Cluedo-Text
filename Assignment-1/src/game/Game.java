package game;

import board.*;
import board.token.Player;
import board.token.Weapon;
import cards.Card;
import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;

import java.util.*;

/**
 *
 */
public class Game {

    private List<Player> players;
    private List<Player> allPlayers;     // all Players. Including players who have lost.
    private List<Weapon> weapons;
    private List<Card> cards;            // cards in play
    private List<Card> allCards;         // all cards including murder cards
    private Board board = new Board();
    private Player winner;
    private List<Card> murder;

    private List<Player> playersTemp;


    /**
     * Starts the game
     * Carry's out player turns
     */
    private void play() {
        setupGame();
        Scanner sc = new Scanner(System.in);
        playersTemp = new ArrayList<>();
        allPlayers = new ArrayList<>();
        allPlayers.addAll(players);

        boolean won = false;
        while (!won) {
            for (Player player : players) {
                player.printEndOfTurn();
                System.out.println(player.getName() + "'s turn " + "(" + player.getToken() + ")\n");

                // ask player to roll the rice then carry out the players moves
                String answer;
                do {
                    System.out.print("Roll Dice?(r):");
                    answer = sc.next();

                } while (!answer.equals("r"));
                //Game.clearConsole();
                board.printBoard();

                int diceValue = rollDice();
                System.out.println("Dice Roll: " + diceValue + "\n");

                player.doPlayerMoves(board, diceValue);

                // check if player is in a room
                if (player.getLocation().isRoom()) {
                    System.out.println(player.getName() + " is in a Room. (q) to make suggestion or accusation\n");
                    Suggestion action = getAction(player, sc);

                    // user made an suggestion
                    if (action.isSuggestion()) {
                        doRefutation(player, action, sc);

                    } else { // user made an accusation
                        boolean outcome = doAccusation(action, player);
                        if (outcome) {
                            won = true;
                        }
                        break;
                    }
                }
            }
        }
        System.out.println(winner.getName() + " has won!\n");
        printMurder();
    }


    /**
     * Setup the board, player and cards as well as
     * getting user input for the games parameters
     * i.e. The number of players
     */
    public void setupGame() {
        Scanner sc = new Scanner(System.in);
        int numberPlayers;
        do {
            System.out.print("Number of Players: ");
            numberPlayers = sc.nextInt();

        } while (numberPlayers < 3 || numberPlayers > 6);

        initialisePlayers(numberPlayers);
        initialiseWeapons();
        board.setupBoard(players, weapons, numberPlayers);
        initialiseCards(numberPlayers);
        board.printBoard();
    }


    /**
     * Rolls the dice.
     * <p>
     * Gets two random number between 1 and 6 then adds them together
     *
     * @return - the number generated
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
     *
     * @param numberPlayers - the current number of players
     */
    public void initialisePlayers(int numberPlayers) {
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
     * Initialises all Weapon Tokens
     */
    public void initialiseWeapons() {
        weapons = new ArrayList<>();

        weapons.add(new Weapon("Candlestick", null));
        weapons.add(new Weapon("Dagger", null));
        weapons.add(new Weapon("Lead Pipe", null));
        weapons.add(new Weapon("Revolver", null));
        weapons.add(new Weapon("Rope", null));
        weapons.add(new Weapon("Spanner", null));
        Collections.shuffle(weapons);
    }


    /**
     * Initialise all the card objects
     *
     * @param numberPlayers - the current number of players
     */
    public void initialiseCards(int numberPlayers) {
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

        // set the murder cards and deal remaining cards
        setMurder();
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
     *
     * @param numberPlayers - the current number of players
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
     * Get the action the player wants to do
     *
     * @param player - the player carrying out an action
     * @param sc - scanner
     *
     * @return - the suggestion generated
     */
    private Suggestion getAction(Player player, Scanner sc) {
        // ask player to make Suggestion or accusation
        String action;
        do {
            System.out.print("Suggestion or Accusation (s) or (a) : ");
            action = sc.next();

        } while (!action.equals("s") && !action.equals("a"));
        sc.nextLine();

        // player chose Suggestion
        if (action.equals("s")) {
            Suggestion suggestion = doAction(player, sc, true);

            // move the suggested player and weapon into the current players room
            board.movePlayerToRoom(suggestion.getCharacterCard().getToken(players), (Room) player.getLocation(), players);
            board.moveWeaponToRoom(suggestion.getWeaponCard().getToken(weapons), (Room) player.getLocation());

            return suggestion;

        } else { // player chose accusation
            return doAction(player, sc, false);
        }
    }

    /**
     * ask user for a suggestion by asking for each of
     * the three types of cards
     *
     * @param player       - player making an action
     * @param sc           - scanner
     * @param isSuggestion - if action is a suggestion or accusation
     * @return - the action (Suggestion)
     */
    public Suggestion doAction(Player player, Scanner sc, boolean isSuggestion) {
        CharacterCard cc;
        RoomCard rc;
        WeaponCard wc;

        // ask for CharacterCard
        String character;
        do {
            System.out.print("Character Card: ");
            character = sc.nextLine();

        } while (!isValidCharacterCard(character));

        // RoomCard
        String room = player.getLocation().getName();
        System.out.println("Room: " + room);

        // ask for WeaponCard
        String weapon;
        do {
            System.out.print("Weapon Card: ");
            weapon = sc.nextLine();

        } while (!isValidWeaponCard(weapon));

        cc = (CharacterCard) player.getCardByName(character, allCards);
        rc = (RoomCard) player.getCardByName(room, allCards);
        wc = (WeaponCard) player.getCardByName(weapon, allCards);

        return new Suggestion(cc, rc, wc, isSuggestion);
    }


    /**
     * Checks if a accusation matches the murder
     *
     * @param accusation - the Suggestion (accusation) made by the player
     * @return - if the accusation was correct
     */
    public boolean correctAccusation(Suggestion accusation) {
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
    public boolean isValidCharacterCard(String cardName) {
        for (Card card : allCards) {
            if (card instanceof CharacterCard && card.getName().equalsIgnoreCase(cardName)) {
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
    public boolean isValidWeaponCard(String cardName) {
        for (Card card : allCards) {
            if (card instanceof WeaponCard && card.getName().equalsIgnoreCase(cardName)) {
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
     * @param sc     - scanner
     */
    private void doRefutation(Player player, Suggestion action, Scanner sc) {
        // ask each player to refute if they can
        for (Player refuter : allPlayers) {
            if (refuter != player) {

                String response;
                do {
                    System.out.print(refuter.getName() + "'s turn to refute(ok): ");
                    response = sc.nextLine();

                } while (!response.equals("ok"));
                Game.clearConsole();

                action.printSuggestion();
                refuter.printPlayerHand();

                // check if player can refute
                if (refuter.canRefute(action)) {
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
                        System.out.print("Reveal Card(y): ");
                        reveal = sc.nextLine();

                    } while (!reveal.equals("y"));
                    System.out.println(player.getCardByName(refutationCard, cards).getName());

                    delay();


                } else { // player cant refute
                    String response1;
                    do {
                        System.out.print(refuter.getName() + " can't refute(ok): ");
                        response1 = sc.nextLine();

                    } while (!response1.equals("ok"));
                    delay();
                    Game.clearConsole();
                }
            }
        }
    }


    /**
     * Returns a list which doesn't contain 'player'
     *
     * @param player - the player the list won't contain
     * @return - List of players not containing 'player'
     */
    public List<Player> incorrectAccusation(Player player) {
        System.out.println("Incorrect Accusation!");
        playersTemp.addAll(players);
        playersTemp.remove(player);
        System.out.println(player.getName() + " is out of the game!");

        return playersTemp;

    }


    /**
     * Checks of 'player' made a correct accusation. Return true.
     * Otherwise return false.
     *
     * @param action - the accusation
     * @param player - the player who made the accusation
     * @return - boolean value representing if the player made the correct
     * accusation or not.
     */
    public boolean doAccusation(Suggestion action, Player player) {
        boolean won;
        if (correctAccusation(action)) { // user made correct accusation so has won
            winner = player;
            won = true;

        } else { // user made incorrect accusation so is removed from the game
            playersTemp = incorrectAccusation(player);
            players = playersTemp;
            won = false;

            if (playersTemp.size() == 1) {
                winner = playersTemp.get(0);
                won = true;
            }
        }
        return won;
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
        System.out.print("\nThe Murder was: ");
        for (Card card : murder) {
            System.out.print(card.getName() + " ");
        }
        System.out.println();
    }


    /**
     * Two second delay then clear console
     */
    public void delay(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        Game.clearConsole();
    }


    public static void clearConsole() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }


    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setWeapons(List<Weapon> weapons) {
        this.weapons = weapons;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getAllCards() {
        return allCards;
    }

    public void setAllCards(List<Card> allCards) {
        this.allCards = allCards;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public List<Player> getPlayersTemp() {
        return playersTemp;
    }

    public void setPlayersTemp(List<Player> playersTemp) {
        this.playersTemp = playersTemp;
    }

    public List<Card> getMurder() {
        return murder;
    }

    public void setMurder(List<Card> murder) {
        this.murder = murder;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public static void main(String[] args) {
        new Game().play();
    }
}
