package lab1;

import aima.core.search.framework.evalfunc.HeuristicFunction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.ToDoubleFunction;

import static java.lang.Math.abs;


public class EdgeManhattanDistance implements HeuristicFunction, ToDoubleFunction<Object> {

    private int[][][] goal = null;
    private int n = -1;
    private HashMap<Integer, Position> idToPosition = new HashMap<Integer, Position>();

    public EdgeManhattanDistance(RubiksCube rc) {
        n = rc.getState().length;

        goal = new int[n][n][n];
        int counter = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    goal[i][j][k] = counter;
                    idToPosition.put(counter, new Position(i, j, k));
                    counter++;
                }
            }
        }
    }


    class Position {
        int i;
        int j;
        int k;

        public Position(int i, int j, int k) {
            this.i = i;
            this.j = j;
            this.k = k;
        }
    }

    @Override
    //Your in this method!
    public double h(Object state) {
        RubiksCube stateRC = (RubiksCube) state;

        double totalDistance = 0.0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                for(int k = 0; k < n; k++){
                    Position pState = new Position(i, j, k);
                    if(isEdge(pState, n)) {
                        int id = stateRC.getState()[i][j][k].getId();
                        Position pGoal = idToPosition.get(id);
                        totalDistance += getManhattanDistance(pState, pGoal);
                    }
                }
            }
        }

        double admissible = totalDistance / ((4*n-4)*(n-1));
        return admissible;
    }

    private boolean isEdge(Position p, int n){
        return p.i == 0 || p.i == n-1 || p.j == 0 || p.j == n-1 || p.k == 0 || p.k == n - 1;
    }

    private double getManhattanDistance(Position p1, Position p2) {
        double distance = ((double) abs(p1.i - p2.i) + abs(p1.j - p2.j) + abs(p1.k - p2.k));
        return distance;
    }

    @Override
    public double applyAsDouble(Object arg0) {
        return h(arg0);
    }

}
