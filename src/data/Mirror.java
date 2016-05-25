package data;

/**
 * Created by Bimal Paul on 5/25/2016.
 */
public class Mirror {
    public String[] coordinates;
    public String mirrorDirection;
    public boolean areBothSidesReflective;
    public String reflectiveSide;

    public String[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getMirrorDirection() {
        return mirrorDirection;
    }

    public void setMirrorDirection(String mirrorDirection) {
        this.mirrorDirection = mirrorDirection;
    }

    public boolean isAreBothSidesReflective() {
        return areBothSidesReflective;
    }

    public void setAreBothSidesReflective(boolean areBothSidesReflective) {
        this.areBothSidesReflective = areBothSidesReflective;
    }

    public String getReflectiveSide() {
        return reflectiveSide;
    }

    public void setReflectiveSide(String reflectiveSide) {
        this.reflectiveSide = reflectiveSide;
    }
}
