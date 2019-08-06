package board.token;

import board.Board;
import board.Location;
import cards.Card;
import game.Game;
import game.Suggestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Player initial co-ordinates
 * <p>
 * Mrs. White:     7, 0    token: 1
 * Mr. Green:      14, 0   token: 2
 * Mrs. Peacock:  23, 6    token: 3
 * Prof. Plum:     23, 13  token: 4
 * Miss Scarlett:  24, 8   token: 5
 * Col. Mustard:   0, 17   token: 6
 */
public class Player extends Token {
    private List<Card> hand;

    private boolean didMove;
    private boolean stopMoves;

    private List<Location> turnLocations = new ArrayList<>(); // keeps track of a players locations during a turn

    public Player(String name, Location location) {
        super(name, location);
        hand = new ArrayList<>();
    }


    /**
     * Moves a player on the board by the direction
     * inputted.
     * <p>
     * Also prints some information about the player
     * - Moves remaining
     * - Current location after move
     * - Players hand
     *
     * @param board       - the current board
     * @param numberMoves - maximum number of moves
     */
    public void doPlayerMoves(Board board, int numberMoves) {
        int i = 0;
        int remainingMoves = numberMoves;
        Scanner sc = new Scanner(System.in);


        while (i < numberMoves) {
            Location current = this.getLocation();

            if (current.isRoom()) {
                doPlayerRoomMoves(board, sc, numberMoves - i + 1);
                break;
            }

            System.out.println("Moves Remaining: " + remainingMoves + "\n");
            String direction = printMoveInformation(sc);
            getMoveDirection(direction, current, board);

            // ensure when move was invalid to not increment
            if (didMove) {
                remainingMoves--;
                i++;

            } else {
                System.err.println("Invalid Move");
            }

            if (stopMoves) {
                board.printBoard();
                break;
            }
            board.printBoard();
            printLocation(this);
        }

        if (!stopMoves && this.getLocation().isRoom()) {
            doPlayerRoomMoves(board, sc, numberMoves);
        }
        turnLocations = new ArrayList<>();
    }


    /**
     * Controls the players moves in a Room
     * There is no limit to the number of moves in a Room
     *
     * @param board       - the current board
     * @param sc          - scanner
     * @param numberMoves - the number of moves to do when the
     *                    player leaves the Room.
     */
    public void doPlayerRoomMoves(Board board, Scanner sc, int numberMoves) {
        String direction;
        do {
            direction = printMoveInformation(sc);
            getMoveDirection(direction, this.getLocation(), board);
            board.printBoard();
            printLocation(this);
            if (!this.getLocation().isRoom()) {
                doPlayerMoves(board, numberMoves);
                break;
            }

        } while (!direction.equals("q"));
    }


    /**
     * Moves the player by the specified direction.
     * Adds the players current Location to a List
     * to keep track of all visited Locations in that turn.
     *
     * @param direction - the direction the player wants to move in.
     * @param current   - the players current location
     * @param board     - the current board
     */
    public void getMoveDirection(String direction, Location current, Board board) {
        switch (direction) {
            case "w":
                didMove = board.movePlayer(this, current.getX(), current.getY() - 1);
                break;

            case "d":
                didMove = board.movePlayer(this, current.getX() + 1, current.getY());
                break;

            case "s":
                didMove = board.movePlayer(this, current.getX(), current.getY() + 1);
                break;

            case "a":
                didMove = board.movePlayer(this, current.getX() - 1, current.getY());
                break;

            case "q":
                didMove = true;
                stopMoves = true;
                break;

        }
        turnLocations.add(current);
    }


    /**
     * Checks if the player can refute by checking the players hand
     * to see if the player has a card that was refuted.
     *
     * @param suggestion - the Suggestion
     * @return - boolean
     */
    public boolean canRefute(Suggestion suggestion) {
        return (hand.contains(suggestion.getCharacterCard()) ||
                hand.contains(suggestion.getRoomCard()) ||
                hand.contains(suggestion.getWeaponCard()));
    }


    /**
     * Used when a player is refuting, to check if the name of
     * the card the player inputted is a name of one of the
     * cards in the suggestion.
     *
     * @param suggestion - the Suggestion
     * @param name       - the name of the card inputted
     * @return - boolean
     */
    public boolean isSuggestionCard(Suggestion suggestion, String name) {
        for (Card c : hand) {
            if (c.getName().equalsIgnoreCase(name)) {
                String ccName = suggestion.getCharacterCard().getName();
                String rcName = suggestion.getRoomCard().getName();
                String wcName = suggestion.getWeaponCard().getName();
                if (c.getName().equals(ccName) || c.getName().equals(rcName) || c.getName().equals(wcName)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Check if the card specified (name) is in the players hand
     *
     * @param name - the name of the card.
     * @return
     */
    public boolean isInHand(String name) {
        for (Card c : hand) {
            if (c.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Iterated through 'cards' until it finds the card with the name 'name'
     * then returns it.
     *
     * @param name  - the name of the card
     * @param cards - the list of all the possible cards
     * @return - the card found
     */
    public <T extends Card> T getCardByName(String name, List<T> cards) {
        for (T card : cards) {
            if (card.getName().equalsIgnoreCase(name)) {
                return card;
            }
        }
        return null;
    }


    /**
     * Prints the move information and returns the direction
     *
     * @param sc - scanner
     * @return - the move direction
     */
    private String printMoveInformation(Scanner sc) {
        this.printPlayerHand();
        System.out.print("Direction: ");
        String direction = sc.nextLine();
        Game.clearConsole();
        System.out.println("-------" + this.getName() + "-------");
        didMove = false;
        stopMoves = false;

        return direction;
    }


    /**
     * Print the location of the specified player.
     *
     * @param player - the player
     */
    public void printLocation(Player player) {
        System.out.println("Location: " + player.getLocation().getName());

    }

    /**
     * Prints a specified players hand
     */
    public void printPlayerHand() {
        System.out.print(this.getName() + "'s hand: ");
        for (Card card : this.getHand()) {
            System.out.print(card.getName() + " - ");

        }
        System.out.println("\n");

    }

    public void printEndOfTurn() {
        System.out.println("--------------------------------");
    }


    /**
     * adds a specified card to the players hand
     *
     * @param card - card to be added
     */
    public void addToHand(Card card) {
        hand.add(card);
    }



    /* Getter and Setters */

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public List<Location> getTurnLocations() {
        return turnLocations;
    }

    public void setTurnLocations(List<Location> turnLocations) {
        this.turnLocations = turnLocations;
    }
}
