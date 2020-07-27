import java.util.Scanner;

public class Main {
    public static void main (String args[]){
        Pacman p = new Pacman(2);
        System.out.println(p.toString());

        p.start();
        while (p.getStatus() == Pacman.Status.IN_GAME){
            Scanner in = new Scanner(System.in);
            String direction = in.nextLine();
            p.setCurrentMove(direction);

        }

        if (p.getStatus() == Pacman.Status.LOSE){
            System.out.println("SEI STATO BECCATO!");
        }
        else {
            System.out.println("HAI VINTO");
        }

    }
}