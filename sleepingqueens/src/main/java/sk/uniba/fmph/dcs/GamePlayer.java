package sk.uniba.fmph.dcs;

import java.util.Scanner;

/**
 * This class enables actually playing the game
 * All my work comes to fruition here
 */
public class GamePlayer {
    public static void main(String[] args) {
        // if more players are desired, just add them to this list
        String[] names = {"Jožko", "Ferko"};

        // Game gets created
        GameAdaptor adaptor = new GameAdaptor(names);
        StoringObserver[] observers = new StoringObserver[names.length];

        // Observers get initialized
        for (int i = 0; i < names.length; i++) {
            observers[i] = new StoringObserver();
            adaptor.getGameObservable().addPlayer(i, observers[i]);
        }

        adaptor.notifyPlayers();

        Scanner in = new Scanner(System.in);

        // print the beginning state of the game
        System.out.println(observers[adaptor.getOnTurn()].getMessage());

        // repeats indefinitely
        while (true) {
            System.out.println(adaptor.play(names[adaptor.getOnTurn()], in.nextLine()));
        }
    }
}
