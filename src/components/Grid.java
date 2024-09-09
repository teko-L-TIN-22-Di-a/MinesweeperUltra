package src.components;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import src.core.StaticValues;

public class Grid {

    private int[][] grid;
    private Random randy = new Random();
    private Point size;
    private List<List<Field>> fieldMatrix;

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

    public int getValue(int x, int y) {
        return grid[y][x];
    }

    public Point getSize() {
        return size;
    }

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
                    System.out.println("new mine at " + x + " " + y);
                }
            }
        }
    }

    private void setMineCounts() {
        for (int y=0; y<grid.length; y++) {
            for (int x=0; x<grid[y].length; x++) {
                if (grid[y][x]!=9) {
                    grid[y][x] = getMineCount(x, y);
                }
            }
        }
    }

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

    private void createFields() {
        for (int i = 0; i<size.y; i++) {
            for (int j = 0; j<size.x; j++) {
                int fieldSize = StaticValues.FIELDSIZE;
                Field newField = new Field(fieldSize, fieldSize, i*fieldSize+300, j*fieldSize+25);
                int fieldValue = getValue(i, j);
                newField.setValue(fieldValue);
                newField.setMatrixLocation(j, i);
                if (fieldValue == 9) {
                    newField.setMine();
                }
                fieldMatrix.get(i).add(newField);
            }
        }
    }

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

    private Field getField(int x, int y) {
        return fieldMatrix.get(y).get(x);
    }

    public List<Field> getAllFields() {
        List<Field> allFields = new ArrayList<>();
        for (List<Field> fl: fieldMatrix) {
            for (Field f: fl) {
                allFields.add(f);
            }
        }
        return allFields;
    }

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
