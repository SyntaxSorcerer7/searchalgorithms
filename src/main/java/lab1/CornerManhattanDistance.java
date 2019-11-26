package lab1;

import aima.core.search.framework.evalfunc.HeuristicFunction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.ToDoubleFunction;

import static java.lang.Math.abs;


public class CornerManhattanDistance implements HeuristicFunction, ToDoubleFunction<Object> {

    private int[][][] goal = null;
    private int n = -1;
    private HashMap<Integer, Position> idToPosition = new HashMap<Integer, Position>();

    private List<Position> corners;

    public CornerManhattanDistance(RubiksCube rc) {
        n = rc.getState().length;
        corners = Arrays.asList(new Position(0, 0, 0),
                new Position(0, 0, n),
                new Position(0, n, 0),
                new Position(0, n, n),
                new Position(n, 0, 0),
                new Position(n, 0, n),
                new Position(n, n, 0),
                new Position(n, n, n));
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
        for (Position p : corners) {
            int id = stateRC.getState()[p.i][p.j][p.k].getId();
            Position pGoal = idToPosition.get(id);
            totalDistance += getManhattanDistance(p, pGoal);
        }

        double admissible = totalDistance / (n*n);
        return admissible;
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
