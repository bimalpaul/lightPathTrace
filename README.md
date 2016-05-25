# How to run? 
* Import Project into IntelliJ/ IDEA as JAVA project
* Right click `src/LightExit.java` - Hit Run - the console should show the results

# Main 
* src/LighExit.java`
* Comments added in class

# Data structures

### 1. `src/data/LightPath.java`
This class contains the structure for the path of light.
* `coordinates` has the (x,y) coordinates of the square through which light enters/ exits
* `laserOrientation` has the orinetation (H,V) in which the laser beam enters or exits the maze


### 2. `src/data/Mirrors.java`
This class contains the structure for mirrors
* `coordinates` has the (x,y) coordinates of the sqaure in which the mirror is placed
* `mirrorDirection` has the direction (L,R) in which the mirror is placed
* `areBothSidesReflective` is a boolean that specifies whether the mirror is reflective on both sides
* `reflectiveSide` - if both sides are not reflective, this will contain the (L,R) refelctive side of the mirror


### 3. `src/data/Maze.java`
This class contains the maze datastructure
* `dimensions` has the (x,y) dimension of the maze. x is the number of columns, y is the number of rows
* `mirrors` is a collection of `Mirrors` that are placed in the squares belonging in the maze
* `entryPoint` is the `LightPath` for the laser beam that enters the maze
* `exitPoint` is the `LightPath` for the laser beam that exits the maze


