package me.perwollnt.d1;

import me.perwollnt.helpers.Solver;
import me.perwollnt.helpers.Tuple;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DayOneSolver extends Solver {

    public void solve1() {
        Tuple<List<String>, List<String>> data = DayOneReader.read();

        int distance = 0;

        List<String> column1 = data.getA();
        List<String> column2 = data.getB();

        column1.sort(String::compareTo);
        column2.sort(String::compareTo);

        for (int i = 0; i < column1.size(); i++) {
            distance += Math.abs(Integer.parseInt(column1.get(i)) - Integer.parseInt(column2.get(i)));
        }

        System.out.println("Distance: " + distance);

    }

    @Override
    public int getDay() {
        return 1;
    }

    @Override
    public String print() {
        return "Day 1";
    }

    public void solve2() {
        Tuple<List<String>, List<String>> data = DayOneReader.read();

        AtomicInteger similiarityScore = new AtomicInteger();

        List<String> column1 = data.getA();
        List<String> column2 = data.getB();

        column1.forEach(line -> {
            int count = column2.stream().filter(line::equals).mapToInt(e -> 1).sum();
            int number = Integer.parseInt(line);

            similiarityScore.addAndGet(count * number);
        });

        System.out.println("Similiarity score: " + similiarityScore);
    }
}
