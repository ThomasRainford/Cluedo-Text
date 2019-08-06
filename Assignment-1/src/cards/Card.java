package cards;


import board.token.Token;

import java.util.List;

public abstract class Card {

    private String name;

    public Card(String name){
        this.name = name;
    }


    /**
     * Gets the Token in the specified list which matched the name
     * field.
     *
     * @param tokens - the List of Tokens
     * @param <T> - A Token
     * @return - A Token
     */
    public <T extends Token> T getToken(List<T> tokens){
        for(T token : tokens){
            if(this.getName().equalsIgnoreCase(token.getName())){
                return token;
            }
        }
        return null;
    }



    /* Getters and Setters */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
