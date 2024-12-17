package me.perwollnt.d3;

import me.perwollnt.helpers.Solver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayThreeSolver extends Solver {

    String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";
    Pattern pattern = Pattern.compile(regex);

    String regex2 = "(?:do\\(\\)|don't\\(\\))|mul\\s*\\(\\s*(\\d{1,3})\\s*,\\s*(\\d{1,3})\\s*\\)";
    Pattern pattern2 = Pattern.compile(regex2);

    @Override
    public void solve1() {
        String data = DayThreeReader.read();
        Matcher matcher = pattern.matcher(data);

        int result = 0;
        int count = 0;

        while (matcher.find()) {
            String match = matcher.group();
            String[] split = match.split(",");
            int a = Integer.parseInt(split[0].substring(4));
            int b = Integer.parseInt(split[1].substring(0, split[1].length() - 1));

            result += a * b;
            count++;
        }

        System.out.println("Day 3 - Part 1: " + result + " " + count);
    }

    @Override
    public int getDay() {
        return 3;
    }

    @Override
    public void solve2() {
        String data = DayThreeReader.read();
        Matcher matcher = pattern2.matcher(data);

        int result = 0;
        int count = 0;

        boolean doFlag = true;

        while (matcher.find()) {
            String match = matcher.group();

            if (match.contains("don't")) {
                doFlag = false;
                continue;
            }

            if (match.contains("do")) {
                doFlag = true;
                continue;
            }

            if (!doFlag) {
                continue;
            }

            String[] split = match.split(",");
            int a = Integer.parseInt(split[0].substring(4));
            int b = Integer.parseInt(split[1].substring(0, split[1].length() - 1));

            result += a * b;
            count++;
        }

        System.out.println("Day 3 - Part 2: " + result + " " + count);
    }

    @Override
    public String print() {
        return "Day 3";
    }
}
