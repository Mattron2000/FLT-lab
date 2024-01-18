package visitor;

import ast.LangType;
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
import ast.TypeDescriptor;
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

		node.setCodice(node.getLeft().getCodice() + " " + node.getRight().getCodice()
				+ getCodiceDcBinOp(node.getOp()));
	}

	@Override
	public void visit(NodeConst node) {
		node.accept(this);

		if (node.getLangType() == LangType.FLOAT)
			node.setCodice(node.getValue() + " 5 k");
		else
			node.setCodice(node.getValue() + " 0 k");
	}

	@Override
	public void visit(NodeConvert node) {
		node.getExpr().accept(this);
		node.setCodice(node.getExpr().getCodice() + " 5 k");
	}

	@Override
	public void visit(NodeDcl node) {
		node.getId().getDefinition().setRegister(Register.newRegister());

        if (node.getExpr() != null) {
            node.getId().accept(this);
            node.getExpr().accept(this);

            node.setCodice(node.getExpr().getCodice() + " s" + node.getId().getCodice());
        }
	}

	@Override
	public void visit(NodeDefer node) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(NodeId node) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(NodePrg nodePrg) {
		StringBuffer codiceDc = new StringBuffer(); // mantiene il codice della visita

		if (nodePrg.getResType() != TypeDescriptor.OK) {
			nodePrg.setCodice(nodePrg.getResType().toString());
			return;
		}

		for (NodeDSs nodeDSs : nodePrg.getDSsList()) {
			nodeDSs.accept(this);

			if (nodeDSs.getCodice() == null) {
				nodePrg.setCodice("ERRORE: nodeDSs con codice null");
				return;
			}

			codiceDc.append(nodeDSs.getCodice() + " ");
		}

		nodePrg.setCodice(codiceDc.toString().trim());
	}

	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		node.setCodice("l" + node.getId().getDefinition().getRegister() + " p P");
	}
}
