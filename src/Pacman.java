import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

/*
    *** VALORI***
    Muro = 1
    Spazio vuoto = 2 v
    pacman = 5 v
    cibo = 0 v
    nemici = 3
     */

public class Pacman extends Thread {
    private int [][] grid;
    private int xSize;
    private int ySize;
    private String currentMove = "d";
    private int score = 0;
    enum Status  {IN_GAME, LOSE, WIN};
    private Status status = Status.IN_GAME;
    int foodCounter;
    private int levelChoice;
    private Pawn playerPawn;
    private Pawn enemyPawn1;
    private Pawn enemyPawn2;
    private Pawn enemyPawn3;
    private Pawn enemyPawn4;
    int lastEnemy1Position = 0;
    int lastEnemy2Position = 0;
    int lastEnemy3Position = 0;
    int lastEnemy4Position = 0;
    private ArrayList<Pawn> enemyList = new ArrayList<>();
    private LinkedList<Integer> lastEnemyPositionList = new LinkedList<>();



    public Pacman (int levelChoice){
        this.levelChoice = levelChoice;
        this.loadLevel();
        this.checkFoodCounter();
        this.initializePawn();
        this.setPlayerPosition();
        this.initializeEnemy();
        this.setEnemyPosition();
        this.initializeLastPosition();
    }

    private void initializeLastPosition(){
        lastEnemyPositionList.add(lastEnemy1Position);
        lastEnemyPositionList.add(lastEnemy2Position);
        lastEnemyPositionList.add(lastEnemy3Position);
        lastEnemyPositionList.add(lastEnemy4Position);
    }

    private void initializePawn() {
        if (this.levelChoice == 1){
            this.playerPawn = new Pawn(13, 0);
        }
        if (this.levelChoice == 2){
            this.playerPawn = new Pawn (8,1);
        }
    }

    private void setPlayerPosition (){
        this.grid [playerPawn.getX()][playerPawn.getY()] = 5;
    }


    //posizione dei nemici nel livello 1 e 2
    private void initializeEnemy (){
        if (this.levelChoice == 1){
            this.enemyPawn1 = new Pawn(5,11);
            this.enemyPawn2 = new Pawn(13,16);
            this.enemyPawn3 = new Pawn(21,6);
            this.enemyPawn4 = new Pawn(21,21);
        }
        if (this.levelChoice == 2){
            this.enemyPawn1 = new Pawn(7,7);
            this.enemyPawn2 = new Pawn(7,9);
            this.enemyPawn3 = new Pawn(3,7);
            this.enemyPawn4 = new Pawn(13,10);
        }
        enemyList.add(this.enemyPawn1);
        enemyList.add(this.enemyPawn2);
        enemyList.add(this.enemyPawn3);
        enemyList.add(this.enemyPawn4);

    }
    //posizione dei nemici col numero assegnato 3
    private void setEnemyPosition (){
        this.grid[enemyPawn1.getX()][enemyPawn1.getY()] =  3;
        this.grid[enemyPawn2.getX()][enemyPawn2.getY()] =  3;
        this.grid[enemyPawn3.getX()][enemyPawn3.getY()] =  3;
        this.grid[enemyPawn4.getX()][enemyPawn4.getY()] =  3;
    }

    public void setCurrentMove (String move){
        this.currentMove = move;
    }



    private void enemyMove (){

        for (int i = 0; i < this.enemyList.size(); i ++){
            double value = Math.random();
            System.out.println(value);

            //destra
            if (value < 0.25) {
                //Se va fuori
                if(this.enemyList.get(i).getY() + 1 >= this.ySize){
                    this.grid [this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] = this.lastEnemyPositionList.get(i);
                    this.lastEnemyPositionList.add(i,  this.grid [this.enemyList.get(i).getX()][0]);
                    this.enemyList.get(i).setY(0);
                }

                else if (this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY() +1] == 5){
                    this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] =  this.lastEnemy1Position;
                    this.enemyList.get(i).setY(this.enemyList.get(i).getY() + 1);
                    this.status = Status.LOSE;
                }
                else if (this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY() +1] == 2 || this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY() +1] == 0){
                    this.grid [this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] = this.lastEnemyPositionList.get(i);
                    this.lastEnemyPositionList.add(i,  this.grid [this.enemyList.get(i).getX()][this.enemyList.get(i).getY() + 1]);
                    this.enemyList.get(i).setY(this.enemyList.get(i).getY() + 1);
                }
                else if (this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY() +1] == 3 || this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY() +1] == 1){
                    continue;
                }
            }

            //sinistra
            else if (value < 0.50){
                //Se va fuori
                if(this.enemyList.get(i).getY() - 1 < 0){
                    this.grid [this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] = this.lastEnemyPositionList.get(i);
                    this.lastEnemyPositionList.add(i,  this.grid [this.enemyList.get(i).getX()][this.ySize - 1]);
                    this.enemyList.get(i).setY(this.ySize - 1);
                }
                else if (this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY() - 1] == 5){
                    this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] =  this.lastEnemy1Position;
                    this.enemyList.get(i).setY(this.enemyList.get(i).getY() - 1);
                    this.status = Status.LOSE;
                }
                else if (this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY() - 1] == 2 || this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY() - 1] == 0){
                    this.grid [this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] = this.lastEnemyPositionList.get(i);
                    this.lastEnemyPositionList.add(i,  this.grid [this.enemyList.get(i).getX()][this.enemyList.get(i).getY() - 1]);
                    this.enemyList.get(i).setY(this.enemyList.get(i).getY() - 1);
                }
                else if (this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY() - 1] == 3 || this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY() - 1] == 1){
                    continue;
                }
            }

            //sopra
            else if (value < 0.75){

                //se va fuori dal confine
                if(this.enemyList.get(i).getX() - 1 < 0){
                    this.grid [this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] = this.lastEnemyPositionList.get(i);
                    this.lastEnemyPositionList.add(i,  this.grid [this.xSize - 1][this.enemyList.get(i).getY()]);
                    this.enemyList.get(i).setX(this.xSize - 1);
                }

                else if (this.grid[this.enemyList.get(i).getX() - 1 ][this.enemyList.get(i).getY()] == 5){
                    this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] =  this.lastEnemy1Position;
                    this.enemyList.get(i).setX(this.enemyList.get(i).getX() - 1);
                    this.status = Status.LOSE;
                }
                else if (this.grid[this.enemyList.get(i).getX() - 1][this.enemyList.get(i).getY() ] == 2 || this.grid[this.enemyList.get(i).getX()- 1][this.enemyList.get(i).getY()] == 0){
                    this.grid [this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] = this.lastEnemyPositionList.get(i);
                    this.lastEnemyPositionList.add(i,  this.grid [this.enemyList.get(i).getX() - 1][this.enemyList.get(i).getY()]);
                    this.enemyList.get(i).setX(this.enemyList.get(i).getX() - 1);
                }
                else if (this.grid[this.enemyList.get(i).getX() - 1][this.enemyList.get(i).getY()] == 3 || this.grid[this.enemyList.get(i).getX() - 1][this.enemyList.get(i).getY()] == 1){
                    continue;
                }
            }


            //sotto
            else if (value <= 1){
                //se va fuori dal confine
                if(this.enemyList.get(i).getX() + 1 >= this.xSize){
                    this.grid [this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] = this.lastEnemyPositionList.get(i);
                    this.lastEnemyPositionList.add(i,  this.grid [0][this.enemyList.get(i).getY()]);
                    this.enemyList.get(i).setX(0);
                }

                else if (this.grid[this.enemyList.get(i).getX() + 1 ][this.enemyList.get(i).getY()] == 5){
                    this.grid[this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] =  this.lastEnemy1Position;
                    this.enemyList.get(i).setX(this.enemyList.get(i).getX() + 1);
                    this.status = Status.LOSE;
                }
                else if (this.grid[this.enemyList.get(i).getX() + 1][this.enemyList.get(i).getY() ] == 2 || this.grid[this.enemyList.get(i).getX() + 1][this.enemyList.get(i).getY()] == 0){
                    this.grid [this.enemyList.get(i).getX()][this.enemyList.get(i).getY()] = this.lastEnemyPositionList.get(i);
                    this.lastEnemyPositionList.add(i,  this.grid [this.enemyList.get(i).getX() + 1][this.enemyList.get(i).getY()]);
                    this.enemyList.get(i).setX(this.enemyList.get(i).getX() + 1);
                }
                else if (this.grid[this.enemyList.get(i).getX() + 1][this.enemyList.get(i).getY()] == 3 || this.grid[this.enemyList.get(i).getX() + 1][this.enemyList.get(i).getY()] == 1){
                    continue;
                }
            }
            this.setEnemyPosition();
        }
    }

    @Override
    public void run(){
        while ( status == Status.IN_GAME){
            try {
                Thread.sleep(3000);
                move();
                this.enemyMove();
                System.out.println(this.toString());
                System.out.println(this.getScore());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getScore (){
        return this.score;
    }

    public void checkFoodCounter (){
        int count= 0;
        for (int i = 0; i < this.grid.length; i++){
            for (int j = 0; j < this.grid[i].length; j ++){
                if (this.grid[i][j] == 0){
                    count ++;
                }
            }
        }
        this.foodCounter = count;
    }




    public void move (){
        if (this.currentMove.equalsIgnoreCase("d")){

            if (this.playerPawn.getY() + 1 >= this.ySize){
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setY(0);
            }
            //se incontra un nemico
            if (this.grid [playerPawn.getX()] [playerPawn.getY() + 1] == 3){
                this.status = Status.LOSE;
            }
            if (this.grid [playerPawn.getX()] [playerPawn.getY() + 1] != 1){
                if (this.grid [playerPawn.getX()] [playerPawn.getY() + 1] == 0){
                    this.foodCounter --;
                    this.score += 10;
                }
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setY(this.playerPawn.getY() + 1);
                this.setPlayerPosition();
            }
        }

        if (this.currentMove.equalsIgnoreCase("a")){
            if (this.playerPawn.getY() - 1 < 0){
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setY(this.ySize-1);
            }
            if (this.playerPawn.getY() - 1 < 0){
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setY(ySize - 1);
            }
            if (this.grid [playerPawn.getX()] [playerPawn.getY() - 1] == 3){
                this.status = Status.LOSE;
            }
            if (this.grid [playerPawn.getX()] [playerPawn.getY() - 1] != 1){
                if (this.grid [playerPawn.getX()] [playerPawn.getY() - 1] == 0){
                    this.foodCounter --;
                    this.score += 10;
                }
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setY(this.playerPawn.getY() - 1);
                this.setPlayerPosition();
            }
        }

        if (this.currentMove.equalsIgnoreCase("s")){
            if (this.playerPawn.getX() + 1 >= this.xSize){
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setX(0);
            }
            if (this.grid [playerPawn.getX() + 1] [playerPawn.getY()] == 3){
                this.status = Status.LOSE;
            }
            if (this.grid [playerPawn.getX() + 1] [playerPawn.getY()] != 1){
                if (this.grid [playerPawn.getX() + 1] [playerPawn.getY()] == 0){
                    this.foodCounter --;
                    this.score += 10;
                }
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setX(this.playerPawn.getX() + 1);
                this.setPlayerPosition();
            }
        }

        if (this.currentMove.equalsIgnoreCase("w")){
            if (this.playerPawn.getX() - 1 < 0){
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setX(this.xSize - 1);
            }
            if (this.grid [playerPawn.getX() - 1] [playerPawn.getY()] == 3){
                this.status = Status.LOSE;
            }
            if (this.grid [playerPawn.getX() - 1] [playerPawn.getY()] != 1){
                if (this.grid [playerPawn.getX() - 1] [playerPawn.getY()] == 0){
                    this.foodCounter --;
                    this.score += 10;
                }
                this.grid [playerPawn.getX()] [playerPawn.getY()] = 2;
                this.playerPawn.setX(this.playerPawn.getX() - 1);
                this.setPlayerPosition();
            }
        }
        if (this.isWin()){
            this.status = Status.WIN;
        }

    }

    public boolean isWin (){
        if (this.foodCounter == 0){
            return true;
        }
        return false;
    }

    public Status getStatus() {
        return status;
    }

    public void loadLevel(){
        String path = "livello";
        if (this.levelChoice == 1){
            this.grid = new int[27][28];
            this.xSize = 27;
            this.ySize = 28;
            path += "1.txt";
        }
        if (this.levelChoice == 2){
            this.grid = new int[17][18];
            this.xSize = 17;
            this.ySize = 18;
            path += "2.txt";
        }
        try {
            FileReader reader = new FileReader(path);
            int character;
            do {
                character = reader.read();
                for (int i = 0; i < this.grid.length ; i ++){
                    for (int j = 0; j < this.grid[i].length  ; j++) {
                        this.grid [i][j] = character - 48;
                        character = reader.read();
                    }
                }
            } while (character != -1);

        }  catch (Exception e) { }
    }

    public String toString (){
        String result= "";
        for (int i = 0; i < this.grid.length; i ++){
            result += "|";
            for (int j = 0; j <this.grid[i].length; j ++){
                if (this.grid [i][j] == 1){
                    result += "[" + "\u001B[33m\uD83D\uDEE2\u001B[0m" + "]";
                } else if(this.grid [i][j] == 0) {
                    result += "[" + "\033[0;34m\uD83E\uDDC0\u001B[0m" + "]";
                }else if (this.grid [i][j] == 2){
                    result += "[" + "\033[0;30m\uD83D\uDC3E\u001B[0m" + "]";
                }else if (this.grid [i][j] == 3 ){
                    result += "[" + "\u001B[46m\uD83D\uDE3C\u001B[0m" + "]";
                }else if (this.grid [i][j] == 5 ){
                    result += "[" + "\u001B[31m\uD83D\uDC2D\u001B[0m" + "]";
                } else {
                    result += "[" + "\uD83D\uDE94" + "]";
                }
            }
            result += "\n";
        }

        return result;

    }

}