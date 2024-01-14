package ast;

import visitor.IVisitor;
import visitor.SemanticException;

/**
 * @author Palmieri Matteo
 */
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

    @Override
    public void accept(IVisitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
