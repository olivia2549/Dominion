public class PointTokens {
    private int points;

    public PointTokens() {
        points = 0;
    }

    public void increasePoints(int incrementNum) {
        points += incrementNum;
    }

    public int getPoints() {
        return points;
    }
}
