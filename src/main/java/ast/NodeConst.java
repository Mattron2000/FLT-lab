package ast;

import visitor.IVisitor;

/**
 * @author Palmieri Matteo
 */
public class NodeConst extends NodeExpr {

    private String value;
    private LangType type;

    public NodeConst(String value, LangType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public LangType getLangType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "<CONST, " + this.type + ", " + this.value + ">";
    }

	@Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
