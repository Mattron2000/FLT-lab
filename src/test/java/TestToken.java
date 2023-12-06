import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestToken {

	/**
	 * Check toString() function that execute correctly
	 */
	@Test
	void testToString() {
		// int tempa;
		String row = new Token(TokenType.INT_KW, 1).toString() +
				new Token(TokenType.ID, 1, "tempa").toString() +
				new Token(TokenType.SEMICOLON, 1).toString();
		System.out.println(row);
		assert (row.equals("<INT_KW, riga:1>" +
				"<ID, riga:1, valore:tempa>" +
				"<SEMICOLON, riga:1>"));

		// float tempb = tempa / 3.2;
		row = new Token(TokenType.FLOAT_KW, 3).toString() +
				new Token(TokenType.ID, 3, "tempb").toString() +
				new Token(TokenType.ASSIGN, 3).toString() +
				new Token(TokenType.ID, 3, "tempa").toString() +
				new Token(TokenType.DIVIDE, 3).toString() +
				new Token(TokenType.FLOAT_VAL, 3, "3.2").toString() +
				new Token(TokenType.SEMICOLON, 1).toString();
		System.out.println(row);
		assert (row.equals("<FLOAT_KW, riga:3>" +
				"<ID, riga:3, valore:tempb>" +
				"<ASSIGN, riga:3>" +
				"<ID, riga:3, valore:tempa>" +
				"<DIVIDE, riga:3>" +
				"<FLOAT_VAL, riga:3, valore:3.2>" +
				"<SEMICOLON, riga:1>"));
	}

	@Test
	void testEOF() throws FileNotFoundException, IOException, LexicalException {
		String CurrentWorkingDirectoryOfJavaProject = System.getProperty("user.dir");

		String t = new Scanner(CurrentWorkingDirectoryOfJavaProject + "/src/test/java/data/testScanner/testEOF.txt")
				.nextToken().toString();

		System.out.println(t);
		assertEquals(t, "<EOF, riga:4>");
	}

}
