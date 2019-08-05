package game;

import cards.CharacterCard;
import cards.RoomCard;
import cards.WeaponCard;

/**
 *
 */
public class Suggestion {
    private CharacterCard characterCard;
    private RoomCard roomCard;
    private WeaponCard weaponCard;
    private boolean isSuggestion;

    public Suggestion(CharacterCard characterCard, RoomCard roomCard, WeaponCard weaponCard, boolean isSuggestion){
        this.characterCard = characterCard;
        this.roomCard = roomCard;
        this.weaponCard = weaponCard;
        this.isSuggestion = isSuggestion;
    }


    public void printSuggestion(){
        System.out.println("\nSuggestion: " + characterCard.getName() + " "
                + roomCard.getName() + " " + weaponCard.getName());
    }


    /* Getters and Setters */

    public CharacterCard getCharacterCard() {
        return characterCard;
    }

    public RoomCard getRoomCard() {
        return roomCard;
    }

    public WeaponCard getWeaponCard() {
        return weaponCard;
    }

    public boolean isSuggestion() {
        return isSuggestion;
    }

}
