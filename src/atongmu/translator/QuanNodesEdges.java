/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++Written by: Hao Wu++++++++++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 *
 *	This is a part of my PhD work.
 *  haowu@cs.nuim.ie
 *  JUL-2013 
 *  
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * ++++++++++++++++++++++++++++++Do or do not, there is no try.+++++++++++++++++++++++++
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package atongmu.translator;

import java.util.*;
import org.eclipse.emf.ecore.*;
import atongmu.ast.Argument;
import atongmu.ast.BinaryExpression;
import atongmu.ast.Constant;
import atongmu.ast.ConstDecl;
import atongmu.ast.Declaration;
import atongmu.ast.FunExpression;
import atongmu.ast.NegFun;
import atongmu.ast.Var;
import atongmu.ast.NumLiteral;
import atongmu.ast.BoolLiteral;
import atongmu.ast.ArithmeticExpression;
import atongmu.ast.Connective;
import atongmu.ast.Arithmetic;
import atongmu.ast.FOFormula;
import atongmu.ast.StrVar;
import atongmu.ast.Expression;
import atongmu.type.BoolType;
import atongmu.type.IntType;
import atongmu.type.EnumType;
import atongmu.type.Type;
import atongmu.atg.GraphNode;
import atongmu.atg.Edge;
import atongmu.atg.DataNode;
import atongmu.atg.PrimitiveType;

/** 
 *  Specifying a particular number of nodes and edges. For example,
 *	A graph with Mccabe Complexity of K
 *  K = N - E + 2 * P
 ***************************************************************************************************
 *  The generation process consists of two phases.
 *  In phase 1, use mccabe.smt2 to solve the constraints.
 *  Two numbers E and N are decided in phase 1. P is always set to 1.
 *   
 *  This is phase 2: Generating formulas for a graph that matches right number of edges based on
 *  the nodes decided in phase 1.
 *  E: indicates the total number of edges.
 *	nodes: indicates total number of graph nodes to be included.
 *  ref: is a reflexive association (undirectional) with multiplicites 0..*.
 ****************************************************************************************************
 */

public final class QuanNodesEdges{

	private a2f modelTranslator;

	public QuanNodesEdges(a2f translator){
		if (translator==null) throw new TranslatorException("Error: model translator cannot be null.");
		this.modelTranslator= translator;
	}

	/**
     * Generate a formula that captures the meaning of K. 
     * NOTE: the nodes here are only those to be included in the graph. 
     * Other nodes have to be switched off before calling this method.
	 * This only applies to a reflexive unidirectional association.
     * @TODO: extend this method to other type of associations. 
	 * But ideally it should have a nice distribution of nodes and edges going around in the graph. 
     */
	public Expression generate(String ref, int E, List<GraphNode> nodes){
		if (E<0) throw new TranslatorException("Error: impossible to generate this graph, please check your E.");
		if (nodes==null) throw new TranslatorException("Error: grah nodes cannot be null.");
		if (nodes.size()==0) throw new TranslatorException("Error: no graph nodes have been specified.");
		
		Var m[][] = modelTranslator.getRefByName(ref);		
		if (m.length!=m[0].length) throw new TranslatorException("Error: this array [" + ref +"] has to be square.");
		Var v[][] = new Var[m.length][m[0].length];
		List<Var> edges = new ArrayList<Var>();
		List<Var> quan = new ArrayList<Var>();
		List<Var> noquan = new ArrayList<Var>();
		HashMap <Var,Var> map = new HashMap<Var, Var>();
		int count=1;
		
		/* For every entry, create a quantity variable v.
		 * Limit their range in 0 and 1.
 		 * Generate formulas to capture the meaning of 0 and 1. 	
		 */
		for (int i=0;i<m.length;i++){
			for (int j=0;j<m[0].length;j++){
				v[i][j] = new Var("_cabcom"+count++,new IntType());
				ConstDecl c = new ConstDecl(v[i][j]);
				map.put(m[i][j], v[i][j]);
				modelTranslator.getFormula().addExpression(FOFormula.Range(0,1,v[i][j]));				
				modelTranslator.getFormula().addExpression(new BinaryExpression(Connective.IMPLIES,
											new ArithmeticExpression(Arithmetic.EQUAL,v[i][j],new NumLiteral(1))
											,m[i][j]));
				modelTranslator.getFormula().addExpression(new BinaryExpression(Connective.IMPLIES,
											new ArithmeticExpression(Arithmetic.EQUAL,v[i][j],new NumLiteral(0))
											,new NegFun(m[i][j])));
			}
		}

		for (int i=0;i<v.length;i++)
			modelTranslator.getFormula().addExpression(
				new ArithmeticExpression(Arithmetic.EQUAL,v[i][v[0].length-i-1], new NumLiteral(0)));

		boolean flag1=false, flag2=false,flag3=false;
		for (Edge e : modelTranslator.getEdges(ref)){
			GraphNode src = (GraphNode)e.source();	//cast is safe here because this has to be a graph node.
			GraphNode dst = (GraphNode)e.dest();	//cast is safe here because this has to be a graph node.
			if (src==dst) continue;
			flag1=false;flag2=false;
			for (int i=0;i<nodes.size();i++)
				if (src==nodes.get(i)) {flag1=true; break;}
								
			for (int i=0;i<nodes.size();i++)	
				if (dst==nodes.get(i)) {flag2=true; break;}
			
			if (flag1 && flag2)
				edges.add(modelTranslator.getLinks().get(e));
		}

		/* Now get those quantity variables. */
		for (int i=0;i<edges.size();i++)
			quan.add(map.get(edges.get(i)));
		for (int i=0;i<v.length;i++){
			for (int j=0;j<v[i].length;j++){
				flag3=false;
				for (int k=0;k<quan.size();k++)
					if (v[i][j]==quan.get(k)){flag3=true;break;}

				if (!flag3) {noquan.add(v[i][j]);}
			}
		}
		
		modelTranslator.getFormula().addExpression(new ArithmeticExpression(
			Arithmetic.EQUAL, FOFormula.join(Arithmetic.PLUS,noquan), new NumLiteral(0)));

		/* display the warning message */
		if (quan.size()<=1 && E > 1)
			System.err.println("Warning: I think the formula will not be satisfied, and you can try it anyway.");

		return (quan.size()==1) ? 
			new ArithmeticExpression(Arithmetic.EQUAL,quan.get(0),new NumLiteral(E))
			:
			new ArithmeticExpression(Arithmetic.EQUAL,FOFormula.join(Arithmetic.PLUS,quan), new NumLiteral(E));

	}
}

