package ast;

import token.Token;

public class NodeConst extends NodeExpr {

    private String value;
    private LangType type;

    public NodeConst(String value, LangType type) {
        this.value = value;
        this.type = type;
    }

    public NodeConst(Token match) {
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
}
