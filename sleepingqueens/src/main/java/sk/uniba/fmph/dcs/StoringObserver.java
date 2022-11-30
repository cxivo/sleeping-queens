package sk.uniba.fmph.dcs;

/**
 * A simple observer, which stores the received message in a String accessable at any time
 */
public class StoringObserver implements GameObserver {
    private String message;

    public String getMessage() {
        return message;
    }

    @Override
    public void notify(String message) {
        this.message = message;
    }
    
}
