package ast;

import visitor.IVisitor;

/**
 * @author Palmieri Matteo
 */
public abstract class NodeAST {

	private TipoTD resType;

	public TipoTD getResType() {
		return this.resType;
	}

	public void setResType(TipoTD resType) {
		this.resType = resType;
	}

	public abstract void accept(IVisitor visitor);
}
