package at.jku.cp.ai.search.algorithms;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import at.jku.cp.ai.search.Node;
import at.jku.cp.ai.search.Search;
import at.jku.cp.ai.search.datastructures.Pair;
import at.jku.cp.ai.search.datastructures.StablePriorityQueue;
import at.jku.cp.ai.search.datastructures.StackWithFastContains;

// A* Search
public class ASTAR implements Search
{
	@SuppressWarnings("unused")
	private Function<Node, Double> heuristic;
	@SuppressWarnings("unused")
	private Function<Node, Double> cost;

	public ASTAR(Function<Node, Double> heuristic, Function<Node, Double> cost) {
		this.heuristic = heuristic;
		this.cost = cost;
	}
	
	@Override
	public Node search(Node start, Predicate<Node> endPredicate)
	{
		StablePriorityQueue<Double, Node> queue = new StablePriorityQueue<>();
		
		HashMap<Integer, Double> openList = new HashMap<>();
		
		HashMap<Integer, Double> closedList = new HashMap<>();
		
		Pair<Double,Node> curr = new Pair<Double, Node>(heuristic.apply(start), start);
		
		openList.put(curr.s.hashCode(), curr.f);
		
		do {
			if(endPredicate.test(curr.s)){
				return curr.s;
			}
			
			List<Node> adjacentNodes = curr.s.adjacent();
			for(Node node : adjacentNodes) {
				
				double g;
				if(closedList.get(curr.s.hashCode()) == null) {
				    g = cost.apply(node);
				}
				else {
					g = cost.apply(node) + closedList.get(curr.s.hashCode());
				}
				
				double h = heuristic.apply(node);
				
				Pair<Double, Node> newNode = new Pair<Double, Node>(h + g, node);
				
				if(openList.containsKey(node.hashCode())) {
					
					if(openList.get(node.hashCode()) < newNode.f) {
						continue;
					}
				}
			
				if(closedList.containsKey(node.hashCode())) {
					if(closedList.get(node.hashCode()) < newNode.f) {
						continue;
					}
					else {
						closedList.put(node.hashCode(), newNode.f);
					}
				}
					
				queue.add(newNode);		
				openList.put(newNode.s.hashCode(), newNode.f);
			}
			
			closedList.put(curr.s.hashCode(), curr.f);
			
			curr = queue.remove();	
			
		} while (!queue.isEmpty());
		
		
		return null;
	}
}
