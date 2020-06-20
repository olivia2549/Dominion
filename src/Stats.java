public class Stats {
    private int money;
    private int actions;
    private int buys;

    public Stats() {
        money = 0;
        actions = 1;
        buys = 1;
    }

    public int getMoney() {
        return money;
    }

    public String changeMoney(int incrementNum) {
        this.money += incrementNum;
        return "Added +$" + incrementNum + ".";
    }

    public int getActions() {
        return actions;
    }

    public String changeActions(int incrementNum) {
        this.actions += incrementNum;
        return "Added " + incrementNum + " Action(s).";
    }

    public int getBuys() {
        return buys;
    }

    public String changeBuys(int incrementNum) {
        this.buys += incrementNum;
        return "Added " + incrementNum + " Buy(s).";
    }

    public String toString() {
        return ("Money: " + money + "\nBuys: " + buys + "\nActions: " + actions);
    }
}
