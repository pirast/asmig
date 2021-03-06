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
package atongmu.ast;
import java.util.*;
import atongmu.ast.visitor.AbstractVisitor;

public class BinaryExpression extends Expression{
	private final Expression left;
	private final Expression right;
	private final Connective conn;
	
	public BinaryExpression(Connective c, Expression l, Expression r){
		conn = c;
		left = l;
		right = r;
	}
	
	public Expression getLeft(){return left;}
	public Expression getRight(){return right;}

	public void accept(AbstractVisitor visitor){
		visitor.visit(this);
	}

	public String getConnectiveString(){
		return conn.toString();
	}

	public Connective getConnective(){
		return conn;
	}

	public boolean isBinaryExpression(){return true;}

	public String toString(){
		return conn.toString() +"["+left + ","+right+"]";
	}
	
}
