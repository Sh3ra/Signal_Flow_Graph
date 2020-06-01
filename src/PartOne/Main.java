package PartOne;

import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //create the graph
        Graph<String, String> g = new DigraphEdgeList<>();
        SignalFlowGraph signalFlowGraph = new SignalFlowGraph();
        ArrayList<ArrayList<Pair<Integer, Double>>> graph = signalFlowGraph.getGraph();
        HashMap<String, Integer> stringHashMap = new HashMap<>();
        for (int i = 0; i < graph.size(); i++)
            g.insertVertex(Integer.toString(i));
        for (int i = 0; i < graph.size(); i++) {
            for (int j = 0; j < graph.get(i).size(); j++) {
                String edge = Double.toString(graph.get(i).get(j).getValue());
                String temp = Double.toString(graph.get(i).get(j).getValue());
                if (stringHashMap.containsKey(edge)) {
                    for (int k = 0; k < stringHashMap.get(edge); k++) {
                        temp += " ";
                    }
                    stringHashMap.replace(edge, stringHashMap.get(edge), stringHashMap.get(edge) + 1);
                }
                else stringHashMap.put(edge, 1);
                g.insertEdge(Integer.toString(i), Integer.toString(graph.get(i).get(j).getKey()), temp);
            }
        }
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);
        Scene scene = new Scene(graphView, 800, 600);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFXGraph Visualization");
        stage.setScene(scene);
        stage.show();

//IMPORTANT - Called after scene is displayed so we can have width and height values
        graphView.init();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
