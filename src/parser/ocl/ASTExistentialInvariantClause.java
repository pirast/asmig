package parser.ocl;

import java.util.List;
import org.antlr.runtime.Token;
import parser.ocl.visitor.PrintVisitor;
import parser.ocl.visitor.ReturnVisitor;
import atongmu.ast.Expression;
public class ASTExistentialInvariantClause extends ASTInvariantClause {

	public ASTExistentialInvariantClause(Token name, ASTExpression e) {
		super(name, e);
	}

	public void accept(PrintVisitor v){
		v.visit(this);
	}

	public <E,LN,LE,V> LE accept(ReturnVisitor<E,LN,LE,V> v){
		return v.visit(this);
	}

}
