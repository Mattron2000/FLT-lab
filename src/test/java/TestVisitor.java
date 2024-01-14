import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import ast.NodePrg;

import parser.Parser;
import parser.SyntacticException;

import scanner.Scanner;

import visitor.IVisitor;
import visitor.TypeCheckingVisitor;

class TestVisitor {
	private final String RootFolderOfJavaProject = System.getProperty("user.dir") + "/src/test/java/data/testVisitor/";

	@TestFactory
	public Stream<DynamicTest> generateDynamicTests() {
		String fileDynamicTestFilePath = RootFolderOfJavaProject + "dynamic_tests.txt";
		ArrayList<DynamicTest> dynamicTests = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(fileDynamicTestFilePath))) {
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				String[] values = line.split(",,");

				assertEquals(3, values.length);

				String fileName = values[0].trim();
				String testName = values[1].trim();
				String expectedExceptionMessage = values[2].trim();

				dynamicTests.add(dynamicTest(testName, () -> {
					Scanner s = new Scanner(RootFolderOfJavaProject + fileName);
					Parser p = new Parser(s);

					if (!expectedExceptionMessage.equals("null")) {
						Throwable e = assertThrows(SyntacticException.class, () -> {
							NodePrg nodePrg = p.parse();

							TypeCheckingVisitor visitor = new TypeCheckingVisitor();

							nodePrg.accept(visitor);

							visitor.getResType(); // visitor.visit(nodePrg);
						});
						assertEquals(expectedExceptionMessage, e.getMessage());
						return;
					}

					NodePrg nodePrg = p.parse();

					TypeCheckingVisitor visitor = new TypeCheckingVisitor();

					nodePrg.accept(visitor);

					visitor.getResType(); // visitor.visit(nodePrg);

					System.out.println(nodePrg.toString());
				}));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dynamicTests.stream();
	}
}
