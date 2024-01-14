package visitor;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConst;
import ast.NodeConvert;
import ast.NodeDSs;
import ast.NodeDcl;
import ast.NodeDefer;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrg;
import ast.NodePrint;
import symbolTable.Attributes;
import symbolTable.SymbolTable;

public class TypeCheckingVisitor implements IVisitor {

	TipoTD resType;

	public TipoTD getResType() {
		return this.resType;
	}

	private NodeExpr convert(NodeExpr node) {
		NodeExpr expr = new NodeConvert(node);
		expr.setResType(TipoTD.FLOAT);

		return expr;
	}

	@Override
	public void visit(NodeAssign node) throws SemanticException {
		NodeId id = node.getId();
		NodeExpr expr = node.getExpr();

		id.accept(this);
		expr.accept(this);

		if ((id.getResType() == TipoTD.INT && expr.getResType() == TipoTD.INT) ||
				(id.getResType() == TipoTD.FLOAT && expr.getResType() == TipoTD.FLOAT)) {
			node.setResType(TipoTD.OK);
			return;
		}

		if ((id.getResType() == TipoTD.FLOAT && expr.getResType() == TipoTD.INT)) {
			node.setExpr(this.convert(expr));
			node.setResType(TipoTD.OK);
			return;
		}

		node.setResType(TipoTD.ERROR);
	}

	@Override
	public void visit(NodeBinOp node) throws SemanticException {
		NodeExpr exprLeft = node.getLeft();
		NodeExpr exprRight = node.getRight();

		exprLeft.accept(this);
		exprRight.accept(this);

		if (exprLeft.getResType().equals(exprRight.getResType())) {
			node.setResType(exprLeft.getResType());
			return;
		}

		if (exprLeft.getResType() == TipoTD.INT && exprRight.getResType() == TipoTD.FLOAT) {
			node.setLeft(this.convert(exprLeft));
			node.setResType(TipoTD.FLOAT);
			return;
		}

		if (exprLeft.getResType() == TipoTD.FLOAT && exprRight.getResType() == TipoTD.INT) {
			node.setRight(this.convert(exprRight));
			node.setResType(TipoTD.FLOAT);
			return;
		}

		node.setResType(TipoTD.ERROR);
	}

	@Override
	public void visit(NodeConst node) {
		switch (node.getLangType()) {
			case INT:
				node.setResType(TipoTD.INT);
				break;
			case FLOAT:
				node.setResType(TipoTD.FLOAT);
				break;
			default:
				node.setResType(TipoTD.ERROR);
		}
	}

	@Override
	public void visit(NodeDcl node) throws SemanticException {
		NodeId id = node.getId();
		NodeExpr expr = node.getExpr();

		id.accept(this);
		if (expr != null)
			expr.accept(this);

		if (id.getResType() == TipoTD.ERROR) {
			Attributes attribute = new Attributes(node.getType());
			id.setAttribute(attribute);
			SymbolTable.enter(id.getValue(), attribute);
			node.setResType(TipoTD.OK);
			return;
		}

		node.setResType(TipoTD.ERROR);
	}

	@Override
	public void visit(NodeDefer node) throws SemanticException {
		NodeId id = node.getId();

		id.accept(this);

		node.setResType(id.getResType());
	}

	@Override
	public void visit(NodeId node) throws SemanticException {
		if (SymbolTable.lookup(node.getValue()) == null) {
			node.setResType(TipoTD.ERROR);
			node.setAttribute(SymbolTable.lookup(node.getValue()));
			return;
		}

		switch (SymbolTable.lookup(node.getValue()).getType()) {
			case INT:
				node.setResType(TipoTD.INT);
				break;
			case FLOAT:
				node.setResType(TipoTD.FLOAT);
				break;
			default:
				throw new SemanticException("Visitor error: questo ID ha un tipo illegale, SymbolTable:" + SymbolTable.lookup(node.getValue()).getType());
		}

		node.setAttribute(SymbolTable.lookup(node.getValue()));
	}

	@Override
	public void visit(NodePrg node) throws SemanticException {
		node.setResType(TipoTD.OK);

		for (NodeDSs nodeDSs : node.getDSsList()) {
			nodeDSs.accept(this);

			if (nodeDSs.getResType() == TipoTD.ERROR)
				node.setResType(TipoTD.ERROR);
		}
	}

	@Override
	public void visit(NodePrint node) throws SemanticException {
		NodeId id = node.getId();

		id.accept(this);

		if (id.getResType() == TipoTD.INT || id.getResType() == TipoTD.FLOAT) {
			node.setResType(TipoTD.OK);
			return;
		}

		node.setResType(TipoTD.ERROR);
	}

	@Override
	public void visit(NodeConvert node) throws SemanticException {
        NodeExpr expr = node.getExpr();

        expr.accept(this);
        
        if (expr.getResType() == TipoTD.ERROR)
            node.setResType(TipoTD.ERROR);
        else 
            node.setResType(TipoTD.FLOAT);
	}
}
