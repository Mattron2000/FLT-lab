package ast;

import visitor.IVisitor;
import visitor.SemanticException;
import visitor.TipoTD;

/**
 * @author Palmieri Matteo
 */
public abstract class NodeAST {

    protected TipoTD resType;

	public abstract void accept(IVisitor visitor) throws SemanticException;

	public TipoTD getResType() {
        return this.resType;
    }

    public void setResType(TipoTD resType) {
        this.resType = resType;
    }
}
