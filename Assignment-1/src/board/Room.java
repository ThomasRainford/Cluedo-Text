package board;

import cards.WeaponCard;


public class Room extends Location{

    private WeaponCard weaponCard;

    public Room(int x, int y, String name){
        super(x, y, name, null, true);
    }




    /* Getters and Setters */

    public WeaponCard getWeaponCard() {
        return weaponCard;
    }

    public void setWeaponCard(WeaponCard weaponCard) {
        this.weaponCard = weaponCard;
    }
}
