package ast;

import visitor.IVisitor;
import visitor.SemanticException;

/**
 * @author Palmieri Matteo
 */
public class NodePrint extends NodeStm {

    private NodeId id;

    public NodePrint(NodeId id) {
        this.id = id;
    }

    public NodeId getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "<PRINT>" + this.id.toString();
    }
	
    @Override
    public void accept(IVisitor visitor) throws SemanticException {
        visitor.visit(this);
    }
}
