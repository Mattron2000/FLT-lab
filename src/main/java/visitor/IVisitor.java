package visitor;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConst;
import ast.NodeConvert;
import ast.NodeDcl;
import ast.NodeDefer;
import ast.NodeId;
import ast.NodePrg;
import ast.NodePrint;

public abstract class IVisitor {
	
	protected StringBuilder log; // per l’eventuale errore nella generazione del codice

	protected IVisitor() {
		this.log = new StringBuilder(); 
	}

	public String getLog() {
		return this.log.toString();
	}

	protected void setLog(String string) {
		if (!this.log.toString().equals(""))
			this.log.append(", ");
		this.log.append(string);
	}

    public abstract void visit(NodeAssign node);
    public abstract void visit(NodeBinOp node);
    public abstract void visit(NodeConst node);
    public abstract void visit(NodeDcl node);
    public abstract void visit(NodeDefer node);
    public abstract void visit(NodeId node);
    public abstract void visit(NodePrg node);
    public abstract void visit(NodePrint node);
    public abstract void visit(NodeConvert node);
}
