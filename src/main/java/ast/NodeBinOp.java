package ast;

public class NodeBinOp extends NodeExpr {

    private LangOper op;
    private NodeExpr left, right;

    public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public LangOper getOp() {
        return this.op;
    }

    public NodeExpr getLeft() {
        return this.left;
    }

    public NodeExpr getRight() {
        return this.right;
    }

    @Override
    public String toString() {
        String toString = "";
        
        if (this.left != null)
            toString += this.left.toString();

        if (this.op != null)
            toString += "<" + this.op.toString() + ">";

        if (this.right != null)
            toString += this.right.toString();

        return toString;
    }
}
