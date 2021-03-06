package parser.ocl;
import org.antlr.runtime.Token;
import parser.ocl.visitor.PrintVisitor;
import parser.ocl.visitor.ReturnVisitor;
import atongmu.ast.Expression;

public class ASTIntegerLiteral extends ASTExpression {
    private int fValue;

    public ASTIntegerLiteral(Token token) {
        fValue = Integer.parseInt(token.getText());
    }

    public String toString() {
        return "ASTIntergerLiteral:" +Integer.toString(fValue);
    }
	
	public int value(){return fValue;}
	
	public void accept(PrintVisitor v){
		v.visit(this);
	}

	public <E,LN,LE,V> E accept(ReturnVisitor<E,LN,LE,V> v){
		return v.visit(this);
	}

}
