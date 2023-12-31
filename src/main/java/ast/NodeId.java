package ast;

public class NodeId extends NodeAST {

    private String value;

    public NodeId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "<ID, " + this.value + ">";
    }
}
