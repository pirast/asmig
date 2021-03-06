/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++Written by: Hao Wu++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 *	This is a part of my PhD work.
 *  haowu@cs.nuim.ie
 *  APR-2012 
 *  
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ++++++++++++++++++++++++++++++Do or do not, there is no try.+++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */
package atongmu.atg;

public class NodeAttribute extends Edge{
	private NodeType type;
	private String name;
	private PrimitiveType ptype;
	private DataNode data;
	private GraphNode hostnode;
	
	public NodeAttribute(){
		type=null;
		ptype=null;
		name="";
	}
	
	public NodeAttribute(GraphNode node, DataNode dn){
		hostnode=node;
		data=dn;
	}

	public NodeAttribute(String n, NodeType t){
		name=n;
		type=t;
		ptype=null;
	}

	public NodeAttribute(String n, PrimitiveType t){
		name=n;
		ptype=t;
		type=null;
	}

	public String getName(){return name;}
	public void setName(String n){name=n;}

	public boolean isgood(){
		return type!=null ^ ptype!=null;
	}

	public String toString(){		
		return (type!=null) ?
			name+":"+type.toString() 
			:
			name+":"+ptype.toString();
	}
}
