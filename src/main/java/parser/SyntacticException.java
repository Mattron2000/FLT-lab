package parser;

import token.Token;

public class SyntacticException extends Exception {

    Token token;

    public SyntacticException(Token token) {
        this.token = token;
    }

    public SyntacticException(String message) {
        super(message);
    }
}