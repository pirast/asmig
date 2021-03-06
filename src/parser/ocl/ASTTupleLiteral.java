package parser.ocl;

import java.util.Iterator;
import java.util.List;
import parser.ocl.visitor.PrintVisitor;
import parser.ocl.visitor.ReturnVisitor;
import atongmu.ast.Expression;

public class ASTTupleLiteral extends ASTExpression {
    private List fItems;    // (ASTTupleItem)

    public ASTTupleLiteral(List items) {
        fItems = items;
    }

	public void accept(PrintVisitor v){
		v.visit(this);
	}

	public <E,LN,LE,V> E accept(ReturnVisitor<E,LN,LE,V> v){
		return v.visit(this);
	}

}
