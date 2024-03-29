package ast;

import token.TokenType;
import visitor.IVisitor;

/**
 * @author Palmieri Matteo
 */
public class NodeBinOp extends NodeExpr {

    private TokenType op;
    private NodeExpr left, right;

    public NodeBinOp(TokenType op, NodeExpr left, NodeExpr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public TokenType getOp() {
        return this.op;
    }

    public NodeExpr getLeft() {
        return this.left;
    }
	
	public void setLeft(NodeExpr left) {
		this.left = left;
	}

    public NodeExpr getRight() {
        return this.right;
    }

	public void setRight(NodeExpr right) {
		this.right = right;
	}

    @Override
    public String toString() {
        String toString = "";
        
        if (this.left != null)
            toString += this.left.toString();

        if (this.op != null)
            toString += "<" + this.op.toString() + ">";

        if (this.right != null)
            toString += this.right.toString();

        return toString;
    }

	@Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
