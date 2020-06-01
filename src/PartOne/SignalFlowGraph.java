package PartOne;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SignalFlowGraph {

    ArrayList<ArrayList<Pair<Integer,Integer>>> graph;
    int src=0,des;
    ArrayList<ArrayList<Integer>> forwardPaths =new ArrayList<>();
    ArrayList<ArrayList<Integer>> loops =new ArrayList<>();
    ArrayList<ArrayList<Integer>> trace =new ArrayList<>();


    void getGraph(){
        int n,k;
        System.out.print("P.S: Nodes must be from 0 to n where 0 is src.\n");
        System.out.print("Enter Number of Nodes: ");
        Scanner scanner=new Scanner(System.in);
        n=scanner.nextInt();
        System.out.print("Enter Number of Edges: ");
        k=scanner.nextInt();
        System.out.print("Enter every two connected nodes and their weight:\n");
        graph=new ArrayList<>();
        for(int i=0;i<=n;i++){ArrayList<Pair<Integer,Integer>>b=new ArrayList<>();graph.add(b);}
        for (int i=0;i<k;i++)
        {
            int x=scanner.nextInt(),y=scanner.nextInt(),z=scanner.nextInt();
            graph.get(x).add(new Pair<>(y,z));
        }
        for (ArrayList<Pair<Integer, Integer>> i:graph) {
            if (i.size() == 0) des = graph.indexOf(i);
        }

        dfs(src,des,true,false);
        forwardPaths = (ArrayList<ArrayList<Integer>>) trace.clone();
        trace.clear();
        for (int i = 0; i <=n ; i++) {
            temp.clear();
            nodes.clear();
            dfs(i,i,true,true);
        }
        loops = (ArrayList<ArrayList<Integer>>) trace.clone();
    }

    ArrayList<Integer>temp=new ArrayList<>();
    ArrayList<Integer>nodes=new ArrayList<>();
    void dfs (int src,int des,boolean firstTime,boolean loop){
         if(src==des){
             if(!firstTime) {
                 ArrayList<Integer> myObject = (ArrayList<Integer>) temp.clone();
                 if (loop) Collections.sort(myObject);
                 if(!trace.contains(myObject))
                    trace.add(myObject);
                 return;
             }
         }
        if(nodes.contains(src)) return;
        for (int i = 0; i < graph.get(src).size(); i++) {
            firstTime=false;
            nodes.add(src);
            temp.add(graph.get(src).get(i).getValue());
            dfs(graph.get(src).get(i).getKey(),des,firstTime,loop);
            temp.remove(temp.size()-1);
            nodes.remove(nodes.size()-1);
        }
    }

    int getMasonFormula(){
        int m=0;

        return m;
    }

}
