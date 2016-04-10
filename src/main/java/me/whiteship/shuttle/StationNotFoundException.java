package me.whiteship.shuttle;

/**
 * @author Keesun Baik
 */
public class StationNotFoundException extends RuntimeException {

    private String stationName;

    @Override
    public String getMessage() {
        return "Not found '" + this.stationName + "' station. Please check if the name of station is correct.";
    }

    public StationNotFoundException(String stationName) {
        this.stationName = stationName;
    }

}
