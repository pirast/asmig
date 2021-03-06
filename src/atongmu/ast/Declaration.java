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

import atongmu.type.*;
import atongmu.value.*;
import atongmu.ast.visitor.AbstractVisitor;

public class Declaration extends Expression{
	protected Type type;
	protected String name;
	protected Value value; //optional

	public Declaration(){

	}

	public Declaration(String n, Type t){
		type=t;
		name=n;
	}

	public Declaration(String n, Type t, Value v){
		type=t;
		name=n;
		value=v;
	}

	public Type getType(){return type;}
	public void setType(Type t){type=t;}
	public String getName(){return name;}
	public void setName(String n){name=n;}

	public void accept(AbstractVisitor visitor){
		visitor.visit(this);
	}
	
	public String toString(){
		return (value==null) ?
		"declare-XXXX<"+name+":"+type.toString()+">" :
		"<"+name+":"+type.toString()+"->"+value.toString()+">";
	}

}
