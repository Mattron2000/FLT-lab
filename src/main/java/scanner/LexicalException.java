package scanner;

import java.io.FileNotFoundException;
import java.io.IOException;

public class LexicalException extends Exception {

  public LexicalException(String string) {
    super(string);
  }

  public LexicalException() {
    super();
  }

  public LexicalException(String message, FileNotFoundException cause) {
    super(message, cause);
  }

  public LexicalException(String message, IOException cause) {
    super(message, cause);
  }

}
