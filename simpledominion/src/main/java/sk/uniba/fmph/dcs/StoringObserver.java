package sk.uniba.fmph.dcs;

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
