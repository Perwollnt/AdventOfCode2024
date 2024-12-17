package me.perwollnt.d4;

import me.perwollnt.helpers.Solver;

import java.util.List;

public class DayFourSolver extends Solver {
    @Override
    public void solve1() {
        List<List<String>> input = DayFourReader.read();
        // ok now, i got a 2d list, the next thing i need to do is find stuff.

        // This word search allows words to be
        // horizontal,
        // vertical,
        // diagonal,
        // written backwards,
        // or even overlapping other words.
        // It's a little unusual, though, as you don't merely need to find one instance of XMAS - you need to find all of them.
        // Here are a few ways XMAS might appear, where irrelevant characters have been replaced with .:

        //. . X . . .
        //. S A M X .
        //. A . . A .
        //X M A S . S
        //. X . . . .

        // loop the list, check each word if ifs x, if yes check the surrounding words for m, if yes check the SAME DIRECTION for a and s.

        int foundNum = 0;

        for (int i = 0; i < input.size(); i++) {
            List<String> row = input.get(i);

            for (int j = 0; j < row.size(); j++) {
                if (row.get(j).equals("X")) {
                    foundNum += countXMAS(input, i, j);
                }
            }
        }

        System.out.println("Found " + foundNum + " XMAS");
    }

    private int countXMAS(List<List<String>> grid, int i, int j) {
        int foundCount = 0;

        int[][] directions = getDirections();

        for (int[] dir : directions) {
            if (isXMAS(grid, i, j, dir) != null) {
                foundCount++;
            }
        }

        return foundCount;
    }

    private int[][] isXMAS(List<List<String>> grid, int i, int j, int[] dir) {
        int[][] returnValue = new int[4][2];
        returnValue[0] = new int[]{i, j};

        int ni = i + dir[0], nj = j + dir[1];
        if (
                isOutOfBounds(ni, grid.size(), nj, grid.getFirst().size())
                        || !grid.get(ni).get(nj).equals("M")
        ) return null;
        returnValue[1] = new int[]{ni, nj};

        ni += dir[0];
        nj += dir[1];
        if (
                isOutOfBounds(ni, grid.size(), nj, grid.getFirst().size())
                        || !grid.get(ni).get(nj).equals("A")
        ) return null;
        returnValue[2] = new int[]{ni, nj};

        ni += dir[0];
        nj += dir[1];
        if (
                isOutOfBounds(ni, grid.size(), nj, grid.getFirst().size())
                        || !grid.get(ni).get(nj).equals("S")
        ) return null;
        returnValue[3] = new int[]{ni, nj};

        return returnValue;
    }

    private int[][] getDirections() {
        return new int[][]{
                {-1, -1}, {-1, 0}, {-1, 1}, // Top-left, Top, Top-right
                {0, -1}, {0, 1}, // Left,       Right
                {1, -1}, {1, 0}, {1, 1}  // Bottom-left, Bottom, Bottom-right
        };
    }

    @Override
    public void solve2() {
        List<List<String>> input = DayFourReader.read();
        int foundNum = 0;

        // Iterate through the grid to find X-MAS patterns
        for (int i = 1; i < input.size() - 1; i++) { // Avoid grid edges
            for (int j = 1; j < input.get(i).size() - 1; j++) { // Avoid grid edges
                if (isXMASCenter(input, i, j)) {
                    foundNum++;
                }
            }
        }

        System.out.println("Found " + foundNum + " X-MAS centered patterns");
    }

    /**
     * Checks if the current cell is the center of a valid X-MAS pattern.
     */
    private boolean isXMASCenter(List<List<String>> grid, int i, int j) {
        // Center point must be "A"
        if (!grid.get(i).get(j).equals("A")) return false;
        System.out.println("Center point is A");


        // Directions for diagonals
        int[][] diagonals = {
                {-1, -1, 1, 1}, // Top-left to bottom-right
                {-1, 1, 1, -1}  // Top-right to bottom-left
        };

        // Check both diagonals for valid MAS patterns
        boolean isValid = true;
        for (int[] diag : diagonals) {
            int x1 = i + diag[0], y1 = j + diag[1];
            int x2 = i + diag[2], y2 = j + diag[3];

            System.out.println("Checking diagonal: " + x1 + "," + y1 + " -> " + x2 + "," + y2);

            // Fix 1: Ensure bounds checking for both ends of the diagonal
            if (isOutOfBounds(x1, grid.size(), y1, grid.getFirst().size())
                    || isOutOfBounds(x2, grid.size(), y2, grid.getFirst().size())) {
                System.out.println("Out of bounds");
                continue;
            }

            char c1 = grid.get(x1).get(y1).charAt(0);
            char c2 = grid.get(x2).get(y2).charAt(0);
            System.out.println("Chars: " + c1 + "," + c2);

            // Fix 2: Simplified validation using isValidMAS method
            if (!isValidMAS(c1, c2)) {
                isValid = false;
            }
        }

        return isValid;
    }

    private boolean isValidMAS(char c1, char c2) {
        // Fix 3: Added support for both forward and backward MAS patterns
        return (c1 == 'M' && c2 == 'S') || (c1 == 'S' && c2 == 'M');
    }

    private boolean isOutOfBounds(int i, int ilen, int j, int jlen) {
        return i < 0 || i >= ilen || j < 0 || j >= jlen;
    }


    @Override
    public int getDay() {
        return 4;
    }

    @Override
    public String print() {
        return "Day 4";
    }
}