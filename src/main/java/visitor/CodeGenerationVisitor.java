package visitor;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConst;
import ast.NodeConvert;
import ast.NodeDSs;
import ast.NodeDcl;
import ast.NodeDefer;
import ast.NodeId;
import ast.NodePrg;
import ast.NodePrint;

import symbolTable.SymbolTable;

import token.TokenType;

/**
 * @author Palmieri Matteo
 * 
 *         LISTA COMANDI
 *         - sa : pop dello stack e lo mette nel registro a
 *         - la : push sullo stack dal registro a
 *         - p : stampa su terminale il valore in cima allo stack
 *         - P : scarta il valore in cima allo stack facendo pop
 *         - x k : indica che il valore in cima allo stack avrá x cifre decimali
 */
public class CodeGenerationVisitor extends IVisitor {

	public CodeGenerationVisitor() {
		super();
		SymbolTable.init();
		Register.init();
	}

	private String getCodiceDcBinOp(TokenType op) {
		switch (op) {
			case PLUS:
				return "+";
			case MINUS:
				return "-";
			case MULT:
				return "*";
			case DIVIDE:
				return "/";
			default:
				return "ERRORE OPERATORE";
		}
	}

	@Override
	public void visit(NodeAssign node) {
		node.getId().accept(this);
		node.getExpr().accept(this);

		node.setCodice(node.getExpr().getCodice() + " s" + node.getId().getCodice() + " 0 k");
	}

	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		node.getRight().accept(this);

		node.setCodice(node.getLeft().getCodice() +
				" " + node.getRight().getCodice() +
				" " + getCodiceDcBinOp(node.getOp()));
	}

	@Override
	public void visit(NodeConst node) {
		node.setCodice(node.getValue());
	}

	@Override
	public void visit(NodeConvert node) {
		node.getExpr().accept(this);
		node.setCodice(node.getExpr().getCodice() + " 5 k");
	}

	@Override
	public void visit(NodeDcl node) {
		node.getId().getDefinition().setType(node.getType());
		node.getId().accept(this);

		if (node.getExpr() == null) {
			node.setCodice("");
			return;
		}

		node.getExpr().accept(this);

		switch (node.getId().getDefinition().getType()) {
			case FLOAT:
				node.setCodice(node.getExpr().getCodice() + " 5 k s" + node.getId().getCodice() + " 0 k");
				break;
			case INT:
				node.setCodice(node.getExpr().getCodice() + " s" + node.getId().getCodice() + " 0 k");
				break;
		}
	}

	@Override
	public void visit(NodeDefer node) {
		node.getId().accept(this);
		node.setCodice("l" + node.getId().getCodice());
	}

	@Override
	public void visit(NodeId node) {
		if (SymbolTable.lookup(node.getValue()) == null) {
			node.getDefinition().setRegister(Register.newRegister());

			SymbolTable.enter(node.getValue(), node.getDefinition());
		} else
			node.setDefinition(SymbolTable.lookup(node.getValue()));

		node.setCodice(node.getDefinition().getRegister().toString());
	}

	@Override
	public void visit(NodePrg nodePrg) {
		for (NodeDSs nodeDSs : nodePrg.getDSsList()) {
			nodeDSs.accept(this);
			if (!nodeDSs.getCodice().equals(""))
				setLog(nodeDSs.getCodice());
		}
		nodePrg.setCodice(this.log.toString());
	}

	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		node.setCodice("l" + node.getId().getCodice() + " p P 0 k");
	}
}
