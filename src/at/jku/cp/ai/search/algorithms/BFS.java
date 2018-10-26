package at.jku.cp.ai.search.algorithms;

import java.util.Iterator;
import java.util.function.Predicate;
import at.jku.cp.ai.search.datastructures.*;
import at.jku.cp.ai.rau.nodes.IBoardNode;
import at.jku.cp.ai.search.Node;
import at.jku.cp.ai.search.Search;


// Breadth-First search
public class BFS implements Search
{
	@Override
	public Node search(Node start, Predicate<Node> endPredicate)
	{
		StablePriorityQueue<Integer, Node> queue = new StablePriorityQueue<>();
		StackWithFastContains<Integer> path = new StackWithFastContains<>();
		queue.add(new Pair<Integer, Node>(1, start));
		path.push(start.hashCode());
		
		while(!queue.isEmpty())
		{
			if(endPredicate.test(queue.element().s)) {
				return queue.element().s;
			}
			
			Node current = queue.remove().s;
			
			
			for(Node node : current.adjacent()){
				//System.out.println(path.contains(node.hashCode()));
				if(!path.contains(node.hashCode())){
					path.push(node.hashCode());
					queue.add(new Pair<Integer, Node>(1, node));
				}
				
			}	/*
			Pair p;
			System.out.print("(");
			for(int i = 0; i < queue.size(); i++){
				p = queue.remove();
				System.out.printf("%d \t",p.s.hashCode());
				queue.add(p);
			}
			System.out.print(")");
			System.out.println();
			System.out.print("[");
			for(int i = 0; i < path.size(); i++){
				System.out.printf("%d \t",path.elementAt(i).hashCode());
			}
			System.out.print("]");
			System.out.println();*/
		}
		
		
		return null;
	}
}