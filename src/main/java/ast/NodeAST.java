package ast;

import visitor.IVisitor;
import visitor.TypeDescriptor;

/**
 * @author Palmieri Matteo
 */
public abstract class NodeAST {

	private TypeDescriptor type;
	public String code;

	public TypeDescriptor getResType() {
		return this.type;
	}

	public void setResType(TypeDescriptor type) {
		this.type = type;
	}

	public String getCodice() {
		return this.code;
	}

	public void setCodice(String codice) {
		this.code = codice;
	}

	public abstract void accept(IVisitor visitor);
}
