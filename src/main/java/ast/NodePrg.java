package ast;

import java.util.ArrayList;

import visitor.IVisitor;

/**
 * @author Palmieri Matteo
 */
public class NodePrg extends NodeAST {

    private ArrayList<NodeDSs> dSsList;

    public NodePrg(ArrayList<NodeDSs> dSsList) {
        this.dSsList = dSsList;
    }

    public ArrayList<NodeDSs> getDSsList() {
        return dSsList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (NodeDSs nodeDSs : this.dSsList)
            sb.append(nodeDSs.toString()).append('\n');

        return sb.toString();
    }

	@Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
