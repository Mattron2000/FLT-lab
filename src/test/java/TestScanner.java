import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestScanner {

	private static final String testScannerFolder = System.getProperty("user.dir")
			+ "/src/test/java/data/testScanner/";

	/**
	 * Check toString() function, that Token class have, execute correctly
	 */
	@Test
	void testToString() {
		// int tempa;
		String row = new Token(TokenType.INT_KW, 1).toString() +
				new Token(TokenType.ID, 1, "tempa").toString() +
				new Token(TokenType.SEMICOLON, 1).toString();
		System.out.println(row);
		assertEquals("<INT_KW, riga:1>" +
				"<ID, riga:1, valore:tempa>" +
				"<SEMICOLON, riga:1>", row);

		// float tempb = tempa / 3.2;
		row = new Token(TokenType.FLOAT_KW, 3).toString() +
				new Token(TokenType.ID, 3, "tempb").toString() +
				new Token(TokenType.ASSIGN, 3).toString() +
				new Token(TokenType.ID, 3, "tempa").toString() +
				new Token(TokenType.DIVIDE, 3).toString() +
				new Token(TokenType.FLOAT_VAL, 3, "3.2").toString() +
				new Token(TokenType.SEMICOLON, 1).toString();
		System.out.println(row);
		assertEquals("<FLOAT_KW, riga:3>" +
				"<ID, riga:3, valore:tempb>" +
				"<ASSIGN, riga:3>" +
				"<ID, riga:3, valore:tempa>" +
				"<DIVIDE, riga:3>" +
				"<FLOAT_VAL, riga:3, valore:3.2>" +
				"<SEMICOLON, riga:1>", row);
	}

	@Test
	void testEOF() throws LexicalException, IOException {
		Scanner s = new Scanner(testScannerFolder + "testEOF.txt");
		Token t = s.nextToken();

		assertEquals(4, t.getRiga());
		assertEquals(TokenType.EOF, t.getTipo());
		assertEquals(null, t.getValore());

		t = s.nextToken();

		assertEquals(4, t.getRiga());
		assertEquals(TokenType.EOF, t.getTipo());
		assertEquals(null, t.getValore());
	}

	@Test
	void testFloat() throws LexicalException, IOException {
		Scanner s = new Scanner(testScannerFolder + "testFLOAT.txt");
		Token t = s.nextToken();

		assertEquals(1, t.getRiga());
		assertEquals(TokenType.FLOAT_VAL, t.getTipo());
		assertEquals("98.8095", t.getValore());

		t = s.nextToken();
		assertEquals(2, t.getRiga());
		assertEquals(TokenType.FLOAT_VAL, t.getTipo());
		assertEquals("0.0", t.getValore());

		t = s.nextToken();
		assertEquals(3, t.getRiga());
		assertEquals(TokenType.FLOAT_VAL, t.getTipo());
		assertEquals("98.0", t.getValore());

		t = s.nextToken();
		assertEquals(5, t.getRiga());
		assertEquals(TokenType.FLOAT_VAL, t.getTipo());
		assertEquals("89.9999", t.getValore());

		t = s.nextToken();
		assertEquals(5, t.getRiga());
		assertEquals(TokenType.EOF, t.getTipo());
		assertEquals(null, t.getValore());
	}

	@Test
	void testGenerale() throws LexicalException, IOException {
		Scanner s = new Scanner(testScannerFolder + "testGenerale.txt");
		Token t = s.nextToken();

		assertEquals(t.getTipo(), TokenType.INT_KW);
		assertEquals(t.getRiga(), 1);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getRiga(), 1);
		assertEquals(t.getValore(), "temp");

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.SEMICOLON);
		assertEquals(t.getRiga(), 1);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getRiga(), 2);
		assertEquals(t.getValore(), "temp");

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.PLUS_ASSIGN);
		assertEquals(t.getRiga(), 2);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.FLOAT_VAL);
		assertEquals(t.getRiga(), 2);
		assertEquals(t.getValore(), "5.0");

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.SEMICOLON);
		assertEquals(t.getRiga(), 2);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.FLOAT_KW);
		assertEquals(t.getRiga(), 4);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getRiga(), 4);
		assertEquals(t.getValore(), "b");

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.SEMICOLON);
		assertEquals(t.getRiga(), 4);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getRiga(), 5);
		assertEquals(t.getValore(), "b");

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.ASSIGN);
		assertEquals(t.getRiga(), 5);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getRiga(), 5);
		assertEquals(t.getValore(), "temp");

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.PLUS);
		assertEquals(t.getRiga(), 5);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.FLOAT_VAL);
		assertEquals(t.getRiga(), 5);
		assertEquals(t.getValore(), "3.2");

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.SEMICOLON);
		assertEquals(t.getRiga(), 5);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.PRINT);
		assertEquals(t.getRiga(), 6);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getRiga(), 6);
		assertEquals(t.getValore(), "b");

		t = s.nextToken();
		assertEquals(t.getTipo(), TokenType.SEMICOLON);
		assertEquals(t.getRiga(), 6);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 7);
		assertEquals(t.getTipo(), TokenType.EOF);
		assertEquals(t.getValore(), null);
	}

	@Test
	void testId() throws LexicalException, IOException {
		Scanner s = new Scanner(testScannerFolder + "testId.txt");
		Token t = s.nextToken();

		assertEquals(t.getRiga(), 1);
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getValore(), "jskjdsfhkjdshkf");

		t = s.nextToken();
		assertEquals(t.getRiga(), 2);
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getValore(), "printl");

		t = s.nextToken();
		assertEquals(t.getRiga(), 4);
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getValore(), "ffloat");

		t = s.nextToken();
		assertEquals(t.getRiga(), 6);
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getValore(), "hhhjj");

		assertEquals(s.peekToken().getRiga(), 7);
		assertEquals(s.peekToken().getTipo(), TokenType.EOF);
		assertEquals(s.peekToken().getValore(), null);
	}

	@Test
	void testIdKeywords() throws LexicalException, IOException {
		Scanner s = new Scanner(testScannerFolder + "testIdKeyWords.txt");
		Token t = s.nextToken();

		assertEquals(1, t.getRiga());
		assertEquals(TokenType.INT_KW, t.getTipo());
		assertEquals(null, t.getValore());

		t = s.nextToken();
		assertEquals(1, t.getRiga());
		assertEquals(TokenType.ID, t.getTipo());
		assertEquals("inta", t.getValore());

		t = s.nextToken();
		assertEquals(2, t.getRiga());
		assertEquals(TokenType.FLOAT_KW, t.getTipo());
		assertEquals(null, t.getValore());

		t = s.nextToken();
		assertEquals(3, t.getRiga());
		assertEquals(TokenType.PRINT, t.getTipo());
		assertEquals(null, t.getValore());

		t = s.nextToken();
		assertEquals(4, t.getRiga());
		assertEquals(TokenType.ID, t.getTipo());
		assertEquals("nome", t.getValore());

		t = s.nextToken();
		assertEquals(5, t.getRiga());
		assertEquals(TokenType.ID, t.getTipo());
		assertEquals("intnome", t.getValore());

		t = s.nextToken();
		assertEquals(6, t.getRiga());
		assertEquals(TokenType.INT_KW, t.getTipo());
		assertEquals(null, t.getValore());

		t = s.nextToken();
		assertEquals(6, t.getRiga());
		assertEquals(TokenType.ID, t.getTipo());
		assertEquals("nome", t.getValore());

		t = s.nextToken();
		assertEquals(6, t.getRiga());
		assertEquals(TokenType.EOF, t.getTipo());
		assertEquals(null, t.getValore());
	}

	@Test
	void testInt() throws LexicalException, IOException {
		Scanner s = new Scanner(testScannerFolder + "testINT.txt");
		Token t = s.nextToken();

		assertEquals(t.getRiga(), 2);
		assertEquals(t.getTipo(), TokenType.INT_VAL);
		assertEquals(t.getValore(), "698");

		t = s.nextToken();
		assertEquals(t.getRiga(), 4);
		assertEquals(t.getTipo(), TokenType.INT_VAL);
		assertEquals(t.getValore(), "560099");

		t = s.nextToken();
		assertEquals(t.getRiga(), 5);
		assertEquals(t.getTipo(), TokenType.INT_VAL);
		assertEquals(t.getValore(), "1234");

		t = s.nextToken();
		assertEquals(t.getRiga(), 5);
		assertEquals(t.getTipo(), TokenType.EOF);
		assertEquals(t.getValore(), null);
	}

	@Test
	void testKeywords() throws LexicalException, IOException {
		Scanner s = new Scanner(testScannerFolder + "testKeywords.txt");
		Token t = s.nextToken();

		assertEquals(t.getRiga(), 2);
		assertEquals(t.getTipo(), TokenType.PRINT);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 2);
		assertEquals(t.getTipo(), TokenType.FLOAT_KW);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 5);
		assertEquals(t.getTipo(), TokenType.INT_KW);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 5);
		assertEquals(t.getTipo(), TokenType.EOF);
		assertEquals(t.getValore(), null);
	}

	@Test
	void testOperators() throws LexicalException, IOException {
		Scanner s = new Scanner(testScannerFolder + "testOperators.txt");
		Token t = s.nextToken();

		assertEquals(t.getRiga(), 1);
		assertEquals(t.getTipo(), TokenType.PLUS);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 1);
		assertEquals(t.getTipo(), TokenType.DIVIDE_ASSIGN);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 2);
		assertEquals(t.getTipo(), TokenType.MINUS);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 2);
		assertEquals(t.getTipo(), TokenType.MULT);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 3);
		assertEquals(t.getTipo(), TokenType.DIVIDE);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 5);
		assertEquals(t.getTipo(), TokenType.PLUS_ASSIGN);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 6);
		assertEquals(t.getTipo(), TokenType.ASSIGN);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 6);
		assertEquals(t.getTipo(), TokenType.MINUS_ASSIGN);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 8);
		assertEquals(t.getTipo(), TokenType.MINUS);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 8);
		assertEquals(t.getTipo(), TokenType.ASSIGN);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 8);
		assertEquals(t.getTipo(), TokenType.MULT_ASSIGN);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 10);
		assertEquals(t.getTipo(), TokenType.SEMICOLON);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 10);
		assertEquals(t.getTipo(), TokenType.EOF);
		assertEquals(t.getValore(), null);
	}

	@Test
	void testErroriID() throws LexicalException, IOException {
		Scanner s = new Scanner(testScannerFolder + "erroriID");
		Token t = s.nextToken();

		assertEquals(t.getRiga(), 1);
		assertEquals(t.getTipo(), TokenType.EOF);
		assertEquals(t.getValore(), null);
	}

	@Test
	void testErroriNumbers() throws LexicalException, IOException {
		Scanner s = null;
		Token t = null;

		for (int i = 1; i < +4; i++) {
			s = new Scanner(
					testScannerFolder + "erroriNumbers/erroriNumbers" + i
							+ ".txt");

			switch (i) {
				case 1:
					try {
						t = s.nextToken();
					} catch (LexicalException e) {
						System.out.println(e.getMessage());
						assertEquals("Errore in scanNumber() alla riga 1, non puoi creare un numero intero iniziando con la cifra zero",
								e.getMessage());
					}
					break;
				case 2:
					try {
						t = s.nextToken();
					} catch (LexicalException e) {
						System.out.println(e.getMessage());
						assertEquals("Errore in scanNumber() alla riga 2, sono arrivato a '123' ma ho trovato 'a'",
								e.getMessage());
					}
					break;
				case 3:
					System.out.println("3");

					break;
				case 4:
					System.out.println("4");

					break;
			}
		}
	}
}
