package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.HashMap;

import token.Token;
import token.TokenType;

// import token.*;

public class Scanner {
	final char EOF = (char) -1;
	private int riga;
	private PushbackReader buffer;
	// private String log;

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

		//// DEBUG
		// for (Character c : letters)
		// System.out.println(c);
	}

	/**
	 * cifre e inizializzazione
	 */
	private static final ArrayList<Character> digits;
	static {
		digits = new ArrayList<>();
		for (int i = (int) '0'; i <= (int) '9'; i++)
			digits.add((char) i);

		//// DEBUG
		// for (Character c : digits)
		// System.out.println(c);
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
		// for (Character c : charTypeHMap.keySet())
		// System.out.println("char: " + c + "\tToken type: " + charTypeHMap.get(c));
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
		// for (String kw : keywordHMap.keySet())
		// System.out.println("keyword: " + kw + "\tToken type: " +
		// keywordHMap.get(kw));
	}

	/**
	 * Costruttore dello Scanner
	 * 
	 * @param fileName percorso assoluto o dalla radice del progetto
	 * @throws FileNotFoundException errore se il percorso indicato non c'é il file
	 * @throws LexicalException
	 */
	public Scanner(String fileName) throws LexicalException {
		try {
			this.buffer = new PushbackReader(new FileReader(fileName));
			this.riga = 1;
			// this.log = null;
			// inizializzare campi che non hanno inizializzazione
		} catch (FileNotFoundException e) {
			throw new LexicalException("File nel percorso '" + fileName + "' non trovato", e);
		}
	}

	public Token nextToken() throws LexicalException {
		Character c = peekChar();

		// Avanza nel buffer leggendo i carattere in skipChars
		// incrementando riga se leggi '\n'.
		// Se raggiungi la fine del file ritorna il Token EOF
		while (skipChars.contains(c)) {
			if (c == '\n')
				this.riga++;
			readChar();
			c = peekChar();
		}

		// check that `c` is EOF
		if (c == (char) -1)
			return new Token(TokenType.EOF, this.riga);

		// Se nextChar é in letters, return scanId(), che legge tutte le lettere
		// minuscole e ritorna un Token ID o il Token associato Parola Chiave (per
		// generare i Token per le parole chiave usate l'HashMap di corrispondenza
		if (letters.contains(c))
			return scanId();

		// Se nextChar e' o in operators oppure ritorna il Token associato con
		// l'operatore o il delimitatore
		if (charTypeHMap.containsKey(c))
			return new Token(charTypeHMap.get(readChar()), this.riga);

		// Se nextChar e' in numbers return scanNumber()che legge sia un intero che un
		// float e ritorna il Token INUM o FNUM i caratteri che leggete devono essere
		// accumulati in una stringa che verra' assegnata al campo valore del Token
		if (digits.contains(c))
			return scanNumber();

		// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una eccezione
		// lessicale dicendo la riga e il carattere che la hanno provocata.
		throw new LexicalException("Il carattere '" + c + "' NON E' UN CARATTERE LEGALE");
	}

	private Token scanId() throws LexicalException {
		StringBuilder sb = new StringBuilder();
		Character c = null;
		while (!skipChars.contains(peekChar())) {
			c = readChar();
			if (!letters.contains(c))
				throw new LexicalException(
						"Stavo leggendo un ID e sono arrivato a '" + sb.toString() + "' ma é capitato '" + c + "'");
			sb.append(c);
		}

		for (String regex : keywordHMap.keySet())
			if (sb.toString().matches(regex))
				return new Token(keywordHMap.get(regex), riga);

		return new Token(TokenType.ID, riga, sb.toString());
	}

	private Token scanNumber() throws LexicalException {
		StringBuilder sb = new StringBuilder();
		Character c;

		while (!skipChars.contains(peekChar())) {
			c = readChar();
			if (digits.contains(c)) {
				sb.append(c);
				continue;
			}
			if (c.equals('.')) {
				if (sb.length() == 0)
					throw new LexicalException(
							"Stai creando un valore float iniziando con il punto, devi mettere almeno una cifra intera.");
				sb.append(c);
				return new Token(TokenType.FLOAT_VAL, riga, sb.toString() + '.' + scanFloat(sb.toString()));
			}
			throw new LexicalException();
		}

		return new Token(TokenType.INT_VAL, riga, sb.toString());
	}

	private String scanFloat(String integerPart) throws LexicalException {
		StringBuilder sb = new StringBuilder();
		Character c;

		while (!skipChars.contains(peekChar())) {
			c = readChar();
			if (digits.contains(c)) {
				sb.append(c);
				continue;
			}
			throw new LexicalException("Stavo leggendo un FLOAT_VAL e sono arrivato a '" + integerPart + sb.toString()
					+ "' ma é capitato '" + c + "'");

		}

		return sb.toString();
	}

	/**
	 * Read buffer consuming a character
	 * 
	 * @return character at first of queue
	 * @throws LexicalException error about non-existing input file
	 */
	private char readChar() throws LexicalException {
		try {
			return ((char) this.buffer.read());
		} catch (IOException e) {
			throw new LexicalException("Errore in readChar()...", e);
		}
	}

	/**
	 * Read buffer without consume
	 * 
	 * @return character at first of queue
	 * @throws LexicalException error about non-existing input file
	 */
	private char peekChar() throws LexicalException {
		char c;
		try {
			c = (char) buffer.read();
			buffer.unread(c);
		} catch (IOException e) {
			throw new LexicalException("Errore in peekChar()...", e);
		}
		return c;
	}
}
