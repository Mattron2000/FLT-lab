package symbolTable;

import java.util.HashMap;

/**
 * @author Palmieri Matteo
 */
public class SymbolTable {

	private static HashMap<String, Attributes> table;

	public static void init() {
		table = new HashMap<String, Attributes>();
	}

	public static boolean enter(String id, Attributes entry) {
		Attributes value = table.get(id);
		if (value != null)
			return false;
		table.put(id, entry);
		return true;
	}

	public static Attributes lookup(String id) {
		return table.get(id);
	}

	public static String toStr() {
		StringBuilder res = new StringBuilder("symbol table\n=============\n");

		for (HashMap.Entry<String, Attributes> entry : table.entrySet())
			res.append(entry.getKey() + "   \t" + entry.getValue() + "\n");

		return res.toString();
	}

	public static int size() {
		return (table.size());
	}
}
