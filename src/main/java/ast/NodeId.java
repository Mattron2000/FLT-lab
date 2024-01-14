package ast;

import symbolTable.Attributes;
import visitor.IVisitor;
import visitor.SemanticException;

/**
 * @author Palmieri Matteo
 */
public class NodeId extends NodeAST {

    private String value;
	private Attributes attribute;

    public NodeId(String value) {
        this.value = value;
		this.attribute = null;
    }

	public NodeId(String value, Attributes attribute) {
        this.value = value;
        this.attribute = attribute;
    }

    public String getValue() {
		return this.value;
    }

	public Attributes getAttributes() {
		return this.attribute;
    }

	public void setAttribute(Attributes attribute) {
		this.attribute = attribute;
	}

    @Override
    public String toString() {
        return "<ID, " + this.value + ">";
    }

    @Override
    public void accept(IVisitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
