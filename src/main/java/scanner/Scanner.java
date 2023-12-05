package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.HashMap;

import token.*;

public class Scanner {
	final char EOF = (char) -1;
	private int riga;
	private PushbackReader buffer;
	private String log;

	/**
	 * insieme caratteri di skip (include EOF) e inizializzazione
	 */
	private static final ArrayList<Character> skipChars;
	static {
		skipChars = new ArrayList<>();
		skipChars.add(' ');
		skipChars.add('\n');
		skipChars.add('\t');
		skipChars.add('\r');
	}

	/**
	 * insieme lettere e inizializzazione
	 */
	private static final ArrayList<Character> letters;
	static {
		letters = new ArrayList<>();
		for (int i = (int) 'a'; i <= (int) 'z'; i++)
			letters.add((char) i);
		for (int i = (int) 'A'; i <= (int) 'Z'; i++)
			letters.add((char) i);

		// DEBUG
		// for (Character c :
		// letters) {
		// System.out.println(c);
		// }
	}

	/**
	 * cifre e inizializzazione
	 */
	private static final ArrayList<Character> digits;
	static {
		digits = new ArrayList<>();
		for (int i = (int) '0'; i <= (int) '9'; i++)
			digits.add((char) i);

		// DEBUG
		// for (Character c :
		// digits) {
		// System.out.println(c);
		// }
	}

	/**
	 * mapping fra caratteri '+', '-', '*', '/', ';', '=' e il TokenType
	 * corrispondente
	 */
	private static final HashMap<Character, TokenType> charTypeHMap;
	static {
		charTypeHMap = new HashMap<>();
		charTypeHMap.put('+', TokenType.PLUS);
		charTypeHMap.put('-', TokenType.MINUS);
		charTypeHMap.put('*', TokenType.MULT);
		charTypeHMap.put('/', TokenType.DIVIDE);
		charTypeHMap.put(';', TokenType.SEMICOLON);
		charTypeHMap.put('=', TokenType.ASSIGN);

		// DEBUG
		// for (Character c :
		// charTypeHMap.keySet()) {
		// System.out.println("char: " + c + "\tToken type: " + charTypeHMap.get(c));
		// }
	}

	/**
	 * mapping fra le stringhe "print", "float", "int" e il TokenType corrispondente
	 */
	private static final HashMap<String, TokenType> keywordHMap;
	static {
		keywordHMap = new HashMap<>();
		keywordHMap.put("print", TokenType.PRINT);
		keywordHMap.put("float", TokenType.FLOAT_KW);
		keywordHMap.put("int", TokenType.INT_KW);

		// DEBUG
		// for (String kw :
		// keywordHMap.keySet()) {
		// System.out.println("keyword: " + kw + "\tToken type: " +
		// keywordHMap.get(kw));
		// }
	}

	/**
	 * Costruttore dello Scanner
	 * 
	 * @param fileName percorso assoluto o dalla radice del progetto
	 * @throws FileNotFoundException errore se il percorso indicato non c'é il file
	 */
	public Scanner(String fileName) throws FileNotFoundException {
		this.buffer = new PushbackReader(new FileReader(fileName));
		this.riga = 1;
		// inizializzare campi che non hanno inizializzazione
	}

	public Token nextToken() {
		// nextChar contiene il prossimo carattere dell'input (non consumato).
		try {
			char nextChar = peekChar(); // Catturate l'eccezione IOException e ritornate una LexicalException che la
										// contiene
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// Avanza nel buffer leggendo i carattere in skipChars
		// incrementando riga se leggi '\n'.
		// Se raggiungi la fine del file ritorna il Token EOF

		// Se nextChar e' in letters
		// return scanId()
		// che legge tutte le lettere minuscole e ritorna un Token ID o
		// il Token associato Parola Chiave (per generare i Token per le
		// parole chiave usate l'HashMap di corrispondenza

		// Se nextChar e' o in operators oppure
		// ritorna il Token associato con l'operatore o il delimitatore

		// Se nextChar e' in numbers
		// return scanNumber()
		// che legge sia un intero che un float e ritorna il Token INUM o FNUM
		// i caratteri che leggete devono essere accumulati in una stringa
		// che verra' assegnata al campo valore del Token

		// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
		// eccezione lessicale dicendo la riga e il carattere che la hanno
		// provocata.

		return null;
	}

	// private Token scanNumber()

	// private Token scanId()

	/**
	 * Read buffer consuming a character
	 * 
	 * @return character at first of queue
	 * @throws IOException error about non-existing input file
	 */
	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	/**
	 * Read buffer without consume
	 * 
	 * @return character at first of queue
	 * @throws IOException error about non-existing input file
	 */
	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
}