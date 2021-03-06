package parser.ocl;
import parser.ocl.visitor.PrintVisitor;
import parser.ocl.visitor.ReturnVisitor;
import atongmu.ast.Expression;

public class ASTUndefinedLiteral extends ASTExpression {
    private ASTType fType;

    public ASTUndefinedLiteral(ASTType t) {
        fType = t;
    }

    public ASTUndefinedLiteral() {
        fType = null;
    }

	public void accept(PrintVisitor v){
		v.visit(this);
	}

	public <E,LN,LE,V> E accept(ReturnVisitor<E,LN,LE,V> v){
		return v.visit(this);
	}

}
