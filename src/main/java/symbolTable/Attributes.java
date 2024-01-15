package symbolTable;

import ast.LangType;

/**
 * @author Palmieri Matteo
 */
public class Attributes {

	private LangType type;
	private Character register;

	public Attributes(LangType type) {
		this.type = type;
		this.register = null;
	}

	public LangType getType() {
		return this.type;
	}

	public void setType(LangType type) {
		this.type = type;
	}

	public char getRegister() {
		return this.register;
	}

	public void setRegister(Character register) {
		this.register = register;
	}
}
