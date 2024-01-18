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
import ast.TypeDescriptor;

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

	@Override
	public void visit(NodeAssign node) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(NodeBinOp node) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(NodeConst node) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
	}

	@Override
	public void visit(NodeConvert node) {
		node.getExpr().accept(this);
		node.setCodice(node.getExpr().getCodice() + " 5 k");
	}

	@Override
	public void visit(NodeDcl node) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'visit'");
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
