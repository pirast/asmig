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
import atongmu.util.*;
import atongmu.type.*;
import atongmu.value.*;
import atongmu.atg.StrUnv;
import atongmu.ast.visitor.AbstractVisitor;

public final class StrVar extends Expression{
	private int len;
	private char chars[];
	private Var vars[];	
	private StringType type;
	private static int charCount=0;
	private String longname;
	private String shortname;

	public StrVar(int bound, String n){
		len = bound;
		chars = new char[len];
		vars = new Var[len];
		type = new StringType();
		longname = n;
		init(n);
	}
	
	public StrVar(Var...v){
		len = v.length;
		vars = new Var[len];
		for (int i=0;i<v.length;i++)
			vars[i]=v[i];
	}

	public int length(){return len;}
	public char getChar(int i){return chars[i];}
	public Var getVar(int j){
		if (j<0 || j>=len) throw new FormulaException("string: index is out of bound.");
		return vars[j];
	}
	public Var[] getVars(){return vars;}
	public boolean isValid(){return chars!=null;}
	public boolean isEmpty(){return len==0;}
	public StringType getType(){return type;}
	public void setShortName(String name){shortname=name;}
	public String getShortName(){return shortname;}
	/* evaluate to an array of vars, the prefix must be different for each var */
	private void init(String prefix){
		for (int i=0;i<len;i++){
			vars[i] = new Var(prefix+charCount++,new IntType());
			ConstDecl c = new ConstDecl(vars[i]);
		}
	}
	public boolean isStringType(){return true;}
	public static int totalChars(){return charCount;}
	public void accept(AbstractVisitor visitor){visitor.visit(this);}/* do nothing about it */
	public String toValue(){
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<vars.length;i++){
			IntValue v = (IntValue) vars[i].getValue();
			if (v.getValue()==StrUnv.TCHAR) return sb.toString();
			sb.append(StrUnv.get(v.getValue()));
		}
		return sb.toString();
	}
	public Expression formula(){
		if (vars.length==0) {System.err.println("Warning, string ["+longname+"] has size of zero."); return null;}
		return FOFormula.Range(0,StrUnv.size()-1,vars);
	}
	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<vars.length;i++)
			sb.append(getVar(i)+",");
		return sb.toString();
	}
}
