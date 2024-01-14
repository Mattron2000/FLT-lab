package parser;

import scanner.LexicalException;

import token.Token;

/**
 * @author Palmieri Matteo
 */
public class SyntacticException extends Exception {

    Token token;

    public SyntacticException(Token token) {
        this.token = token;
    }

    public SyntacticException(String message) {
        super(message);
    }

    public SyntacticException(String message, LexicalException e) {
        super(message, e);
    }
}
