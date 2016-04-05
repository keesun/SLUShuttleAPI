package me.whiteship.shuttle;

/**
 * @author Keesun Baik
 */
public class ShuttleNotFoundException extends RuntimeException {

    private int number;

    public ShuttleNotFoundException(int number) {
        this.number = number;
    }
}
