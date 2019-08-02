package game;

import board.Board;
import board.Location;
import board.Room;
import cards.Card;

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
public class Player {
    private String name;
    private Location location;
    private List<Card> hand;
    private String token;

    private boolean didMove;
    private boolean stopMoves;

    public Player(String name, Location location) {
        this.name = name;
        this.location = location;
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
        System.out.println("In doPlayerMoves()");
        Scanner sc = new Scanner(System.in);

        int i = 0;
        int remainingMoves = numberMoves;


        while (i < numberMoves) {
            Location current = this.getLocation();
            if(current.isRoom()){
                doPlayerRoomMoves(board, sc, numberMoves-i+1);
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

        if(!stopMoves && this.getLocation().isRoom()){
            doPlayerRoomMoves(board, sc, numberMoves);
        }
    }


    /**
     *
     * @param board
     * @param sc
     * @param numberMoves
     */
    public void doPlayerRoomMoves(Board board, Scanner sc, int numberMoves){
        System.out.println("In doPlayerRoomMoves()");
        String direction;
        do {
            direction = printMoveInformation(sc);
            getMoveDirection(direction, this.getLocation(), board);
            board.printBoard();
            printLocation(this);
            if(!this.getLocation().isRoom()){
                doPlayerMoves(board, numberMoves);
                break;
            }

        } while(!direction.equals("q"));
    }


    /**
     *
     *
     * @param direction
     * @param current
     * @param board
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
    }





    public boolean cantRefute(Suggestion suggestion) {
        return (hand.contains(suggestion.getCharacterCard()) ||
                hand.contains(suggestion.getRoomCard()) ||
                hand.contains(suggestion.getWeaponCard()));
    }


    public boolean isSuggestionCard(Suggestion suggestion, String name) {
        for (Card c : hand) {
            if (c.getName().equals(name)) {
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
     * @param name - the name of the card
     * @param cards - the list of all the possible cards
     * @return - the card found
     */
    public <T extends Card> T getCardByName(String name, List<Card> cards){
        for (Card card : cards) {
            if (card.getName().equals(name)) {
                return (T) card;
            }
        }
        return null;
    }

    /* Can possibly be replaced */

//    public CharacterCard getCharacterCardByName(String name, List<Card> cards) {
//        for (Card card : cards) {
//            if (card instanceof CharacterCard && card.getName().equals(name)) {
//                return (CharacterCard) card;
//            }
//        }
//        return null;
//    }
//
//
//    public RoomCard getRoomCardByName(String name, List<Card> cards) {
//        for (Card card : cards) {
//            if (card instanceof RoomCard && card.getName().equals(name)) {
//                return (RoomCard) card;
//            }
//        }
//        return null;
//    }
//
//
//    public WeaponCard getWeaponCardByName(String name, List<Card> cards) {
//        for (Card card : cards) {
//            if (card instanceof WeaponCard && card.getName().equals(name)) {
//                return (WeaponCard) card;
//            }
//        }
//        return null;
//    }






    /**
     * Prints the move information and returns the direction
     *
     * @param sc - scanner
     * @return - the move direction
     */
    private String printMoveInformation(Scanner sc){
        this.printPlayerHand();
        System.out.print("Direction: ");
        String direction = sc.next();
        Game.clearConsole();
        System.out.println("-------" + this.getName() + "-------");
        didMove = false;
        stopMoves = false;

        return direction;
    }


    public void printLocation(Player player) {
        System.out.println("Location: " + player.getLocation().getName());

    }

    /**
     * Prints a specified players hand
     */
    public void printPlayerHand() {
        System.out.print(this.getName() + "'s hand: ");
        for (Card card : this.getHand()) {
            System.out.print(card.getName() + ", ");
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
        // when a player is moved the location is set to not accessible
        // to prevent any other players from being on the same location
        this.location.setAccessible(false);
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
