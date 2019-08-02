package cards;


public abstract class Card {

    private String name;

    public Card(String name){
        this.name = name;
    }



    /* Getters and Setters */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
