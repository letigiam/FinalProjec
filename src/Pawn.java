public class Pawn{
    private int x;
    private int y;
    private boolean isEnemy = false;

    public Pawn (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isEnemy() {
        return isEnemy;
    }

    public void setNotEnemy (){
        isEnemy = false;
    }
}