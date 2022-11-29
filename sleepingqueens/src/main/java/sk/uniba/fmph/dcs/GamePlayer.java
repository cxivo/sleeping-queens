package sk.uniba.fmph.dcs;

import java.util.Scanner;

public class GamePlayer {
    public static void main(String[] args) {
        String[] names = {"Jožko", "Ferko"};
        GameAdaptor adaptor = new GameAdaptor(names);
        StoringObserver[] observers = new StoringObserver[names.length];

        for (int i = 0; i < names.length; i++) {
            observers[i] = new StoringObserver();
            adaptor.getGameObservable().addPlayer(i, observers[i]);
        }

        adaptor.notifyPlayers();

        Scanner in = new Scanner(System.in);

        System.out.println(observers[adaptor.getOnTurn()].getMessage());

        while (true) {
            System.out.println(adaptor.play(names[adaptor.getOnTurn()], in.nextLine()));
        }
    }
}
