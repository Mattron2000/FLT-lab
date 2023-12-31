package ast;

public class NodeAssign extends NodeStm {

    private NodeId id;
    private NodeExpr expr;

    public NodeAssign(NodeId id, NodeExpr expr) {
        this.id = id;
        this.expr = expr;
    }

    public NodeId getId() {
        return this.id;
    }

    public NodeExpr getExpr() {
        return this.expr;
    }

    @Override
    public String toString() {
        return this.id.toString() + "<ASSIGN>" + this.expr.toString();
    }
}
