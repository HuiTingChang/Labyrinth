package de.fhac.mazenet.server;

/**
 * Created by bongen on 13.03.17.
 */
public class PathInfo {
    private int stepsFromSource = -1;
    private Position cameFrom;

    public PathInfo(int stepsFromSource, Position cameFrom) {
        this.stepsFromSource = stepsFromSource;
        this.cameFrom = cameFrom;
    }

    public PathInfo() {
        this.stepsFromSource = -1;
        this.cameFrom = null;
    }

    public void setStepsFromSource(int stepsFromSource) {
        this.stepsFromSource = stepsFromSource;
    }

    public void setCameFrom(Position cameFrom) {
        this.cameFrom = cameFrom;
    }

    public int getStepsFromSource() {
        return stepsFromSource;
    }

    public Position getCameFrom() {
        return cameFrom;
    }
}
