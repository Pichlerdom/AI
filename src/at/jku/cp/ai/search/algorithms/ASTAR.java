package at.jku.cp.ai.search.algorithms;

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
		StackWithFastContains<Pair<Double, Node>> path = new StackWithFastContains<>();
		
		
		
		
		Pair<Double,Node> curr = new Pair<Double, Node>(heuristic.apply(start), start);
		
		path.push(curr);
		
		do {
			if(endPredicate.test(curr.s)){
				return curr.s;
			}
			
			
			List<Node> adjacentNodes = curr.s.adjacent();
			for(Node node : adjacentNodes) {
				
				Pair<Double, Node> help = new Pair<Double, Node>(heuristic.apply(node) 
						+ (curr.f - heuristic.apply(node.parent())) 
						+ cost.apply(node), node);
				
				if(!path.contains(help)) {				
					queue.add(help);		
					path.push(help);
				}
			}
			
			curr = queue.remove();	
		
			
		} while (!queue.isEmpty());
		
		
		return null;
	}
}
