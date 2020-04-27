public class Card {
    private String type;
    private String name;
    private String description;
    private int cost;
    private int value;
    private int numRemaining;

    public Card() {
        type = "";
        name = "";
        description = "";
        cost = 0;
        value = 0;
        numRemaining = 0;
    }

    public Card(String type, String name, String description, int cost, int value, int numRemaining) {
        this.type = type;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.value = value;
        this.numRemaining = numRemaining;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getValue() {
        return value;
    }

    public int getNumRemaining() {
        return numRemaining;
    }

    public void decreaseNumRemaining() {
        --numRemaining;
    }

    public String toString() {
        String card = "";
        if ((type.equals("Action")) || (type.equals("Action-Attack"))) {
            card += (name + " $" + cost + "\n" + description + " (" + numRemaining + " remaining)");
        } else {
            card += (name + " $" + cost + " (" + numRemaining + " remaining)");
        }

        return card;
    }

    public String cardInHand() {
        String card = "";
        card += (type + ": " + name + " - " + description);
        return card;
    }
}
