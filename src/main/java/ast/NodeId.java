package ast;

import token.Token;

public class NodeId extends NodeAST {

    private String value;

    public NodeId(String value) {
        this.value = value;
    }

    public NodeId(Token token) {
        this.value = token.getValore();
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "<ID, " + this.value + ">";
    }
}
