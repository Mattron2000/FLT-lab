package ast;

import java.util.ArrayList;

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
}
