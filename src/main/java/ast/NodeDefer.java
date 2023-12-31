package ast;

public class NodeDefer extends NodeExpr {

    private NodeId id;

    public NodeDefer(NodeId id) {
        this.id = id;
    }

    public NodeId getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "<DEFER, " + this.id.getValue() + ">";
    }
}
