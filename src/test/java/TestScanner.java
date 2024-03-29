import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import scanner.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

/**
 * Tutti i test dello scanner fanno le stesse cose, passano un percorso file
 * assoluto al file txt alla classe scanner e controlla ogni token al suo
 * interno
 */
class TestScanner {

	/**
	 * percorso della cartella dove contiene tutti i file di testo
	 */
	private static final String testScannerFolder = System.getProperty("user.dir")
			+ "/src/test/java/data/testScanner/";

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
		Token t = null;
		try {
			t = s.nextToken();
		} catch (LexicalException e) {
			assertEquals(
					"Errore in scanNumber() alla riga 1, non puoi creare un numero intero iniziando con la cifra zero",
					e.getMessage());
			return;
		}

		assertEquals(1, t.getRiga());
		assertEquals(TokenType.FLOAT_VAL, t.getTipo());
		assertEquals("098.8095", t.getValore());

		t = s.nextToken();
		assertEquals(2, t.getRiga());
		assertEquals(TokenType.FLOAT_VAL, t.getTipo());
		assertEquals("0.", t.getValore());

		t = s.nextToken();
		assertEquals(3, t.getRiga());
		assertEquals(TokenType.FLOAT_VAL, t.getTipo());
		assertEquals("98.", t.getValore());

		t = s.nextToken();
		assertEquals(5, t.getRiga());
		assertEquals(TokenType.FLOAT_VAL, t.getTipo());
		assertEquals("89.99999", t.getValore());

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
		assertEquals(t.getTipo(), TokenType.ASSIGN);
		assertEquals(t.getRiga(), 2);
		assertEquals(t.getValore(), null);

		try {
			t = s.nextToken();
		} catch (LexicalException e) {
			assertEquals("Errore in scanFloat() alla riga 2, il valore float deve avere almeno una cifra decimale",
					e.getMessage());
			return;
		}

		assertEquals(t.getTipo(), TokenType.FLOAT_VAL);
		assertEquals(t.getRiga(), 2);
		assertEquals(t.getValore(), "5.");

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
		assertEquals(t.getTipo(), TokenType.ASSIGN);
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
		assertEquals(t.getTipo(), TokenType.ASSIGN);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 6);
		assertEquals(t.getTipo(), TokenType.ASSIGN);
		assertEquals(t.getValore(), null);

		t = s.nextToken();
		assertEquals(t.getRiga(), 6);
		assertEquals(t.getTipo(), TokenType.ASSIGN);
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
		assertEquals(t.getTipo(), TokenType.ASSIGN);
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

	@TestFactory
	public Stream<DynamicTest> generateDynamicTestErroriNumbers() {
		ArrayList<DynamicTest> dynamicTests = new ArrayList<>();

		String[] expectedExceptionMessageList = {
				"Errore in scanNumber() alla riga 1, non puoi scrivere un numero intero con uno zero davanti",
				"Errore in scanNumber() alla riga 2, sono arrivato a '123' ma ho trovato 'a'",
				"Errore in scanFloat() alla riga 3, sono arrivato a '12.' ma ho trovato una lettera alfabetica 'a'",
				"Errore in scanFloat() alla riga 4, il valore float puó contenere fino a 5 cifre decimali numeriche"
		};

		for (int i = 1; i <= 4; i++) {
			String fileName = "erroriNumbers/erroriNumbers" + i + ".txt";
			String testName = "erroriNumbers" + i;
			String expectedExceptionMessage = expectedExceptionMessageList[i - 1];

			dynamicTests.add(dynamicTest(testName, () -> {
				Scanner s = new Scanner(testScannerFolder + fileName);
				Throwable e = assertThrows(LexicalException.class, () -> s.nextToken());
				System.out.println(e.getMessage());
				assertEquals(expectedExceptionMessage, e.getMessage());
			}));
		}

		return dynamicTests.stream();
	}
}
