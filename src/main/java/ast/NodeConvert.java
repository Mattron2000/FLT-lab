package ast;

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
		return this.node.toString();
	}
}
