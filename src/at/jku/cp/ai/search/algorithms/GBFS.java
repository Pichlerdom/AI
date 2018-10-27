package at.jku.cp.ai.search.algorithms;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;



import at.jku.cp.ai.search.Node;
import at.jku.cp.ai.search.Search;
import at.jku.cp.ai.search.datastructures.Pair;
import at.jku.cp.ai.search.datastructures.StablePriorityQueue;
import at.jku.cp.ai.search.datastructures.StackWithFastContains;

// Greedy Best-First Search
public class GBFS implements Search
{
	@SuppressWarnings("unused")
	private Function<Node, Double> heuristic;

	public GBFS(Function<Node, Double> heuristic) {
		this.heuristic = heuristic;
	}
	
	@Override
	public Node search(Node start, Predicate<Node> endPredicate)
	{
		StablePriorityQueue<Double, Node> queue = new StablePriorityQueue<>();
		StackWithFastContains<Integer> path = new StackWithFastContains<>();
		
		
		Node curr = start;
		
		path.push(start.hashCode());
		
		do {
			
			if(endPredicate.test(curr)){
				return curr;
			}
			
			List<Node> adjacentNodes = curr.adjacent();
			for(Node node : adjacentNodes) {
				if(!path.contains(node.hashCode())) {
					queue.add(new Pair<Double, Node>(heuristic.apply(node), node));
					path.push(node.hashCode());
				}
			}
			
			curr = queue.remove().s;	
		} while (!queue.isEmpty());
		
		return null;
	}
}
