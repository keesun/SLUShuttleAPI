package me.whiteship.shuttle;

/**
 * @author Keesun Baik
 */
public class NotFoundStationException extends RuntimeException {

    private String stationName;

    public NotFoundStationException(String stationName) {
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }
}
