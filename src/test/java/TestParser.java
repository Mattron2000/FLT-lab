import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import parser.Parser;
import scanner.Scanner;

class TestParser {
	private final String RootFolderOfJavaProject = System.getProperty("user.dir") + "/src/test/java/data/testParser/";

	@TestFactory
	public Stream<DynamicTest> generateDynamicTests() {
		String fileDynamicTestFilePath = RootFolderOfJavaProject + "dynamic_tests.txt";
		List<DynamicTest> dynamicTests = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileDynamicTestFilePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");

				assertEquals(2, values.length);

				String fileName = values[0].trim();
				String testName = values[1].trim();

				DynamicTest dynamicTest = dynamicTest(testName, () -> {
					Scanner s = new Scanner(RootFolderOfJavaProject + fileName);
					Parser p = new Parser(s);

					p.parse();
				});

				dynamicTests.add(dynamicTest);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dynamicTests.stream();
	}

	// @Test
	// void testCorretto1() throws FileNotFoundException, LexicalException,
	// SyntacticException {

	// }

	// @Test
	// void testCorretto2() throws FileNotFoundException, LexicalException,
	// SyntacticException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testParserCorretto2.txt");
	// Parser p = new Parser(s);

	// p.parse();
	// }

	// @Test
	// void testEcc0() throws FileNotFoundException, LexicalException,
	// SyntacticException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testParserEcc_0.txt");
	// Parser p = new Parser(s);

	// p.parse();
	// }

	// @Test
	// void testEcc1() throws FileNotFoundException, LexicalException,
	// SyntacticException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testParserEcc_1.txt");
	// Parser p = new Parser(s);

	// p.parse();
	// }

	// @Test
	// void testEcc2() throws FileNotFoundException, LexicalException,
	// SyntacticException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testParserEcc_2.txt");
	// Parser p = new Parser(s);

	// p.parse();
	// }

	// @Test
	// void testEcc3() throws FileNotFoundException, LexicalException,
	// SyntacticException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testParserEcc_3.txt");
	// Parser p = new Parser(s);

	// p.parse();
	// }

	// @Test
	// void testEcc4() throws FileNotFoundException, LexicalException,
	// SyntacticException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testParserEcc_4.txt");
	// Parser p = new Parser(s);

	// p.parse();
	// }

	// @Test
	// void testEcc5() throws FileNotFoundException, LexicalException,
	// SyntacticException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testParserEcc_5.txt");
	// Parser p = new Parser(s);

	// p.parse();
	// }

	// @Test
	// void testEcc6() throws FileNotFoundException, LexicalException,
	// SyntacticException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testParserEcc_6.txt");
	// Parser p = new Parser(s);

	// p.parse();
	// }

	// @Test
	// void testEcc7() throws FileNotFoundException, LexicalException,
	// SyntacticException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testParserEcc_7.txt");
	// Parser p = new Parser(s);

	// p.parse();
	// }

	// @Test
	// void testSoloDich() throws FileNotFoundException, LexicalException,
	// SyntacticException {
	// Scanner s = new Scanner(RootFolderOfJavaProject + "testSoloDichPrint1.txt");
	// Parser p = new Parser(s);

	// p.parse();
	// }
}
// t = s.nextToken();
// assertEquals(4, t.getRiga());
// assertEquals(TokenType.EOF, t.getTipo());
// assertEquals(null, t.getValore());