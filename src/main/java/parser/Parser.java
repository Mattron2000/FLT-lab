package parser;

import java.io.IOException;
import java.util.ArrayList;

import ast.LangType;
import ast.NodeDSs;
import ast.NodeDcl;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrg;
import ast.NodePrint;
import ast.NodeStm;

import scanner.LexicalException;
import scanner.Scanner;

import token.Token;
import token.TokenType;

public class Parser {

    private Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

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
                NodeId id = new NodeId(match(TokenType.ID));
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
                // TODO opAss
                // TODO Exp

                return null;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga()
                                + " non é il tokn correto per assegnare un valore alla variabile");
        }
    }

    private NodeStm parseStm() throws SyntacticException, LexicalException, IOException {
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case ID: // Stm -> id opAss Exp ;
                // TODO id
                // TODO opAss
                // TODO Exp

                return null;
            case PRINT: // Stm -> print id ;
                // print
                match(TokenType.PRINT);
                // id
                NodeId idPrint = new NodeId(match(TokenType.ID));
                // ;
                match(TokenType.SEMICOLON);

                return new NodePrint(idPrint);
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga() + " non é quello giusto per scrivere uno statement");
        }
    }

    // private NodeExpr parseExpr() throws SyntacticException, LexicalException,
    // IOException {
    // Token tk = this.scanner.peekToken();
    // switch (tk.getTipo()) {
    // case ID, FLOAT_VAL, INT_VAL: // Exp -> Tr ExpP
    // // TODO Tr
    // // TODO ExpP

    // return null;
    // default:
    // throw new SyntacticException(
    // "Token " + tk.toString() + " alla riga " + tk.getRiga());
    // }
    // }

    // private NodeExpr parseExprP() throws SyntacticException, LexicalException,
    // IOException {
    // Token tk = this.scanner.peekToken();
    // switch (tk.getTipo()) {
    // case PLUS: // ExpP -> + Tr ExpP
    // // TODO +
    // // TODO Tr
    // // TODO ExpP

    // return null;
    // case MINUS: // ExpP -> - Tr ExpP
    // // TODO -
    // // TODO Tr
    // // TODO ExpP

    // return null;
    // case SEMICOLON: // ExpP -> ϵ
    // // TODO ϵ

    // return null;
    // default:
    // throw new SyntacticException(
    // "Token " + tk.toString() + " alla riga " + tk.getRiga());
    // }
    // }

    // private NodeExpr parseTr() throws SyntacticException, LexicalException,
    // IOException {
    // Token tk = this.scanner.peekToken();
    // switch (tk.getTipo()) {
    // case ID, FLOAT_VAL, INT_VAL: // Tr -> Val TrP
    // // TODO Val
    // // TODO TrP

    // return null;
    // default:
    // throw new SyntacticException(
    // "Token " + tk.toString() + " alla riga " + tk.getRiga());
    // }
    // }

    // private NodeExpr parseTrP() throws SyntacticException, LexicalException,
    // IOException {
    // Token tk = this.scanner.peekToken();
    // switch (tk.getTipo()) {
    // case MULT: // TrP -> * Val TrP
    // // TODO *
    // // TODO Val
    // // TODO TrP

    // return null;
    // case DIVIDE: // TrP -> / Val TrP
    // // TODO /
    // // TODO Val
    // // TODO TrP

    // return null;
    // case MINUS, PLUS, SEMICOLON: // TrP -> ϵ
    // // TODO ϵ

    // return null;
    // default:
    // throw new SyntacticException(
    // "Token " + tk.toString() + " alla riga " + tk.getRiga());
    // }
    // }

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

    // private void parseVal() throws SyntacticException, LexicalException,
    // IOException {
    // Token tk = this.scanner.peekToken();
    // switch (tk.getTipo()) {
    // case INT_VAL: // Val -> intVal
    // // TODO intVal

    // // return null;
    // case FLOAT_VAL: // Val -> floatVal
    // // TODO floatVal

    // // return null;
    // case ID: // Val -> id
    // // TODO id

    // // return null;
    // default:
    // throw new SyntacticException(
    // "Token " + tk.toString() + " alla riga " + tk.getRiga());
    // }
    // }
}