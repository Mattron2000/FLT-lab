package parser;

import java.io.IOException;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {

    private Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    private Token match(TokenType type) throws LexicalException, SyntacticException, IOException {
        Token tk = this.scanner.peekToken();
        if (type.equals(tk.getTipo()))
            return this.scanner.nextToken();
        else
            throw new SyntacticException(
                    "Aspettavo il tipo " + type.toString() + " e invece ho trovato " + tk.getTipo().toString()
                            + " alla riga " + tk.getRiga());
    }

    public void parse() throws SyntacticException, LexicalException, IOException {
        this.parsePrg();
    }

    private void parsePrg() throws SyntacticException, LexicalException, IOException {
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW, TokenType.INT_KW, TokenType.ID, TokenType.PRINT, TokenType.EOF: // Prg -> DSs $
                parseDSs();
                match(TokenType.EOF);
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga() + " non é inizio del programma");
        }
    }

    private void parseDSs() throws SyntacticException, LexicalException, IOException {
        // TODO
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW: // DSs -> Dcl DSs
                // TODO
                match(null);
                break;
            case TokenType.INT_KW: // DSs -> Stm DSs
                // TODO
                match(null);
                break;
            case TokenType.INT_VAL: // DSs -> ϵ
                // TODO
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseDcl() throws SyntacticException, LexicalException, IOException {
        // TODO
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW: // Dcl -> Ty id DclP
                // TODO
                match(null);
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseDclP() throws SyntacticException, LexicalException, IOException {
        // TODO
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW: // DclP -> ;
                // TODO
                match(null);
                break;
            case TokenType.INT_KW: // DclP -> opAss Exp;
                // TODO
                match(null);
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseStm() throws SyntacticException, LexicalException, IOException {
        // TODO
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW: // Stm -> id opAss Exp
                // TODO
                match(null);
                break;
            case TokenType.INT_KW: // Stm -> print id;
                // TODO
                match(null);
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseExp() throws SyntacticException, LexicalException, IOException {
        // TODO
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW: // Exp -> Tr ExpP
                // TODO
                match(null);
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseExpP() throws SyntacticException, LexicalException, IOException {
        // TODO
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW: // ExpP -> + Tr ExpP
                // TODO
                match(null);
                break;
            case TokenType.INT_KW: // ExpP -> - Tr ExpP
                // TODO
                match(null);
                break;
            case TokenType.PRINT: // ExpP -> ϵ
                // TODO
                match(null);
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseTr() throws SyntacticException, LexicalException, IOException {
        // TODO
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW: // Tr -> Val TrP
                // TODO
                match(null);
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseTrP() throws SyntacticException, LexicalException, IOException {
        // TODO
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW: // TrP -> * Val TrP
                // TODO
                match(null);
                break;
            case TokenType.INT_KW: // TrP -> / Val TrP
                // TODO
                match(null);
                break;
            case TokenType.PRINT: // TrP -> ϵ
                // TODO
                match(null);
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseTy() throws SyntacticException, LexicalException, IOException {
        // TODO
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW: // Ty -> float
                // TODO
                match(null);
                break;
            case TokenType.INT_KW: // Ty -> int
                // TODO
                match(null);
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseVal() throws SyntacticException, LexicalException, IOException {
        // TODO
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW: // Val -> intVal
                // TODO
                match(null);
                break;
            case TokenType.INT_KW: // Val -> floatVal
                // TODO
                match(null);
                break;
            case TokenType.PRINT: // Val -> id
                // TODO
                match(null);
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

}