package ast;

import symbolTable.Attributes;

import visitor.IVisitor;

/**
 * @author Palmieri Matteo
 */
public class NodeId extends NodeAST {

    private String value;
	private Attributes definition;

    public NodeId(String value) {
        this.value = value;
		this.definition = null;
    }

	public NodeId(String value, Attributes definition) {
        this.value = value;
        this.definition = definition;
    }

    public String getValue() {
		return this.value;
    }

	public Attributes getDefinition() {
		return this.definition;
    }

	public void setDefinition(Attributes definition) {
		this.definition = definition;
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
