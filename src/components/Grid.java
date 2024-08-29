package src.components;

import java.awt.Point;
import java.util.Random;

public class Grid {

    private int[][] grid;
    private Random randy = new Random();
    private Point size;

    public Grid(int width, int height, int mineCount) {
        grid = new int[width][height];
        size = new Point(width, height);
        setMines(mineCount);
        setMineCounts();
        print();
    }

    public int getField(int x, int y) {
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
