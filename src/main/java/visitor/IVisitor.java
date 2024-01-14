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

public abstract interface IVisitor {
    public abstract void visit(NodeAssign node) throws SemanticException;
    public abstract void visit(NodeBinOp node) throws SemanticException;
    public abstract void visit(NodeConst node);
    public abstract void visit(NodeDcl node) throws SemanticException;
    public abstract void visit(NodeDefer node) throws SemanticException;
    public abstract void visit(NodeId node) throws SemanticException;
    public abstract void visit(NodePrg node) throws SemanticException;
    public abstract void visit(NodePrint node) throws SemanticException;
    public abstract void visit(NodeConvert node) throws SemanticException;
}
