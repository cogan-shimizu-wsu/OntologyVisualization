package dase.wright.edu.ontoViz.OntolologyVisualization;

public class PropertyNode
{
	boolean	isReverse;
	String	propertyName;

	public void setNot(boolean not)
	{
		this.isReverse = not;
	}

	public PropertyNode(Boolean val, String name)
	{
		this.isReverse = val;
		this.propertyName = name;
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}
}
