package ast;

import visitor.IVisitor;

/**
 * @author Palmieri Matteo
 */
public class NodeId extends NodeAST {

    private String value;

	public NodeId(String value) {
        this.value = value;
    }

    public String getValue() {
		return this.value;
    }

    @Override
    public String toString() {
        return "<ID, " + this.value + ">";
    }

    @Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
