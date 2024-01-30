package symbolTable;

import java.util.HashMap;

/**
 * @author Palmieri Matteo
 */
public class SymbolTable {

	private static HashMap<String, Attributes> table;

	/**
	 * inizializzi una hashmap vuota
	 * 
	 */
	public static void init() {
		table = new HashMap<String, Attributes>();
	}

	/**
	 * Inserisci una nuova coppia <String, Attributes>, se viene aggiunta con
	 * successo, ritorna true, se esiste giá la kay, ritorna false
	 * 
	 * @param id    chiave, solitamente il nome dell'ID
	 * @param entry valore, il relativo Attributes
	 * @return lo stato di successo o fallimento dell'aggiunta alla hashmap
	 */
	public static boolean enter(String id, Attributes entry) {
		if (table.get(id) != null)
			return false;
		table.put(id, entry);
		return true;
	}

	/**
	 * restituisce il valore associato alla chiave
	 * 
	 * @param id chiave, solitamente il nome dell'ID
	 * @return l'Attributes associato, altrimenti null se la chiave non esiste nella
	 *         hashmap
	 */
	public static Attributes lookup(String id) {
		return table.get(id);
	}

	/**
	 * é la funzione toString() della hashmap
	 * 
	 * @return
	 */
	public static String toStr() {
		StringBuilder res = new StringBuilder(
				"-----------------symbol table----------------\n" +
				"nome variabile\t| nome registro\t| tipo valore\n");

		for (HashMap.Entry<String, Attributes> entry : table.entrySet())
			res.append("  " + entry.getKey() + "\t\t| " + entry.getValue().getRegister() + "\t\t| " + entry.getValue().getType()
					+ "\n");

		return res.toString();
	}

	/**
	 * restituisce la dimensione della hashmap
	 * 
	 * @return quante entry ci sono nella hashmap
	 */
	public static int size() {
		return (table.size());
	}
}
