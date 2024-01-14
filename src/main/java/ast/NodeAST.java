package ast;

import visitor.IVisitor;

/**
 * @author Palmieri Matteo
 */
public abstract class NodeAST {

	public abstract void accept(IVisitor visitor);

}
