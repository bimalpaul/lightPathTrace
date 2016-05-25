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

        String fileName = "C:\\bench\\git\\bimalpaul\\mirrorMaze\\exampleFiles\\file1.txt";
        if (fileName.length() == 0) {
            throw new RuntimeException("Need a file name!");
        }
        Scanner fileScanner = setup(fileName);
        recognizeInputPattern(fileScanner);
    }

    private static Scanner setup(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        return (new Scanner(fileReader));
    }

    private static Maze recognizeInputPattern(Scanner scanner) {
        Maze maze = new Maze();
        List<Mirror> mirrors = new ArrayList<>();
        LightPath entryPoint = new LightPath();
        int inputIndex = 0;
        Boolean invalidFile = true;
        String[] dimensionArray = new String[2];
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("-1")) {
                inputIndex++;
                if (scanner.hasNext()) {
                    line = scanner.nextLine();
                }
            }

            if (inputIndex == 0) {
                dimensionArray = line.split(",");
                if (dimensionArray.length != 2) {
                    throw new RuntimeException("Dimension array not specified properly. Please change file contents. It should be of format row count, column count");
                }
            }

            if (inputIndex == 1) {
                Boolean bothSidesReflective = false;
                String reflectiveSide = "";
                String mirrorDirection = "";
                invalidFile = checkIfValidSquarePosition(line, dimensionArray);
                if (invalidFile) {
                    throw new RuntimeException("Invalid input. Check mirror placement coordinates");
                }

                Mirror mirror = new Mirror();

                String mirrorWithoutDirection = line.replaceAll("[^\\d,]", "");
                String[] mirrorPosition = mirrorWithoutDirection.split(",");
                mirror.setCoordinates(mirrorPosition);

                String mirrorDirectionAndOrientation = line.replaceAll("[^\\D],", "");
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
                mirrors.add(mirror);
            }


            if (inputIndex == 2) {
                invalidFile = checkIfValidSquarePosition(line, dimensionArray);
                if (invalidFile) {
                    throw new RuntimeException("Invalid input. Check light entry coordinates");
                }


                String entryPointWithoutOrientation = line.replaceAll("[^\\d,]", "");
                String[] lightEntryPoint = entryPointWithoutOrientation.split(",");
                entryPoint.setCoordinates(lightEntryPoint);

                String laserOrientation = line.replaceAll("[^\\D],", "");
                String[] laserArray = laserOrientation.split("");
                if(laserArray.length < 2) {
                    throw new RuntimeException("Laser entry orientation not mentioned correctly. Please edit input file.");
                }
                 entryPoint.setLaserOrientation(laserArray[1]);


            }

        }
        maze.setDimensions(dimensionArray);
        maze.setMirrors(mirrors);
        maze.setEntryPoint(entryPoint);

        System.out.println("Maze dimensions: (" + maze.getDimensions()[0] + "," + maze.getDimensions()[1] + ")");

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

        return null;
    }

    private static boolean checkIfValidSquarePosition(String line, String[] dimensionArray) {
        line = line.replaceAll("[^\\d,]", "");
        String[] dimArray = line.split(",");
        Boolean rowCheck = Integer.parseInt(dimArray[0]) > Integer.parseInt(dimensionArray[0]);
        Boolean colCheck = Integer.parseInt(dimArray[1]) > Integer.parseInt(dimensionArray[1]);
        return rowCheck || colCheck;
    }
}


