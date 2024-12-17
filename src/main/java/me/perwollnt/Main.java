package me.perwollnt;

import me.perwollnt.helpers.Settings;
import me.perwollnt.helpers.Solver;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(Settings.getCookies());
            Reflections reflections = new Reflections("me.perwollnt");
            Set<Class<? extends Solver>> solverClasses = reflections.getSubTypesOf(Solver.class);

            // I am pretty sure there's a better way to solve this issue, but I could not care less
            Set<Class<? extends Solver>> sortedSolverClasses = solverClasses.stream()
                    .sorted((a, b) -> {
                        try {
                            Solver solverA = a.getDeclaredConstructor().newInstance();
                            Solver solverB = b.getDeclaredConstructor().newInstance();
                            return Integer.compare(solverA.getDay(), solverB.getDay());
                        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                                 InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            for (Class<? extends Solver> solverClass : sortedSolverClasses) {
                if (!Modifier.isAbstract(solverClass.getModifiers())) {
                    Solver instance = solverClass.getDeclaredConstructor().newInstance();

                    System.out.println("\n");
                    System.out.println(instance.print());
                    if (!instance.isDisabled()) instance.solve();
                    else System.out.println("This solver is disabled");
                }
            }
        } catch (Exception e) {
            String errorMessage = "An error occurred while trying to solve the puzzles: " + e.getMessage();
            System.out.println(errorMessage);
        }
    }
}