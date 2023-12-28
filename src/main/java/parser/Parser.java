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

    /**
     * Check that the current token is the same TokenType of 'type'
     * 
     * @param type Expected TokenType
     * @return current token
     * @throws LexicalException
     * @throws SyntacticException
     * @throws IOException
     */
    private Token match(TokenType type) throws LexicalException, SyntacticException, IOException {
        Token tk = this.scanner.peekToken();

        if (tk.getTipo().equals(type))
            return this.scanner.nextToken();
        else
            throw new SyntacticException("Aspettavo il tipo " + type.toString() + " e invece ho trovato "
                    + tk.getTipo().toString() + " alla riga " + tk.getRiga());
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
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case FLOAT_KW, INT_KW: // DSs -> Dcl DSs
                // TODO Dcl
                // TODO DSs
                break;
            case ID, PRINT: // DSs -> Stm DSs
                // TODO Stm
                // TODO DSs
                break;
            case EOF: // DSs -> ϵ
                // TODO ϵ
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseDcl() throws SyntacticException, LexicalException, IOException {
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case FLOAT_KW, INT_KW: // Dcl -> Ty id DclP
                // TODO Ty
                // TODO id
                // TODO DclP
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseDclP() throws SyntacticException, LexicalException, IOException {
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case SEMICOLON: // DclP -> ;
                // TODO ;
                break;
            case ASSIGN: // DclP -> opAss Exp;
                // TODO opAss
                // TODO Exp
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseStm() throws SyntacticException, LexicalException, IOException {
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case ID: // Stm -> id opAss Exp
                // TODO id
                // TODO opAss
                // TODO Exp
                break;
            case PRINT: // Stm -> print id;
                // TODO print
                // TODO id
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseExp() throws SyntacticException, LexicalException, IOException {
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case ID, FLOAT_VAL, INT_VAL: // Exp -> Tr ExpP
                // TODO Tr
                // TODO ExpP
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseExpP() throws SyntacticException, LexicalException, IOException {
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case PLUS: // ExpP -> + Tr ExpP
                // TODO +
                // TODO Tr
                // TODO ExpP
                break;
            case MINUS: // ExpP -> - Tr ExpP
                // TODO -
                // TODO Tr
                // TODO ExpP
                break;
            case EOF: // ExpP -> ϵ
                // TODO ϵ
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseTr() throws SyntacticException, LexicalException, IOException {
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case ID, FLOAT_VAL, INT_VAL: // Tr -> Val TrP
                // TODO Val
                // TODO TrP
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseTrP() throws SyntacticException, LexicalException, IOException {
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case MULT: // TrP -> * Val TrP
                // TODO *
                // TODO Val
                // TODO TrP
                break;
            case DIVIDE: // TrP -> / Val TrP
                // TODO /
                // TODO Val
                // TODO TrP
                break;
            case EOF: // TrP -> ϵ
                // TODO ϵ
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseTy() throws SyntacticException, LexicalException, IOException {
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case TokenType.FLOAT_KW: // Ty -> float
                // TODO float
                break;
            case TokenType.INT_KW: // Ty -> int
                // TODO int
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }

    private void parseVal() throws SyntacticException, LexicalException, IOException {
        Token tk = this.scanner.peekToken();
        switch (tk.getTipo()) {
            case INT_VAL: // Val -> intVal
                // TODO intVal
                break;
            case FLOAT_VAL: // Val -> floatVal
                // TODO floatVal
                break;
            case ID: // Val -> id
                // TODO id
                break;
            default:
                throw new SyntacticException(
                        "Token " + tk.toString() + " alla riga " + tk.getRiga());
        }
    }
}