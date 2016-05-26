/**
 * Created by Bimal Paul on 5/24/2016.
 */

import data.Maze;
import data.Mirror;
import data.LightPath;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LightExit {
    public static void main(String[] args) throws IOException {

        String fileName = "C:\\bench\\git\\bimalpaul\\lightPathTrace\\exampleFiles\\file1.txt";  // This is the path of the file to be test. \ escaped
        if (fileName.length() == 0) {
            throw new RuntimeException("Need a file name!");
        }
        Scanner fileScanner = setup(fileName);
        Maze maze = recognizeInputPattern(fileScanner);
        showMaze(maze);
    }

    private static Scanner setup(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);  // initialize fileReader with filePath
        return (new Scanner(fileReader)); // initialize fileScanner with fileReader
    }

    private static Maze recognizeInputPattern(Scanner scanner) {
        Maze maze = new Maze();
        List<Mirror> mirrors = new ArrayList<>();
        LightPath entryPoint = new LightPath();
        int inputIndex = 0;
        Boolean invalidFile = true; // bool to check for validity of file
        String[] dimensionArray = new String[2];
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("-1")) {
                inputIndex++; // number of inputs is calculated by number of -1s
                if (scanner.hasNext()) {
                    line = scanner.nextLine();
                }
            }

            if (inputIndex == 0) {
                dimensionArray = line.split(",");  // split first line to get dimensions
                if (dimensionArray.length != 2) {  // length should always be 2
                    throw new RuntimeException("Dimension array not specified properly. Please change file contents. It should be of format row count, column count");
                }
            }

            if (inputIndex == 1) {
                invalidFile = checkIfValidSquarePosition(line, dimensionArray);
                if (invalidFile) {
                    throw new RuntimeException("Invalid input. Check mirror placement coordinates");
                }
                Mirror mirror = getMirror(line);
                mirrors.add(mirror);
            }


            if (inputIndex == 2) {
                invalidFile = checkIfValidSquarePosition(line, dimensionArray);
                if (invalidFile) {
                    throw new RuntimeException("Invalid input. Check light entry coordinates");
                }
                entryPoint = getEntryPoint(line);
            }

        }
        maze.setDimensions(dimensionArray);
        maze.setMirrors(mirrors);
        maze.setEntryPoint(entryPoint);
        LightPath exitPoint = tracePath(dimensionArray, mirrors, entryPoint);

        return maze;
    }

    private static LightPath tracePath(String[] dimensionArray, List<Mirror> mirrors, LightPath entryPoint) {
        String laserOrientation = entryPoint.getLaserOrientation();
        int xCord = Integer.parseInt(entryPoint.getCoordinates()[0]);
        int yCord = Integer.parseInt(entryPoint.getCoordinates()[1]);

        int mazeXCord = Integer.parseInt(dimensionArray[0]) - 1; //coordinates start with 0
        int mazeYCord = Integer.parseInt(dimensionArray[1]) - 1;

        int xExit = xCord;
        int yExit = yCord;

        switch (laserOrientation) {
            case "V": {
                yExit = yCord;
                // if laser enters vertically - keep increasing y coordinates till (x,) matches one of the mirrors (OR) x or y greater than dimensions
                for (Mirror mirror : mirrors) {
                    do {
                        if (yExit == mirror.getCoordinates()[1]) {
                            // reflect here
                            // get mirror's reflective side
                            String reflectiveSide = "";
                            String mirrorDirection = mirror.getMirrorDirection();
                            if (!mirror.isAreBothSidesReflective()) {
                                reflectiveSide = mirror.getReflectiveSide();
                            } else {
                                reflectiveSide = "B";
                            }
                            if (mirrorDirection.equalsIgnoreCase("R")) {
                                if (reflectiveSide.equalsIgnoreCase("L")) {
                                    // pass through
                                    yExit++;

                                } else if (reflectiveSide.equalsIgnoreCase("R") || reflectiveSide.equalsIgnoreCase("B")) {
                                    xExit++;
                                }
                            } else {
                                if (reflectiveSide.equalsIgnoreCase("R")) {
                                    // pass through
                                    yExit++;

                                } else if (reflectiveSide.equalsIgnoreCase("L") || reflectiveSide.equalsIgnoreCase("B")) {
                                    xExit--;
                                }
                            }


                        } else if (yExit < mirror.getCoordinates()[1]) {
                            yExit++;
                        } else {
                            // ***should not happen***
                        }
                    } while (yExit <= mazeYCord && xExit <= mazeXCord && yExit >= 0 && xExit >= 0);
                }
                break;
            }
            case "H": {
                break;
            }
        }
        int xExitCord = 0 ;
        int yExitCord = 0 ;
        if(xExit > 0) {
            xExitCord = xExit > mazeXCord ? (xExit - 1) : xExit;
        }
        if(yExit > 0) {
            yExitCord = yExit > mazeYCord ? (yExit - 1) : yExit;
        }
        System.out.println("xexit : " + xExitCord);
        System.out.println("yexit : " + yExitCord);

        return null;
    }

    private static void showMaze(Maze maze) {
        System.out.println("Maze dimensions: " + maze.getDimensions()[0] + "X" + maze.getDimensions()[1]);

        for (int i = 0; i < maze.getMirrors().size(); i++) {
            Mirror mirrorToDisplay = maze.getMirrors().get(i);
            System.out.println();
            System.out.println("Mirror #" + (i + 1));
            System.out.println("Position: (" + mirrorToDisplay.getCoordinates()[0] + "," + mirrorToDisplay.getCoordinates()[1] + ")");
            System.out.println("Mirror direction: " + mirrorToDisplay.getMirrorDirection());
            String direction = mirrorToDisplay.isAreBothSidesReflective() ? "both" : mirrorToDisplay.getReflectiveSide();
            System.out.println("Reflective Side(s): " + direction);
        }

        System.out.println("\nLazer entry point: (" + maze.getEntryPoint().getCoordinates()[0] + "," + maze.getEntryPoint().getCoordinates()[1] + ")");
        System.out.println("Lazer entry orientation: " + maze.getEntryPoint().getLaserOrientation());
    }

    private static LightPath getEntryPoint(String line) {
        LightPath entryPoint = new LightPath();
        String entryPointWithoutOrientation = line.replaceAll("[^\\d,]", "");
        String[] lightEntryPoint = entryPointWithoutOrientation.split(",");
        entryPoint.setCoordinates(lightEntryPoint);

        String laserOrientation = line.replaceAll("[^\\D],", "");
        String[] laserArray = laserOrientation.split("");
        if (laserArray.length < 2) {
            throw new RuntimeException("Laser entry orientation not mentioned correctly. Please edit input file.");
        }
        entryPoint.setLaserOrientation(laserArray[1]);
        return entryPoint;
    }

    private static Mirror getMirror(String line) {
        Boolean bothSidesReflective = false;
        String reflectiveSide = "";
        String mirrorDirection = "";
        Mirror mirror = new Mirror();

        String mirrorWithoutDirection = line.replaceAll("[^\\d,]", "");  // regex to remove all characters other than digits and ,
        String[] mirrorPosString = mirrorWithoutDirection.split(",");
        int mirrorX = Integer.parseInt(mirrorPosString[0]);
        int mirrorY = Integer.parseInt(mirrorPosString[1]);
        int[] mirrorPosition = new int[2];
        mirrorPosition[0] = mirrorX;
        mirrorPosition[1] = mirrorY;

        mirror.setCoordinates(mirrorPosition);

        String mirrorDirectionAndOrientation = line.replaceAll("[^\\D],", ""); // regex to remove all number and ,
        String[] mirrorDirectionAndOrientationArray = mirrorDirectionAndOrientation.split("");


        if (mirrorDirectionAndOrientationArray.length > 1) {
            mirrorDirection = mirrorDirectionAndOrientationArray[1];
            if (mirrorDirectionAndOrientationArray.length > 2) {
                reflectiveSide = mirrorDirectionAndOrientationArray[2];
            } else {
                bothSidesReflective = true;
            }
        } else {
            throw new RuntimeException("The direction in which mirror is placed is not specified. Please edit input file.");
        }

        mirror.setMirrorDirection(mirrorDirection);
        mirror.setAreBothSidesReflective(bothSidesReflective);
        if (!bothSidesReflective) {
            mirror.setReflectiveSide(reflectiveSide);
        }
        return mirror;
    }

    private static boolean checkIfValidSquarePosition(String line, String[] dimensionArray) {
        line = line.replaceAll("[^\\d,]", "");
        String[] dimArray = line.split(",");
        Boolean rowCheck = Integer.parseInt(dimArray[0]) > (Integer.parseInt(dimensionArray[0]) - 1);
        Boolean colCheck = Integer.parseInt(dimArray[1]) > (Integer.parseInt(dimensionArray[1]) - 1);
        return rowCheck || colCheck;
    }
}


