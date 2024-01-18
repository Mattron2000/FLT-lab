package symbolTable;

import ast.LangType;

/**
 * @author Palmieri Matteo
 */
public class Attributes {

	private LangType type;

	public Attributes(LangType type) {
		this.type = type;
	}

	public LangType getType() {
		return this.type;
	}

	public void setType(LangType type) {
		this.type = type;
	}
}
