package me.perwollnt.d5;

import me.perwollnt.helpers.Solver;
import me.perwollnt.helpers.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DayFiveSolver extends Solver {
    @Override
    public void solve1() {
        DayFiveReader.ReadValue value = DayFiveReader.read();

        // build graph from rules.
        Map<Integer, List<Integer>> graph = buildGraph(value.getTypeOne());

        // validate each update (typetwo) and file the middle page for valid updates.
        int sum = 0;

        for (List<Integer> update : value.getTypeTwo()) {
            if (isValidUpdate(graph, update)) {
                int middle = update.get(update.size() / 2);
                sum += middle;
            }
        }

        System.out.println("Sum of middle pages: " + sum);
    }

    /**
     * Validate if a given update follows the rules of the graph.
     *
     * @param graph  the graph
     * @param update the update
     * @return true if the update is valid, false otherwise.
     */
    private boolean isValidUpdate(Map<Integer, List<Integer>> graph, List<Integer> update) {
        // create position map for the update
        Map<Integer, Integer> positionMap = new HashMap<>();
        for (int i = 0; i < update.size(); i++) {
            positionMap.put(update.get(i), i);
        }

        // check if all relevant rules are satisfied
        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            int x = entry.getKey();
            if (!positionMap.containsKey(x)) continue; // skip if x is not in the update

            for (int y : entry.getValue()) {
                if (positionMap.containsKey(y) && positionMap.get(x) >= positionMap.get(y)) {
                    // rule x -> y is violated
                    return false;
                }
            }
        }

        return true;
    }

    private Map<Integer, List<Integer>> buildGraph(List<Tuple<Integer, Integer>> rules) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (Tuple<Integer, Integer> rule : rules) {
            graph.computeIfAbsent(rule.getA(), k -> new ArrayList<>()).add(rule.getB());
        }
        return graph;
    }

    //private List<Integer> resolverOrder(List<Tuple<Integer, Integer>> rules) {
    //    // build graph and inDegre map
    //    Map<Integer, List<Integer>> graph = new HashMap<>();
    //    Map<Integer, Integer> inDegree = new HashMap<>();


    //    // now we populate graph and in-degree map
    //    for (Tuple<Integer, Integer> rule : rules) {
    //        graph.computeIfAbsent(rule.getA(), k -> new ArrayList<>()).add(rule.getB());
    //        inDegree.put(rule.getB(), inDegree.getOrDefault(rule.getB(), 0) + 1);
    //        inDegree.putIfAbsent(rule.getA(), 0);
    //    }

    //    // perform a topological sort
    //    Queue<Integer> queue = new LinkedList<>();
    //    for(Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
    //        if(entry.getValue() == 0) {
    //            queue.offer(entry.getKey());
    //        }
    //    }

    //    List<Integer> sortedList = new ArrayList<>();
    //    while (!queue.isEmpty()) {
    //        int current = queue.poll();
    //        sortedList.add(current);

    //        for(int neighbor : graph.getOrDefault(current, Collections.emptyList())) {
    //            inDegree.put(neighbor, inDegree.get(neighbor) -1);
    //            if(inDegree.get(neighbor) == 0) {
    //                queue.offer(neighbor);
    //            }
    //        }
    //    }

    //    if(sortedList.size() != inDegree.size()) {
    //        throw new IllegalStateException("We fucked up, Cycle detected; No valid ordering exists");
    //    }

    //    return sortedList;
    //}

    @Override
    public void solve2() {
        DayFiveReader.ReadValue value = DayFiveReader.read();

        // Build the graph from rules.
        Map<Integer, List<Integer>> graph = buildGraph(value.getTypeOne());

        // Validate each update and correct invalid ones.
        int invalidOrderedSum = 0;

        for (List<Integer> update : value.getTypeTwo()) {
            if (!isValidUpdate(graph, update)) {
                // If the update is invalid, correct it and sum its middle page.
                List<Integer> corrected = correctUpdate(graph, update);
                if (!corrected.isEmpty()) {
                    int middle = corrected.get(corrected.size() / 2);
                    invalidOrderedSum += middle;
                }
            }
        }

        System.out.println("Sum of middle pages for corrected updates: " + invalidOrderedSum);
    }

    private List<Integer> correctUpdate(Map<Integer, List<Integer>> graph, List<Integer> update) {
        // Step 1: Filter the graph to include only nodes in the update.
        Map<Integer, List<Integer>> subGraph = filterGraph(graph, update);

        // Step 2: Perform topological sorting on the sub-graph.
        return topologicalSort(subGraph, update);
    }

    // Helper to filter the graph for relevant nodes in the update.
    private Map<Integer, List<Integer>> filterGraph(Map<Integer, List<Integer>> graph, List<Integer> update) {
        Map<Integer, List<Integer>> subGraph = new HashMap<>();
        for (int page : update) {
            if (graph.containsKey(page)) {
                subGraph.put(page, new ArrayList<>());
                for (int neighbor : graph.get(page)) {
                    if (update.contains(neighbor)) {
                        subGraph.get(page).add(neighbor);
                    }
                }
            }
        }
        return subGraph;
    }

    // Topological sort for correction.
    private List<Integer> topologicalSort(Map<Integer, List<Integer>> graph, List<Integer> update) {
        // Step 1: Calculate in-degrees for nodes in the graph.
        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int node : update) {
            inDegree.put(node, 0);
        }
        for (Map.Entry<Integer, List<Integer>> entry : graph.entrySet()) {
            for (int neighbor : entry.getValue()) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }

        // Step 2: Initialize the queue with nodes having in-degree 0.
        Queue<Integer> queue = new LinkedList<>();
        for (int node : update) {
            if (inDegree.get(node) == 0) {
                queue.offer(node);
            }
        }

        // Step 3: Perform the sorting.
        List<Integer> sortedList = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            sortedList.add(current);

            for (int neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        // If the sorting doesn’t include all nodes, it indicates a cycle, return empty.
        if (sortedList.size() != update.size()) {
            return Collections.emptyList();
        }

        return sortedList;
    }

    @Override
    public int getDay() {
        return 5;
    }

    @Override
    public String print() {
        return "Day 5";
    }
}