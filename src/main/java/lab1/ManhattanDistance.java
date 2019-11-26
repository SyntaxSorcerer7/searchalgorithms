package lab1;
import java.util.HashMap;
import java.util.function.ToDoubleFunction;

import aima.core.search.framework.evalfunc.HeuristicFunction;
import lab1.RubiksCube;

import static java.lang.Math.abs;


public class ManhattanDistance implements HeuristicFunction, ToDoubleFunction<Object> {

	private int [][][] goal = null;
	private int n = -1;
	private HashMap<Integer, Position> idToPosition = new HashMap<Integer, Position>();

	public ManhattanDistance(RubiksCube rc) {
		n = rc.getState().length;
		goal = new int[n][n][n];
		int counter = 1;
		for (int i = 0; i < n; i++){
			for (int j = 0; j < n; j++){
				for (int k = 0; k < n; k++){
					goal[i][j][k] = counter;
					idToPosition.put(counter, new Position(i, j, k));
					counter++;
				}
			}
		}
	}
	

	class Position{
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
					int id = stateRC.getState()[i][j][k].getId();
					Position pGoal = idToPosition.get(id);
					Position pState = new Position(i,j,k);
					totalDistance += getManhattanDistance(pState, pGoal);
				}
			}
		}
		double admissible = totalDistance/(n*n*2);
		return admissible;
	}

	private double getManhattanDistance(Position p1, Position p2){
		double distance = ((double)abs(p1.i-p2.i)+abs(p1.j-p2.j)+abs(p1.k-p2.k));
		return distance;
	}
	
	@Override
	public double applyAsDouble(Object arg0) {
		return h(arg0);
	}

}
