package me.whiteship.shuttle;

/**
 * @author Keesun Baik
 */
public class NotFoundStationException extends RuntimeException {

    private String stationName;

    @Override
    public String getMessage() {
        return "Not found '" + this.stationName + "' station. Please check if the name of station is correct.";
    }

    public NotFoundStationException(String stationName) {
        this.stationName = stationName;
    }

}
