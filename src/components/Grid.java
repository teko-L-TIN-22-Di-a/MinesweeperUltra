package src.components;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.Clip;

import src.assets.Loader;
import src.assets.SoundMapping;
import src.core.StaticValues;

/**
 * The Grid Class is intended to set the structure for the Fields. It provides
 * functionalities to create Fields and place them in a Matrix and to keep
 * references to the surrounding Fields.  
 * First a value matrix is generated. Then Mines will be set per random inside the Grid.
 * After that, the values around the mines are calculated.
 * At the end, for every value in the value matrix, a Field will be added to the fieldMatrix.
 * @see Field
 */
public class Grid {

    private int[][] grid;
    private Random randy = new Random();
    private Point size;
    private List<List<Field>> fieldMatrix;

    /**
     * Takes width, height and a mine count to create a Grid Object.
     * Creates a value and Field matrix, sets mines and values, creates Fields
     * and creates references to the surrounding Fields.
     * @param width width of the Grid
     * @param height height of the Grid
     * @param mineCount amount of mines in the Grid
     */
    public Grid(int width, int height, int mineCount) {
        grid = new int[width][height];
        size = new Point(width, height);
        fieldMatrix = new ArrayList<>();

        int i;
        for (i=0; i<height; i++) {
            List<Field> line = new ArrayList<>();
            fieldMatrix.add(line);
        }
        setMines(mineCount);
        setMineCounts();
        createFields();
        setAdjacentFields();
        print();
    }

    /**
     * Returns a value from the value matrix.
     * @param x x location in the value matrix
     * @param y y location in the value matrix
     * @return value of position xy in the value matrix
     */
    public int getValue(int x, int y) {
        return grid[y][x];
    }

    /**
     * Returns a Point Object representing width and height of the
     * value matrix as x and y.
     * @return size of the value matrix as x and y
     */
    public Point getSize() {
        return size;
    }

    /**
     * Takes an amount of mines and creates random coordinates
     * inside the value matrix to set to 9 (indicator for mine).
     * Before setting the value, the coordinate is checked, if its value
     * is already 9. If so, another coordinate is generated.
     * @param mineCount
     */
    private void setMines(int mineCount) {
        int i;
        for (i=0; i<mineCount; i++) {
            boolean valid = false;
            while (!valid) {
                int x = randy.nextInt(grid[0].length);
                int y = randy.nextInt(grid.length);
                if (grid[y][x] != 9) {
                    grid[y][x] = 9;
                    valid = true;
                }
            }
        }
    }

    /**
     * Iterates through all the fields in the value matrix, gets the count
     * of 9 (mine) around the field and sets the value of the field to that count.
     */
    private void setMineCounts() {
        for (int y=0; y<grid.length; y++) {
            for (int x=0; x<grid[y].length; x++) {
                if (grid[y][x]!=9) {
                    grid[y][x] = getMineCount(x, y);
                }
            }
        }
    }

    /**
     * Takes x and y coordinates and checks the value matrix for all fields in range
     * x-1 to x+1 and y-1 to y+1 for mines. The field xy will not be counted.
     * Returns the count of mines in all adjacent fields.
     * @param x x coordinate in the value matrix
     * @param y y coordinate in the value matrix
     * @return amount of mines in the adjacent fields
     */
    private int getMineCount(int x,int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Skip the cell itself
                if (i == 0 && j == 0) continue;
                
                int newX = x + i;
                int newY = y + j;
                
                // Check if the new coordinates are within the grid boundaries
                if (newX >= 0 && newX < grid[0].length && newY >= 0 && newY < grid.length) {
                    if (grid[newY][newX] == 9) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Creates Field Objects for every field in value matrix. Adds every new field to the Field matrix
     */
    private void createFields() {
        Clip explosion = Loader.loadSound(SoundMapping.EXPLOSION);
        Clip reveil = Loader.loadSound(SoundMapping.REVEIL);
        for (int i = 0; i<size.y; i++) {
            for (int j = 0; j<size.x; j++) {
                int fieldSize = StaticValues.FIELDSIZE;

                int centerDeltaX = (StaticValues.CANVAS_WIDTH - size.x*(fieldSize+2))/2;
                int centerDeltaY = (StaticValues.CANVAS_HEIGHT - size.y*(fieldSize+2))/2;

                Field newField = new Field(fieldSize, fieldSize, centerDeltaX+i*(fieldSize+2), centerDeltaY+j*(fieldSize+2));
                int fieldValue = getValue(i, j);
                newField.setValue(fieldValue);

                if (fieldValue == 9) {
                    newField.setSound(explosion);
                }
                else {
                    newField.setSound(reveil);
                }

                newField.setMatrixLocation(j, i);
                fieldMatrix.get(i).add(newField);
            }
        }
    }

    /**
     * Iterates through all the Fields and adds a reference to all adjacent
     * Fields to the current Field, if the value of the current Field is 0.
     */
    private void setAdjacentFields() {
        ///fieldMatrix.stream().map(height -> {height.})
        for (List<Field> fields: fieldMatrix) {
            for (Field f: fields) {
                if (f.getValue()==0) {
                    Point ml = f.getMatrixLocation();
                    List<Field> af = getAdjacentFields(ml.x, ml.y);
                    f.setAdjacentFields(af);
                }
            }
        }
    }

    /**
     * Takes x and y coordinates to get all adjacent Fields of Field xy
     * regarding the borders of the Grid.  
     * Returns all adjacent Fields as a List.
     * @param x location x in the Field matrix
     * @param y location y in the Field matrix
     * @return List of all adjacent Fields.
     */
    private List<Field> getAdjacentFields(int x, int y) {
        List<Field> fields = new ArrayList<>();
        int i;
        for (i=-1; i<=1; i++) {
            int j;
            for (j=-1; j<=1; j++) {
                if (i==0 && j==0) {
                    continue;
                }
                if (
                    x+i>=0 &&
                    x+i<size.x &&
                    y+j>=0 &&
                    y+j<size.y
                ) {
                    Field field = getField(x+i, y+j);
                    fields.add(field);
                }
            }
        }
        return fields;
    }

    /**
     * Takes x and y coordinates to get the Field at location xy of the field matrix.
     * @param x location x in the Field matrix
     * @param y location y in the Field matrix
     * @return Field at location xy of the field matrix 
     */
    private Field getField(int x, int y) {
        return fieldMatrix.get(y).get(x);
    }

    /**
     * Iterates through the Field matrix and returns
     * a List containing all Fields of the Grid.
     * @return a List of all Fields in the Grid
     */
    public List<Field> getAllFields() {
        List<Field> allFields = new ArrayList<>();
        for (List<Field> fl: fieldMatrix) {
            for (Field f: fl) {
                allFields.add(f);
            }
        }
        return allFields;
    }

    /**
     * Gets all the Fields in the Grid and performs the reveil
     * method for all of them.
     */
    public void reveilAll() {
        for (Field f: getAllFields()) {
            f.reveil();
        }
    }

    /**
     * Prints out the Grids value matrix to the console.
     */
    public void print() {
        for (int[] row: grid) {
            String out = "";
            for (int field: row) {
                out += field + " ";
            }
            System.out.println(out);
        }
    }
}
