package ast;

import visitor.IVisitor;
import visitor.SemanticException;

/**
 * @author Palmieri Matteo
 */
public class NodeConvert extends NodeExpr {

	private NodeExpr node;

	public NodeConvert(NodeExpr node) {
		this.node = node;
	}

	public NodeExpr getExpr() {
		return this.node;
	}

	@Override
	public String toString() {
		return "<CONVERT," + this.node.toString() + ">";
	}

	@Override
	public void accept(IVisitor visitor) throws SemanticException {
		visitor.visit(this);
	}
}
