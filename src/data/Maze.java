package data;

import java.util.List;

/**
 * Created by Bimal Paul on 5/25/2016.
 */
public class Maze {
    public String[] dimensions;
    public List<Mirror> mirrors;
    public LightPath entryPoint;
    public LightPath exitPoint;


    public String[] getDimensions() {
        return dimensions;
    }

    public void setDimensions(String[] dimensions) {
        this.dimensions = dimensions;
    }

    public List<Mirror> getMirrors() {
        return mirrors;
    }

    public void setMirrors(List<Mirror> mirrors) {
        this.mirrors = mirrors;
    }

    public LightPath getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(LightPath entryPoint) {
        this.entryPoint = entryPoint;
    }

    public LightPath getExitPoint() {
        return exitPoint;
    }

    public void setExitPoint(LightPath exitPoint) {
        this.exitPoint = exitPoint;
    }
}
