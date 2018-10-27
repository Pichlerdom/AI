package at.jku.cp.ai.search.algorithms;

import java.util.function.Predicate;

import at.jku.cp.ai.search.Node;
import at.jku.cp.ai.search.Search;
import at.jku.cp.ai.search.datastructures.Pair;
import at.jku.cp.ai.search.datastructures.StackWithFastContains;

// Depth-Limited Depth-First Search
public class DLDFS implements Search {
	// we need an O(1) datastructure for path-avoidance.
	// 'contains' is O(N) in a stack, where N
	// is the current depth, so we use a stack and a set in parallel
	
	private StackWithFastContains<Node> path;
	private StackWithFastContains<Integer> adjacentPath;
	
	private int limit;

	public DLDFS(int limit) {
		this.limit = limit;
	}

	@Override
	public Node search(Node start, Predicate<Node> endPredicate) {
		
		path = new StackWithFastContains<Node>();
		adjacentPath = new StackWithFastContains<Integer>();
		
		int countDepth = 0;
		
		
		// path.push(new Pair<Integer, Node>(0, start));
	    Node curr = start;
	    int adjacentIndex = 0; 
		
		do {
			
			if(endPredicate.test(curr)){
				return curr;
			}
			
			if(curr.adjacent().size() > adjacentIndex && countDepth <= limit) {
				
				if(!path.contains(curr.adjacent().get(adjacentIndex))) {
					
					path.push(curr); 
					adjacentPath.push(adjacentIndex +1);
					
					curr =  curr.adjacent().get(adjacentIndex);
					adjacentIndex = 0;
					
					countDepth ++;
				}
				
				else {
					
					adjacentIndex++;
				}			
			}
			
			else {
				
				curr = path.pop();
				adjacentIndex = adjacentPath.pop();
				
				countDepth --;
			}
				
		} while(!path.isEmpty());
		
		return null;
	}
}
