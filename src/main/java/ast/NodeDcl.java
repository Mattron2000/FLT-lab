package ast;

public class NodeDcl extends NodeDSs {

    private NodeId id;
    private LangType type;
    private NodeExpr expr;

    public NodeDcl(NodeId id, LangType type, NodeExpr expr) {
        this.id = id;
        this.type = type;
        this.expr = expr;
    }

    public NodeId getId() {
        return this.id;
    }

    public LangType getType() {
        return this.type;
    }

    public NodeExpr getexpr() {
        return this.expr;
    }

    @Override
    public String toString() {
        String toString = "<" + this.type + "_KW>" + this.id.toString();

        if (this.expr != null)
            toString += this.expr.toString();

        return toString;
    }
}
