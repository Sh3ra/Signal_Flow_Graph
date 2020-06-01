package PartOne;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SignalFlowGraph {

    ArrayList<ArrayList<Pair<Integer, Double>>> graph;
    int src = 0, des;
    ArrayList<ArrayList<Double>> forwardPaths = new ArrayList<>();
    ArrayList<ArrayList<Integer>> forwardPathsNodes = new ArrayList<>();
    ArrayList<ArrayList<Double>> loops = new ArrayList<>();
    ArrayList<ArrayList<Integer>> loopsNodes = new ArrayList<>();
    ArrayList<ArrayList<Double>> trace = new ArrayList<>();
    ArrayList<ArrayList<Integer>> traceNodes = new ArrayList<>();


    public ArrayList<ArrayList<Pair<Integer, Double>>> getGraph() {
        int n, k;
        System.out.print("P.S: Nodes must be from 0 to n-1 inclusive where 0 is Source.\n");
        System.out.print("Enter Number of Nodes: ");
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        System.out.print("Enter Number of Edges: ");
        k = scanner.nextInt();
        System.out.print("Enter every two connected nodes and their weight:\n");
        graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ArrayList<Pair<Integer, Double>> b = new ArrayList<>();
            graph.add(b);
        }
        for (int i = 0; i < k; i++) {
            int x = scanner.nextInt(), y = scanner.nextInt();
            double z = scanner.nextInt();
            graph.get(x).add(new Pair<>(y, z));
        }
        for (ArrayList<Pair<Integer, Double>> i : graph) {
            if (i.size() == 0) des = graph.indexOf(i);
        }

        dfs(src, des, true, false);
        forwardPaths = (ArrayList<ArrayList<Double>>) trace.clone();
        forwardPathsNodes = (ArrayList<ArrayList<Integer>>) traceNodes.clone();
        trace.clear();
        traceNodes.clear();
        for (int i = 0; i < n; i++) {
            temp.clear();
            nodes.clear();
            dfs(i, i, true, true);
        }
        loops = (ArrayList<ArrayList<Double>>) trace.clone();
        loopsNodes = (ArrayList<ArrayList<Integer>>) traceNodes.clone();
        /*
        System.out.println(forwardPathsNodes);
        System.out.println(forwardPaths);
        System.out.println(loopsNodes);
        System.out.println(loops);
        */
        System.out.println("Total Transfer Function: " + getMasonFormula());
        return graph;
    }

    private final ArrayList<Double> temp = new ArrayList<>();
    private final ArrayList<Integer> nodes = new ArrayList<>();

    private void dfs(int src, int des, boolean firstTime, boolean loop) {
        if (src == des) {
            if (!firstTime) {
                ArrayList<Double> myObject = (ArrayList<Double>) temp.clone();
                ArrayList<Integer> myNodes = (ArrayList<Integer>) nodes.clone();
                if (loop) Collections.sort(myNodes);
                if (!traceNodes.contains(myNodes)) {
                    trace.add(myObject);
                    traceNodes.add(myNodes);
                }
                return;
            }
        }
        if (nodes.contains(src)) return;
        for (int i = 0; i < graph.get(src).size(); i++) {
            firstTime = false;
            nodes.add(src);
            temp.add(graph.get(src).get(i).getValue());
            dfs(graph.get(src).get(i).getKey(), des, firstTime, loop);
            temp.remove(temp.size() - 1);
            nodes.remove(nodes.size() - 1);
        }
    }

    double getMasonFormula() {
        double m = 0;
        for (int i = 0; i < forwardPaths.size(); i++) {
            double pi = 1;
            pi *= getGain(forwardPaths.get(i));
            pi *= getDetermirnti(forwardPathsNodes.get(i));
            m += pi;
        }
        double delta = getDetermirnti(new ArrayList<Integer>());
        //System.out.println(delta);
        return m / delta;
    }

    private double getGain(ArrayList<Double> arrayList) {
        double ans = 1;
        for (Double i : arrayList) {
            ans *= i;
        }
        return ans;
    }

    private double getDetermirnti(ArrayList<Integer> integers) {
        double d = 0;
        d = 1;
        double totalGain = 0;
        int count = 0;
        for (ArrayList<Double> i : loops) {
            if (canAddLoop(new ArrayList<Integer>(), count, integers))
                totalGain += getGain(i);
            count++;
        }
        d -= totalGain;
        for (int i = 2; i <= loops.size(); i++) {
            ArrayList<Integer> temp = new ArrayList<>();
            getGainUntouchingLoops(integers, 0, i, temp);
            d += (tempGain * Math.pow(-1, i));
            tempGain = 0;
        }
        return d;
    }

    int tempGain = 0;

    private void getGainUntouchingLoops(ArrayList<Integer> path, int index, int maxSize, ArrayList<Integer> temp) {
        ArrayList<Integer> newArray = (ArrayList<Integer>) temp.clone();
        if (temp.size() == maxSize) {
            int t = 1;
            for (int i = 0; i < maxSize; i++) {
                t *= getGain(loops.get(newArray.get(i)));
            }
            tempGain += t;
            return;
        }
        if (index == loops.size()) return;
        getGainUntouchingLoops(path, index + 1, maxSize, newArray);
        if (canAddLoop(temp, index, path)) {
            newArray.add(index);
            getGainUntouchingLoops(path, index + 1, maxSize, newArray);
        }
    }

    private boolean canAddLoop(ArrayList<Integer> temp, int index, ArrayList<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            if (loopsNodes.get(index).contains(path.get(i)))
                return false;
        }
        for (Integer i : temp) {
            for (int j = 0; j < loopsNodes.get(i).size(); j++) {
                if (loopsNodes.get(index).contains(loopsNodes.get(i).get(j)))
                    return false;
            }
        }
        return true;
    }

}

