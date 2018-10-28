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
		
		StackWithFastContains<Double> openListDouble = new StackWithFastContains<>();
		StackWithFastContains<Node> openListNodes = new StackWithFastContains<>();
		
		StackWithFastContains<Double> closedListDouble = new StackWithFastContains<>();
		StackWithFastContains<Node> closedListNodes = new StackWithFastContains<>();
		
		Pair<Double,Node> curr = new Pair<Double, Node>(heuristic.apply(start), start);
		
		openListDouble.push(curr.f);
		openListNodes.push(curr.s);
		
		do {
			if(endPredicate.test(curr.s)){
				return curr.s;
			}
			
			List<Node> adjacentNodes = curr.s.adjacent();
			for(Node node : adjacentNodes) {
				
				Pair<Double, Node> help = new Pair<Double, Node>(heuristic.apply(node) 
						+ (curr.f - heuristic.apply(node.parent())) 
						+ cost.apply(node), node);
				
				/*if(openListNodes.contains(help.s) && openListDouble.get(openListNodes.indexOf(help.s)) == help.f) {
					continue;
				}
				if(closedListNodes.contains(help.s) && closedListDouble.get(closedListNodes.indexOf(help.s)) == help.f) {
					continue;
				}*/
				
				if(openListNodes.contains(help.s)) {
					
					if(openListDouble.get(openListNodes.indexOf(help.s)) < help.f) {
						continue;
					}
					else {
						openListDouble.set(openListNodes.indexOf(help.s), help.f);;
					}
				}
				
				if(closedListNodes.contains(help.s)) {
					if(closedListDouble.get(closedListNodes.indexOf(help.s)) < help.f) {
						continue;
					}					
				}
					
				queue.add(help);		
				openListDouble.push(help.f);
				openListNodes.push(help.s);
			}
			
			closedListDouble.push(curr.f);
			closedListNodes.push(curr.s);
			
			curr = queue.remove();	
			
		} while (!queue.isEmpty());
		
		
		return null;
	}
}
