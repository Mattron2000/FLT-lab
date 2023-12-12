package parser;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {

    private Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    private Token match(TokenType type) throws LexicalException, SyntacticException {
        Token tk = this.scanner.peekToken();
        if (type.equals(tk.getTipo()))
            return this.scanner.nextToken();
        else
            throw new SyntacticException(
                    "Aspettavo il tipo " + type.toString() + " e invece ho trovato " + tk.getTipo().toString()
                            + " alla riga " + tk.getRiga());
    }

    public void parse() throws SyntacticException, LexicalException {
        this.parsePrg();
    }

    private void parsePrg() throws SyntacticException, LexicalException {
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

    private void parseDSs() {
    }

    private void parseDcl() {
    }

    private void parseTy() {
    }

    private void parseDclP() {
    }

    private void parseStm() {
    }

    private void parseExp() {
    }

    private void parseExpP() {
    }

    private void parseTr() {
    }

    private void parseTrP() {
    }

    private void parseVal() {
    }

}