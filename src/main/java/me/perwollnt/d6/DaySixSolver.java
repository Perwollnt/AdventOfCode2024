package me.perwollnt.d6;

import me.perwollnt.helpers.Solver;
import me.perwollnt.helpers.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 6 Solver: Predict the guard's patrol path.
 * The guard moves based on these rules:
 * 1. Moves forward if the next cell is valid and empty.
 * 2. Turns right if blocked or out of bounds.
 * 3. Stops when it exits the grid.
 *
 * Calculates the total number of distinct positions visited by the guard.
 */
public class DaySixSolver extends Solver {

    private List<List<String>> board = new ArrayList<>();
    private static final String GUARD = "^";
    // Movement directions: North, East, South, West
    private static final int[][] DIRECTIONS = {
            {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };

    // 4789

    @Override
    public void solve1() {
        board = DaySixReader.read(); // Load the grid
        Tuple<Integer, Integer> position = findGuard();
        if (position == null) throw new RuntimeException("Guard not found on the board.");

        Set<String> visited = new HashSet<>();
        visited.add(positionToString(position));

        int dirIndex = 0; // Start facing Up

        while (true) {
            int[] direction = DIRECTIONS[dirIndex];
            Tuple<Integer, Integer> nextPos = move(position, direction);

            if (!isInsideBoard(nextPos)) break; // Stop when guard exits the grid

            if (canMove(nextPos)) {
                updateBoard(position, nextPos); // Move the guard and update the grid
                position = nextPos;
                visited.add(positionToString(position));
            } else {
                dirIndex = (dirIndex + 1) % 4; // Turn right
            }
        }

        System.out.println("Total distinct positions visited by this fucking guard: " + visited.size());
    }

    private Tuple<Integer, Integer> findGuard() {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(i).size(); j++) {
                if (board.get(i).get(j).equals(GUARD)) return new Tuple<>(i, j);
            }
        }
        return null;
    }

    private Tuple<Integer, Integer> move(Tuple<Integer, Integer> pos, int[] dir) {
        return new Tuple<>(pos.getA() + dir[0], pos.getB() + dir[1]);
    }

    private boolean isInsideBoard(Tuple<Integer, Integer> pos) {
        return pos.getA() >= 0 && pos.getA() < board.size()
                && pos.getB() >= 0 && pos.getB() < board.getFirst().size();
    }

    private boolean canMove(Tuple<Integer, Integer> pos) {
        return board.get(pos.getA()).get(pos.getB()).equals(".");
    }

    private void updateBoard(Tuple<Integer, Integer> current, Tuple<Integer, Integer> next) {
        board.get(current.getA()).set(current.getB(), "."); // Clear current position
        board.get(next.getA()).set(next.getB(), GUARD);     // Place guard at new position
    }

    private String positionToString(Tuple<Integer, Integer> pos) {
        return pos.getA() + "|" + pos.getB();
    }


    @Override
    public void solve2() {
    }

    @Override
    public int getDay() {
        return 6;
    }

    @Override
    public String print() {
        return "Day 6";
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    private static class InvalidMoveException extends Exception {
        public InvalidMoveException(String message) {
            super(message);
        }
    }
}