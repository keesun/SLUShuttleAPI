package me.whiteship.shuttle;

/**
 * @author Keesun Baik
 */
public class ShuttleNotFoundException extends RuntimeException {

    private int number;

    public ShuttleNotFoundException(int number) {
        this.number = number;
    }

    @Override
    public String getMessage() {
        return "Shuttle that has " + this.number + " not found";
    }
}
