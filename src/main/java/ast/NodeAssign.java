package ast;

import visitor.IVisitor;

/**
 * @author Palmieri Matteo
 */
public class NodeAssign extends NodeStm {

    private NodeId id;
    private NodeExpr expr;
    private LangOperAss langOperAss;

    public NodeAssign(LangOperAss langOperAss, NodeId id, NodeExpr expr) {
        this.id = id;
        this.expr = expr;
        this.langOperAss = langOperAss;
    }

    public NodeId getId() {
        return this.id;
    }

    public NodeExpr getExpr() {
        return this.expr;
    }

    public LangOperAss getLangOperAss() {
        return this.langOperAss;
    }

    @Override
    public String toString() {
        return this.id.toString() + "<" + this.langOperAss + ">" + this.expr.toString();
    }

	@Override
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
