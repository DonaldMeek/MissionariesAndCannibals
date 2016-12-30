/* Donald Meek
 * 2-12-2016
 * 
 * The IDT class is the main class for the iterative deepening tree. 
 */

package iterativeDeepening;

public class IDT {

	private Node root = null; 	
	
	IDT()
	{
		Node n = new Node();
		root = n;
	}
	
	// The showResult function takes a root and prints all values on the solution path
	private void showResult(Node n)
	{	
		/* The leftmost number is the number of missionaries on the incorrect side, the middle number is the number
		of cannibals on the incorrect side, and the rightmost number is the number of boats on the incorrect side */
		n.printValues();
		
		// Base Case
		if ( n.isSolution() ) System.exit(0);
		
		// Find the child on the solution path and pass it to this function
		for (int i = 0; i < n.getNumberOfChildren(); i++)
		{
			Node childNode = n.getChildAtIndex(i);
			if ( childNode.getIsOnSolutionPath() ) showResult(childNode);
		}
				
	}
	
	// The findSolutionPath() function take the node that has the solution vector and sets isOnSolutionPath to true for each ancestor of the solution-containing node
	private void findSolutionPath(Node n)
	{
		n.setIsOnSolutionPath(true);
		if ( n.getIsRoot() ) showResult(n);
		findSolutionPath( n.getParent() );
	}
	
	// Start with three missionaries, three cannibals, and one boat out of place.
	public void iterativeDeepeningSearch()
	{	
		Node solutionNode = null;
		for (int limit = 0; true; limit++)
		{
			solutionNode = depthLimitedSearch(root, limit);
			if (solutionNode != null)
			{
				findSolutionPath(solutionNode);
				showResult(root);
			}
		}
	}
	
	// The depthLimitedSearch() function returns the solution node or returns null if the solution is not found. With this algorithm, either a node is null or it has the solution values.
	private Node depthLimitedSearch(Node n, int limit)
	{
		if (limit == 0) return null;
		if (n == null) return null;
		if ( n.isSolution() ) return n;
		
		Node resultNode = n.setChildren(); // Note that setChildren returns the node with the solution if it is found
		Node childNode = null;
		if (resultNode == null)
		{
			resultNode = n;
			for (int i = 0; i < resultNode.getNumberOfChildren(); i++)
			{
				childNode = depthLimitedSearch(resultNode.getChildAtIndex(i), limit - 1);
				if (childNode != null) return childNode; // returns childNode if it has the solution values
			}
		}
		else return resultNode; // returns resultNode if it has the solution values
		return null;
	}
	
	public static void main(String[] args) 
	{
		
		IDT idt = new IDT(); 
		idt.iterativeDeepeningSearch();
	}

}
