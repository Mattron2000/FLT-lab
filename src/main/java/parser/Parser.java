package parser;

import java.io.IOException;

import java.util.ArrayList;

import ast.LangType;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConst;
import ast.NodeDSs;
import ast.NodeDcl;
import ast.NodeDefer;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrg;
import ast.NodePrint;
import ast.NodeStm;

import scanner.LexicalException;
import scanner.Scanner;

import token.*;

/**
 * @author Palmieri Matteo
 */
public class Parser {

	private Scanner scanner;

	public Parser(Scanner scanner) {
		this.scanner = scanner;
	}

	/**
	 * controlla che il token corrente nel buffer sia del tipo previsto, se
	 * corrisponde, lo consuma e restituisce il token, altrimenti lancio l'errore
	 * 
	 * @param tokenType Tipo di Token previsto
	 * @return il token corrente (verrá consumato dal buffer)
	 * @throws LexicalException
	 * @throws SyntacticException
	 * @throws IOException
	 */
	private Token match(TokenType tokenType) throws LexicalException, SyntacticException, IOException {
		Token tk = this.scanner.peekToken();

		if (tk.getTipo().equals(tokenType))
			return this.scanner.nextToken();
		else
			throw new SyntacticException(
					"Aspettavo un token di tipo " + tokenType + " e invece ho trovato un token di tipo " + tk.getTipo()
							+ " alla riga " + tk.getRiga());
	}

	public NodePrg parse() throws SyntacticException, LexicalException, IOException {
		return this.parsePrg();
	}

	private NodePrg parsePrg() throws SyntacticException, LexicalException, IOException {
		ArrayList<NodeDSs> DSsList = new ArrayList<>();
		Token tk = this.scanner.peekToken();
		switch (tk.getTipo()) {
			case FLOAT_KW, INT_KW, ID, PRINT, EOF: // Prg -> DSs $
				// DSs
				DSsList = parseDSs(DSsList);
				// $
				match(TokenType.EOF);

				return new NodePrg(DSsList);
			default:
				throw new SyntacticException(
						"Token " + tk.toString() + " alla riga " + tk.getRiga() + " non é inizio del programma");
		}
	}

	private ArrayList<NodeDSs> parseDSs(ArrayList<NodeDSs> DSsList)
			throws SyntacticException, LexicalException, IOException {
		Token tk = this.scanner.peekToken();
		switch (tk.getTipo()) {
			case FLOAT_KW, INT_KW: // DSs -> Dcl DSs
				// Dcl
				NodeDcl nodeDcl = parseDcl();
				DSsList.add(nodeDcl);
				// DSs
				DSsList = parseDSs(DSsList);

				return DSsList;
			case ID, PRINT: // DSs -> Stm DSs
				// Stm
				NodeStm nodeStm = parseStm();
				DSsList.add(nodeStm);
				// DSs
				DSsList = parseDSs(DSsList);

				return DSsList;
			case EOF: // DSs -> ϵ
				// ϵ

				return DSsList;
			default:
				throw new SyntacticException(
						"Token " + tk.toString() + " non é il corretto primo token per un'istruzione alla riga "
								+ tk.getRiga());
		}
	}

	private NodeDcl parseDcl() throws SyntacticException, LexicalException, IOException {
		Token tk = this.scanner.peekToken();
		switch (tk.getTipo()) {
			case FLOAT_KW, INT_KW: // Dcl -> Ty id DclP
				// Ty
				LangType type = parseTy();
				// id
				NodeId id = new NodeId(match(TokenType.ID).getValore());
				// DclP
				NodeExpr expr = parseDclP();

				return new NodeDcl(id, type, expr);
			default:
				throw new SyntacticException(
						"Token " + tk.toString() + " alla riga " + tk.getRiga()
								+ " non é il token corretto per dichiarare una variabile");
		}
	}

	private NodeExpr parseDclP() throws SyntacticException, LexicalException, IOException {
		Token tk = this.scanner.peekToken();
		switch (tk.getTipo()) {
			case SEMICOLON: // DclP -> ;
				// ;
				match(TokenType.SEMICOLON);

				return null;
			case ASSIGN: // DclP -> opAss Exp ;
				// opAss
				match(TokenType.ASSIGN);
				// Exp
				NodeExpr expr = parseExpr();
				// ;
				match(TokenType.SEMICOLON);

				return expr;
			default:
				throw new SyntacticException(
						"Token " + tk.toString() + " alla riga " + tk.getRiga()
								+ " non é il tokn corretto per assegnare un valore alla variabile");
		}
	}

	private NodeStm parseStm() throws SyntacticException, LexicalException, IOException {
		Token tk = this.scanner.peekToken();
		switch (tk.getTipo()) {
			case ID: // Stm -> id = Exp ;
				// id
				NodeId id = new NodeId(match(TokenType.ID).getValore());
				// =
				match(TokenType.ASSIGN);
				// Exp
				NodeExpr expr = parseExpr();
				// ;
				match(TokenType.SEMICOLON);

				return new NodeAssign(TokenType.ASSIGN, id, expr);
			case PRINT: // Stm -> print id ;
				// print
				match(TokenType.PRINT);
				// id
				NodeId idPrint = new NodeId(match(TokenType.ID).getValore());
				// ;
				match(TokenType.SEMICOLON);

				return new NodePrint(idPrint);
			default:
				throw new SyntacticException(
						"Token " + tk.toString() + " alla riga " + tk.getRiga()
								+ " non é quello giusto per scrivere uno statement");
		}
	}

	// private TokenType parseStmOpAss() throws SyntacticException, LexicalException, IOException {
	// 	Token tk = this.scanner.peekToken();
	// 	switch (tk.getTipo()) {
	// 		case PLUS_ASSIGN: // opAss -> +=
	// 			match(TokenType.PLUS_ASSIGN);

	// 			return TokenType.PLUS_ASSIGN;
	// 		case MINUS_ASSIGN: // opAss -> -=
	// 			match(TokenType.MINUS_ASSIGN);

	// 			return TokenType.MINUS_ASSIGN;
	// 		case MULT_ASSIGN: // opAss -> *=
	// 			match(TokenType.MULT_ASSIGN);

	// 			return TokenType.MULT_ASSIGN;
	// 		case DIVIDE_ASSIGN: // opAss -> /=
	// 			match(TokenType.DIVIDE_ASSIGN);

	// 			return TokenType.DIVIDE_ASSIGN;
	// 		case ASSIGN: // opAss -> =
	// 			match(TokenType.ASSIGN);

	// 			return TokenType.ASSIGN;
	// 		default:
	// 			throw new SyntacticException(
	// 					"Token " + tk.toString() + " alla riga " + tk.getRiga()
	// 							+ " non é quello giusto per scrivere uno statement");
	// 	}
	// }

	private NodeExpr parseExpr() throws SyntacticException, LexicalException, IOException {
		Token tk = this.scanner.peekToken();
		switch (tk.getTipo()) {
			case ID, FLOAT_VAL, INT_VAL: // Exp -> Tr ExpP
				// Tr
				NodeExpr left = parseTr();
				// ExpP
				NodeExpr expr = parseExprP(left);

				return expr;
			default:
				throw new SyntacticException(
						"Token " + tk.toString() + " alla riga " + tk.getRiga());
		}
	}

	private NodeExpr parseExprP(NodeExpr left) throws SyntacticException, LexicalException, IOException {
		Token tk = this.scanner.peekToken();
		switch (tk.getTipo()) {
			case PLUS: // ExpP -> + Tr ExpP
				// +
				match(TokenType.PLUS);
				// Tr
				NodeExpr rightPlus = parseTr();
				// ExpP
				NodeExpr exprPlus = parseExprP(new NodeBinOp(TokenType.PLUS, left, rightPlus));

				return exprPlus;
			case MINUS: // ExpP -> - Tr ExpP
				// -
				match(TokenType.MINUS);
				// Tr
				NodeExpr rightMinus = parseTr();
				// ExpP
				NodeExpr exprMinus = parseExprP(new NodeBinOp(TokenType.MINUS, left, rightMinus));

				return exprMinus;
			case SEMICOLON: // ExpP -> ϵ
				// ϵ

				return left;
			default:
				throw new SyntacticException(
						"Token " + tk.toString() + " alla riga " + tk.getRiga());
		}
	}

	private NodeExpr parseTr() throws SyntacticException, LexicalException, IOException {
		Token tk = this.scanner.peekToken();
		switch (tk.getTipo()) {
			case ID, FLOAT_VAL, INT_VAL: // Tr -> Val TrP
				// Val
				NodeExpr val = parseVal();
				// TrP
				NodeExpr expr = parseTrP(val);

				return expr;
			default:
				throw new SyntacticException(
						"Token " + tk.toString() + " alla riga " + tk.getRiga());
		}
	}

	private NodeExpr parseTrP(NodeExpr left) throws SyntacticException, LexicalException, IOException {
		Token tk = this.scanner.peekToken();
		switch (tk.getTipo()) {
			case MULT: // TrP -> * Val TrP
				// *
				match(TokenType.MULT);
				// Val
				NodeExpr rightMult = parseVal();
				// TrP
				NodeExpr exprMult = parseTrP(new NodeBinOp(TokenType.MULT, left, rightMult));

				return exprMult;
			case DIVIDE: // TrP -> / Val TrP
				// /
				match(TokenType.DIVIDE);
				// Val
				NodeExpr rightDivide = parseVal();
				// TrP
				NodeExpr exprDivide = parseTrP(new NodeBinOp(TokenType.DIVIDE, left, rightDivide));

				return exprDivide;
			case MINUS, PLUS, SEMICOLON: // TrP -> ϵ
				// ϵ

				return left;
			default:
				throw new SyntacticException(
						"Token " + tk.toString() + " alla riga " + tk.getRiga());
		}
	}

	private LangType parseTy() throws SyntacticException, LexicalException, IOException {
		Token tk = this.scanner.peekToken();
		switch (tk.getTipo()) {
			case TokenType.FLOAT_KW: // Ty -> float
				// float
				match(TokenType.FLOAT_KW);

				return LangType.FLOAT;
			case TokenType.INT_KW: // Ty -> int
				// int
				match(TokenType.INT_KW);

				return LangType.INT;
			default:
				throw new SyntacticException(
						"Token " + tk.toString() + " alla riga " + tk.getRiga() + " non é una keyword accettata");
		}
	}

	private NodeExpr parseVal() throws SyntacticException, LexicalException,
			IOException {
		Token tk = this.scanner.peekToken();
		switch (tk.getTipo()) {
			case INT_VAL: // Val -> intVal
				// intVal
				return new NodeConst(match(TokenType.INT_VAL).getValore(), LangType.INT);
			case FLOAT_VAL: // Val -> floatVal
				// floatVal
				return new NodeConst(match(TokenType.FLOAT_VAL).getValore(), LangType.FLOAT);
			case ID: // Val -> id
				// id
				return new NodeDefer(new NodeId(match(TokenType.ID).getValore()));
			default:
				throw new SyntacticException(
						"Token " + tk.toString() + " alla riga " + tk.getRiga());
		}
	}
}
