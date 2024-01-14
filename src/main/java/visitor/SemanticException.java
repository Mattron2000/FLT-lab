package visitor;

import token.Token;

/**
 * @author Palmieri Matteo
 */
public class SemanticException extends Exception {

    Token token;

    public SemanticException(Token token) {
        this.token = token;
    }

    public SemanticException(String message) {
        super(message);
    }
}
