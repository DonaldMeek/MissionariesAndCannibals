/* Node for an iterative deepening tree where each node has three values stored in an array and each node has at most five 
 * children. The default constructor defines a root. Non-root nodes must pass a parent to the constructor. 
 */

package iterativeDeepening;

public class Node {
	private int [] values; // Contains the number of missionaries, cannibals, and boats that are on the wrong side respectively.
	int [][] childrenValues;
	private Node [] children;
	private Node parent; 
	private boolean isRoot;
	private boolean isOnSolutionPath; // used to find and print values on the solution path after the solution is found
	private int numberOfChildren;
	private int depth;

	private final int [][] MCB_VEC = {{1,0,1},{2,0,1},{0,1,1},{0,2,1},{1,1,1}}; // used to perform vector addition or vector subtraction on node values for missionaries, cannibals, and the boat
	
	// Root constructor 
	Node()
	{
		setDefaults();
		isRoot = true; 
		isOnSolutionPath = true;
		depth = 0;
		setValues(3, 3, 1);
	}
	
	// Child constructor
	Node(Node n, int [] nodeValues, int d)
	{
		setDefaults();
		parent = n;
		depth = d;
		isRoot = false;
		isOnSolutionPath = false;
		setValues(nodeValues);
	}

	private void setDefaults()
	{
		values = new int[3];
		children = new Node[5];		
		childrenValues = new int[5][3];
		numberOfChildren = 0;
	}
	
	// Checks for possible children, defines them, and checks if the solution is found.
	public Node setChildren()
	{			
		setChildrenValues();
		Node n = null;

		// define each child
		for (int k = 0; k < 5; k++)
		{
			if (k < numberOfChildren) 
			{
				n = new Node(this, childrenValues[k], this.depth + 1);
				children[k] = n;
				
				//If the solution is found, end the test and print the values on the path to the solution
				if( n.isSolution() ) return n;
			}
			else
			{
				childrenValues[k] = null;
				children[k] = null;
			}
		}
		n = null;
		return null;
	}
	
	// Finds and checks childrenValues
	private void setChildrenValues()
	{
		int [] tempValues = new int[3];
		
		numberOfChildren = 0;
		for (int i = 0; i < 5; i++) // i is an index for operands 
		{
			//Reset tempValues
			for (int index = 0; index < 3; index++) tempValues[index] = this.values[index];
			
			// If this node has odd depth, then we add values to get possible children's values. Otherwise we subtract
			if (this.depth % 2 != 0) for (int j = 0; j < 3; j++) tempValues[j] += MCB_VEC[i][j];
			else for (int j = 0; j < 3; j++) tempValues[j] -= MCB_VEC[i][j];
			
			// Find which children have valid values and store them in childrenValues
			if ( hasValidValues(tempValues) )
			{
				for (int j = 0; j < 3; j++) childrenValues[numberOfChildren][j] = tempValues[j];
				numberOfChildren++;
			}
		}
		tempValues = null;
	}
	
	public int getNumberOfChildren()
	{
		return numberOfChildren;
	}
	
	public Node getChildAtIndex(int index)
	{
		return children[index];
	}
	
	private void setValues(int num1, int num2, int num3)
	{
		values[0] = num1;
		values[1] = num2;
		values[2] = num3;
	}
	
	private void setValues(int [] v)
	{
		for (int i = 0; i < 3; i++) values[i] = v[i];
	}
	
	public int getValueAtIndex(int index)
	{	
		return values[index];
	}
	
	public void printValues()
	{
		System.out.println(values[0] + " " + values[1] + " " + values[2] + "\n");
	}
	
	public void printValues(int [] v)
	{
		System.out.println(v[0] + " " + v[1] + " " + v[2] + "\n");
	}
	
	public void setIsOnSolutionPath(boolean choice)
	{
		this.isOnSolutionPath = choice;
	}
	
	public boolean getIsOnSolutionPath()
	{
		return isOnSolutionPath;
	}
	
	public boolean getIsRoot()
	{
		return isRoot;
	}
	
	public Node getParent()
	{
		return parent;
	}
	
	public boolean isSolution()
	{
		if(values[0] == 0 && values[1] == 0 && values[2] == 0) return true;
		else return false;	
	}
	
	public boolean hasValidValues(int [] v)
	{
		if (v[0] != 0 && v[0] < v[1]) return false; // Check if there are more cannibals than missionaries on the wrong side
		if ( ( (3 - v[0]) != 0) && ( (3 - v[0]) < (3 - v[1]) ) ) return false; // Check if there are more cannibals than missionaries on the right side
		for (int i = 0; i < 2; i++) if (v[i] > 3 || v[i] < 0) return false; // Check if there is an invalid number of people
 		if (v[2] != 0 && v[2] != 1) return false;	// Check if the boat is in an invalid state
		return true;
	}
}
