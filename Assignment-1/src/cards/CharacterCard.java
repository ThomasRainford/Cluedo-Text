package cards;

import game.Player;

import java.util.List;

public class CharacterCard extends Card {

    public CharacterCard(String name){
        super(name);
    }

    public Player getPlayer(List<Player> players){
        for(Player player : players){
            if(this.getName().equals(player.getName())){
                return player;
            }
        }
        return null;
    }


}
