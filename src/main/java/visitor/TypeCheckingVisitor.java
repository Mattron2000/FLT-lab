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
import ast.TipoTD;
import symbolTable.Attributes;
import symbolTable.SymbolTable;

public class TypeCheckingVisitor implements IVisitor {

	private StringBuilder log;

	public TypeCheckingVisitor() {
		this.log = new StringBuilder();
		SymbolTable.init();
	}

	public String getLog() {
		return this.log.toString();
	}

	private NodeExpr convert(NodeExpr node) {
		NodeExpr expr = new NodeConvert(node);
		expr.setResType(TipoTD.FLOAT);

		return expr;
	}

	@Override
	public void visit(NodeAssign node) {
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
	public void visit(NodeBinOp node) {
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
	public void visit(NodeDcl node) {
		NodeId id = node.getId();
		NodeExpr expr = node.getExpr();

		id.accept(this);
		if (expr != null)
			expr.accept(this);

		// cerca il NodeId nella Symbol Table
		// se lo trovi assegna a resType ERRORE e aggiungi al log l’indicazione che
		// l’identificatore e’ gia’ definito
		if (id.getResType() == TipoTD.INT || id.getResType() == TipoTD.FLOAT) {
			node.setResType(TipoTD.ERROR);
			if (!this.log.toString().equals(""))
				this.log.append(", ");
			this.log.append("NodeDcl: l'ID '" + id.getValue() + "' é giá definito");
		} else { // altrimenti inserisci l’identificatore nella Symbol Table con associato il suo
					// tipo
			Attributes attr = new Attributes(node.getType());
			id.setDefinition(attr);
			SymbolTable.enter(id.getValue(), attr);
			node.setResType(TipoTD.OK);
		}
	}

	@Override
	public void visit(NodeDefer node) {
		NodeId id = node.getId();

		id.accept(this);

		node.setResType(id.getResType());

		if(node.getResType() == TipoTD.ERROR){
			if (!this.log.toString().equals(""))
				this.log.append(", ");
			this.log.append("NodeDefer: l'ID '" + id.getValue() + "' non é definito");
		}
	}

	@Override
	public void visit(NodeId node) {
		// cerca il NodeId nella Symbol Table se non lo trovi assegna a resType ERROR e
		// aggiungi al log l’indicazione che l’identificatore non e’ definito
		if (SymbolTable.lookup(node.getValue()) == null) {
			node.setResType(TipoTD.ERROR);
			if (!this.log.toString().equals(""))
				this.log.append(", ");
			this.log.append("NodeId: l'ID '" + node.getValue() + "' non é definito");
		} else { // altrimenti setta resType al tipo dell’identificatore e il campo definition
					// alla entry della symbol table.
			node.setDefinition(SymbolTable.lookup(node.getValue()));
			switch (SymbolTable.lookup(node.getValue()).getType()) {
				case INT:
					node.setResType(TipoTD.INT);
					break;
				case FLOAT:
					node.setResType(TipoTD.FLOAT);
					break;
			}
		}
	}

	@Override
	public void visit(NodePrg node) {
		node.setResType(TipoTD.OK);

		for (NodeDSs nodeDSs : node.getDSsList()) {
			nodeDSs.accept(this);

			if (nodeDSs.getResType() == TipoTD.ERROR)
				node.setResType(TipoTD.ERROR);
		}
	}

	@Override
	public void visit(NodePrint node) {
		NodeId id = node.getId();

		id.accept(this);

		if (id.getResType() == TipoTD.ERROR) {
			node.setResType(TipoTD.ERROR);
			if (!this.log.toString().equals(""))
				this.log.append(", ");
			this.log.append("PRINT: l'ID '" + id.getValue() + "' non é definito");
		} else
			node.setResType(TipoTD.OK);
	}

	@Override
	public void visit(NodeConvert node) {
		// NodeExpr expr = node.getExpr();

		// expr.accept(this);

		// if (expr.getResType() == TipoTD.ERROR)
		// node.setResType(TipoTD.ERROR);
		// else
		// node.setResType(TipoTD.FLOAT);
	}
}
