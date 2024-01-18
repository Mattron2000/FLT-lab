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
import ast.TypeDescriptor;
import symbolTable.Attributes;
import symbolTable.SymbolTable;

public class TypeCheckingVisitor extends IVisitor {

	public TypeCheckingVisitor() {
		super();
		SymbolTable.init();
	}

	public String getLog() {
		return this.log.toString();
	}

	private void setLog(String string) {
		if (!this.log.toString().equals(""))
			this.log.append(", ");
		this.log.append(string);
	}

	private NodeExpr convert(NodeExpr node) {
		NodeExpr expr = new NodeConvert(node);
		expr.setResType(TypeDescriptor.FLOAT);

		return expr;
	}

	@Override
	public void visit(NodeAssign node) {
		NodeId id = node.getId();
		NodeExpr expr = node.getExpr();

		id.accept(this);
		expr.accept(this);

		if (id.getResType().equals(expr.getResType())) {
			node.setResType(TypeDescriptor.OK);
			return;
		}

		if ((id.getResType() == TypeDescriptor.FLOAT && expr.getResType() == TypeDescriptor.INT)) {
			node.setExpr(this.convert(expr));
			node.setResType(TypeDescriptor.OK);
			return;
		}

		node.setResType(TypeDescriptor.ERROR);

		if (id.getResType() == TypeDescriptor.INT && expr.getResType() == TypeDescriptor.FLOAT)
			setLog("NodeAssign: l'ID '" + id.toString() + "' di tipo '" + id.getResType()
					+ "' non é compatibile con l'Expr '" + expr.toString() + "'");
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

		if (exprLeft.getResType() == TypeDescriptor.INT && exprRight.getResType() == TypeDescriptor.FLOAT) {
			node.setLeft(this.convert(exprLeft));
			node.setResType(TypeDescriptor.FLOAT);
			return;
		}

		if (exprLeft.getResType() == TypeDescriptor.FLOAT && exprRight.getResType() == TypeDescriptor.INT) {
			node.setRight(this.convert(exprRight));
			node.setResType(TypeDescriptor.FLOAT);
			return;
		}

		node.setResType(TypeDescriptor.ERROR);
	}

	@Override
	public void visit(NodeConst node) {
		switch (node.getLangType()) {
			case INT:
				node.setResType(TypeDescriptor.INT);
				break;
			case FLOAT:
				node.setResType(TypeDescriptor.FLOAT);
				break;
			default:
				node.setResType(TypeDescriptor.ERROR);
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
		if (id.getResType() == TypeDescriptor.INT || id.getResType() == TypeDescriptor.FLOAT) {
			node.setResType(TypeDescriptor.ERROR);
			setLog("NodeDcl: l'ID '" + id.getValue() + "' é giá definito");
			return;
		}
		// altrimenti inserisci l’identificatore nella Symbol Table con associato il suo
		// tipo
		Attributes attr = new Attributes(node.getType());
		id.setDefinition(attr);

		switch (attr.getType()) {
			case FLOAT:
				id.setResType(TypeDescriptor.FLOAT);
				break;
			case INT:
				id.setResType(TypeDescriptor.INT);
		}

		SymbolTable.enter(id.getValue(), attr);
		node.setResType(TypeDescriptor.OK);

		if (expr == null || (id.getResType() == expr.getResType() && id.getResType() != TypeDescriptor.ERROR))
			return;

		if (id.getResType() == TypeDescriptor.FLOAT && expr.getResType() == TypeDescriptor.INT) {
			node.setExpr(this.convert(expr));
			return;
		}

		node.setResType(TypeDescriptor.ERROR);

		setLog("NodeDcl: l'ID '" + id.toString() + "' di tipo '" + id.getResType()
				+ "' non é compatibile con l'Expr '" + expr.toString() + "'");
	}

	@Override
	public void visit(NodeDefer node) {
		NodeId id = node.getId();

		id.accept(this);

		node.setResType(id.getResType());

		if (node.getResType() == TypeDescriptor.ERROR)
			setLog("NodeDefer: l'ID '" + id.getValue() + "' non é definito");
	}

	@Override
	public void visit(NodeId node) {
		// cerca il NodeId nella Symbol Table se non lo trovi assegna a resType ERROR e
		// aggiungi al log l’indicazione che l’identificatore non e’ definito
		if (SymbolTable.lookup(node.getValue()) == null) {
			node.setResType(TypeDescriptor.ERROR);
			setLog("NodeId: l'ID '" + node.getValue() + "' non é definito");
		} else { // altrimenti setta resType al tipo dell’identificatore e il campo definition
					// alla entry della symbol table.
			node.setDefinition(SymbolTable.lookup(node.getValue()));
			switch (SymbolTable.lookup(node.getValue()).getType()) {
				case INT:
					node.setResType(TypeDescriptor.INT);
					break;
				case FLOAT:
					node.setResType(TypeDescriptor.FLOAT);
					break;
			}
		}
	}

	@Override
	public void visit(NodePrg node) {
		node.setResType(TypeDescriptor.OK);

		for (NodeDSs nodeDSs : node.getDSsList()) {
			nodeDSs.accept(this);

			if (nodeDSs.getResType() == TypeDescriptor.ERROR)
				node.setResType(TypeDescriptor.ERROR);
		}
	}

	@Override
	public void visit(NodePrint node) {
		NodeId id = node.getId();

		id.accept(this);

		if (id.getResType() == TypeDescriptor.ERROR) {
			node.setResType(TypeDescriptor.ERROR);
			setLog("PRINT: l'ID '" + id.getValue() + "' non é definito");
		} else
			node.setResType(TypeDescriptor.OK);
	}

	@Override
	public void visit(NodeConvert node) {
		NodeExpr expr = node.getExpr();

		expr.accept(this);

		if (expr.getResType() == TypeDescriptor.ERROR)
			node.setResType(TypeDescriptor.ERROR);
		else
			node.setResType(TypeDescriptor.FLOAT);
	}
}
