package ast;

import token.TokenType;
import visitor.IVisitor;

/**
 * @author Palmieri Matteo
 */
public class NodeAssign extends NodeStm {

    private NodeId id;
    private NodeExpr expr;
    private TokenType operator;

    public NodeAssign(TokenType operator, NodeId id, NodeExpr expr) {
        this.id = id;
        this.expr = expr;
        this.operator = operator;
    }

    public NodeId getId() {
        return this.id;
    }

    public NodeExpr getExpr() {
        return this.expr;
    }

    public TokenType getTokenType() {
        return this.operator;
    }

	public void setExpr(NodeExpr expr) {
		this.expr = expr;
	}

    @Override
    public String toString() {
        return this.id.toString() + "<" + this.operator + ">" + this.expr.toString();
    }

	@Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
