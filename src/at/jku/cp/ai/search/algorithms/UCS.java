package at.jku.cp.ai.search.algorithms;

import java.util.function.Function;
import java.util.function.Predicate;

import at.jku.cp.ai.search.Node;
import at.jku.cp.ai.search.Search;
import at.jku.cp.ai.search.datastructures.Pair;
import at.jku.cp.ai.search.datastructures.StablePriorityQueue;
import at.jku.cp.ai.search.datastructures.StackWithFastContains;

// Uniform Cost Search
public class UCS implements Search
{
	@SuppressWarnings("unused")
	private Function<Node, Double> cost;

	public UCS(Function<Node, Double> cost) {
		this.cost = cost;
	}

	@Override
	public Node search(Node start, Predicate<Node> endPredicate)
	{
		
		StablePriorityQueue<Double, Node> queue = new StablePriorityQueue<>();
		StackWithFastContains<Integer> path = new StackWithFastContains<>();
		queue.add(new Pair<Double, Node>(cost.apply(start), start));
		path.push(start.hashCode());
		
		while(!queue.isEmpty())
		{
			if(endPredicate.test(queue.element().s)) {
				return queue.element().s;
			}
			
			Pair<Double,Node> curr = queue.remove();
			
			for(Node node : curr.s.adjacent()){
				//System.out.println(path.contains(node.hashCode()));
				if(!path.contains(node.hashCode())){
					path.push(node.hashCode());
					queue.add(new Pair<Double, Node>(cost.apply(node) + curr.f, node));
				}
				
			}
		}
		return null;
	}
		
}
