package me.perwollnt.d2;

import me.perwollnt.helpers.Solver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DayTwoSolver extends Solver {

    public void solve1() {
        List<List<Integer>> lines = DayTwoReader.read();

        int validCount = 0;
        int invalidCount = 0;

        // the levels are either all increasing or all decreasing, if both are found in an inside list that list is invalid
        // any two adjacent levels must only differ by at most 3 and at least 1

        for (List<Integer> line : lines) {
            if (isValid(line)) {
                validCount++;
            } else {
                invalidCount++;
            }
        }

        System.out.println("Valid: " + validCount);
        System.out.println("Invalid: " + invalidCount);

    }

    public void solve2() {
        List<List<Integer>> lines = DayTwoReader.read();

        int validCount = 0;
        int invalidCount = 0;

        // the levels are either all increasing or all decreasing, if both are found in an inside list that list is invalid
        // any two adjacent levels must only differ by at most 3 and at least 1

        for (List<Integer> line : lines) {
            if (isValid(line)) {
                validCount++;
                continue;
            }
            // if its not valid, we loop the list, remove the current looped element and try again
            for (int i = 0; i < line.size(); i++) {
                List<Integer> copy = new ArrayList<>(line);
                copy.remove(i);
                if (isValid(copy)) {
                    validCount++;
                    break;
                }
            }
            invalidCount++;
        }

        System.out.println("Valid + dampener: " + validCount);
        System.out.println("Invalid + dampener: " + invalidCount);

    }

    @Override
    public String print() {
        return "Day 2";
    }

    @Override
    public int getDay() {
        return 2;
    }

    private boolean isValid(List<Integer> line) {
        boolean isIncreasingOrDecreasing =
                IntStream.range(0, line.size() - 1).allMatch(i -> line.get(i) <= line.get(i + 1)) ||
                        IntStream.range(0, line.size() - 1).allMatch(i -> line.get(i) >= line.get(i + 1));
        boolean isValidDifferences =
                IntStream.range(0, line.size() - 1).allMatch(i -> Math.abs(line.get(i + 1) - line.get(i)) >= 1 && Math.abs(line.get(i + 1) - line.get(i)) <= 3);

        return isIncreasingOrDecreasing && isValidDifferences;
    }
}
