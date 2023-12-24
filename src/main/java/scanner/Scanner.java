package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

import java.util.ArrayList;
import java.util.HashMap;

import token.Token;
import token.TokenType;

public class Scanner {
	final char EOF = (char) -1;
	private int riga = 1;
	private PushbackReader buffer;
	private Token nextTk = null;

	/**
	 * insieme caratteri di skip (incluso EOF) e inizializzazione
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
	 * lettere alfanumeriche minuscole e maiuscole
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
	 * cifre numeriche
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
	 * associo i simboli di operazione +,-,*,\,...,; con il TokenType corrispondente
	 */
	private static final HashMap<String, TokenType> charTypeHMap;
	static {
		charTypeHMap = new HashMap<>();
		charTypeHMap.put("+", TokenType.PLUS);
		charTypeHMap.put("-", TokenType.MINUS);
		charTypeHMap.put("*", TokenType.MULT);
		charTypeHMap.put("/", TokenType.DIVIDE);
		charTypeHMap.put(";", TokenType.SEMICOLON);
		charTypeHMap.put("=", TokenType.ASSIGN);
		charTypeHMap.put("+=", TokenType.PLUS_ASSIGN);
		charTypeHMap.put("-=", TokenType.MINUS_ASSIGN);
		charTypeHMap.put("*=", TokenType.MULT_ASSIGN);
		charTypeHMap.put("/=", TokenType.DIVIDE_ASSIGN);
		charTypeHMap.put("++", TokenType.INCREMENT);
		charTypeHMap.put("--", TokenType.DECREMENT);

		// DEBUG
		// for (Character c : charTypeHMap.keySet())
		// System.out.println("char: " + c + "\tToken type: " + charTypeHMap.get(c));
	}

	/**
	 * associo le keyword con il TokenType corrispondente
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
	 * @param fileName percorso assoluto del file di testo da scannerizzare
	 * @throws FileNotFoundException errore se il percorso indicato non c'é il
	 *                               file.txt
	 */
	public Scanner(String fileName) throws FileNotFoundException {
		this.buffer = new PushbackReader(new FileReader(fileName));
	}

	public Token peekToken() throws LexicalException {
		if (this.nextTk == null)
			this.nextTk = nextToken();
		return nextTk;
	}

	public Token nextToken() throws LexicalException {
		if (this.nextTk != null) {
			Token tk = this.nextTk;
			this.nextTk = null;
			return tk;
		}

		Character c = null;
		try {
			c = peekChar();
		} catch (IOException e) {
			throw new LexicalException("Non funziona peekChar() alla riga " + riga, e);
		}

		// Avanza nel buffer scatando i caratteri in skipChars, incrementando riga se
		// legge '\n'.
		while (skipChars.contains(c)) {
			if (c == '\n')
				this.riga++;
			try {
				c = readAndPeekChar();
			} catch (IOException e) {
				throw new LexicalException("Non funziona readAndPeekChar() alla riga " + riga, e);
			}
		}

		// Se ha raggiunto la fine del file ritorna il Token EOF
		if (c == (char) -1)
			return new Token(TokenType.EOF, this.riga);

		// Se `c` é una lettere alfabetica, allora é una KEYWORD o ID
		if (letters.contains(c))
			return scanId();

		// Se `c` é presente in charTypeHMap, allora é un operatore o un delimitatore
		if (charTypeHMap.containsKey(c.toString()))
			return scanOp();

		// Se `c` é una cifra numerica, allora é INT_VAL o FLOAT_VAL
		if (digits.contains(c))
			return scanNumber();

		/*
		 * Altrimenti il carattere NON É UN CARATTERE LEGALE, bisogna sollevare
		 * un'eccezione lessicale
		 */
		throw new LexicalException("Il carattere '" + c + "' NON É UN CARATTERE LEGALE");
	}

	/**
	 * 
	 * @return un Token di tipo OPERATOR (SUM, MINUS, ...) o SEMICOLON
	 * @throws LexicalException ci sono caratteri non appartenenti a charTypeHMap
	 */
	private Token scanOp() throws LexicalException {
		Character fc, sc; // First Char, Second Char;

		try {
			fc = readChar();
		} catch (IOException e) {
			throw new LexicalException("Non funziona readChar() alla riga " + riga, e);
		}

		try {
			sc = peekChar();
		} catch (IOException e) {
			throw new LexicalException("Non funziona peekChar() alla riga " + riga, e);
		}

		String s = fc.toString() + sc.toString();
		if (charTypeHMap.containsKey(s)) {
			try {
				sc = readChar();
			} catch (IOException e) {
				throw new LexicalException("Non funziona readChar() alla riga " + riga, e);
			}
		} else
			s = fc.toString();

		return new Token(charTypeHMap.get(s), riga);
	}

	/**
	 * che legge tutte le lettere minuscole e maiuscole e cifre numeriche
	 * 
	 * @return un Token ID o il Token associato KEYWORD
	 * @throws LexicalException ci sono caratteri che non ci devono essere in un
	 *                          ID/KEYWORD
	 */
	private Token scanId() throws LexicalException {
		StringBuilder sb = new StringBuilder();
		Character c;

		try {
			c = peekChar();
		} catch (IOException e) {
			throw new LexicalException("Non funziona peekChar() alla riga " + riga, e);
		}

		while (letters.contains(c)) {
			sb.append(c);
			try {
				c = readAndPeekChar();
			} catch (IOException e) {
				throw new LexicalException("Non funziona readAndPeekChar() alla riga " + riga, e);
			}
		}

		TokenType tt = keywordHMap.get(sb.toString());

		if (tt != null)
			return new Token(tt, riga);
		else
			return new Token(TokenType.ID, riga, sb.toString());
	}

	/**
	 * legge sia un intero che un float
	 * 
	 * @return un Token INT_VAL o FLOAT_VAL
	 * @throws LexicalException ci sono caratteri al di fuori di quelle numeriche e
	 *                          il .
	 */
	private Token scanNumber() throws LexicalException {
		StringBuilder sb = new StringBuilder();
		Character c;

		try {
			c = peekChar();
		} catch (IOException e) {
			throw new LexicalException("Non funziona peekChar() alla riga " + riga, e);
		}

		while (digits.contains(c)) {
			sb.append(c);
			try {
				c = readAndPeekChar();
			} catch (IOException e) {
				throw new LexicalException("Non funziona readAndPeekChar() alla riga " + riga, e);
			}
		}

		if (c.equals('.')) {
			try {
				c = readChar();
			} catch (IOException e) {
				throw new LexicalException("Non funziona readChar() alla riga " + riga, e);
			}

			if (sb.length() == 0)
				return new Token(TokenType.FLOAT_VAL, riga, "0.0");
			else
				return new Token(TokenType.FLOAT_VAL, riga,
						formatNumber(sb).toString() + "." + scanFloat(sb).toString());
		}

		if (skipChars.contains(c) || charTypeHMap.containsKey(c.toString()))
			return new Token(TokenType.INT_VAL, riga, formatNumber(sb).toString());

		throw new LexicalException("Errore in scanNumber() alla riga " + riga + ", sono arrivato a '" + sb.toString()
				+ "' ma ho trovato '" + c + "'");
	}

	/**
	 * 
	 * @param integerPart
	 * @return
	 * @throws LexicalException
	 */
	private StringBuilder scanFloat(StringBuilder integerPart) throws LexicalException {
		StringBuilder sb = new StringBuilder();
		Character c;
		try {
			c = peekChar();
		} catch (IOException e) {
			throw new LexicalException("Non funziona peekChar() alla riga " + riga, e);
		}

		while (digits.contains(c)) {
			sb.append(c);
			try {
				c = readAndPeekChar();
			} catch (IOException e) {
				throw new LexicalException("Non funziona readAndPeekChar() alla riga " + riga, e);
			}
		}

		if (letters.contains(c))
			throw new LexicalException("Errore in scanNumber() alla riga " + riga + ", sono arrivato a '"
					+ integerPart.toString() + '.' + sb.toString()
					+ "' ma ho trovato '" + c + "'");

		return formatNumber(sb);
	}

	private StringBuilder formatNumber(StringBuilder sb) {
		if (sb == null)
			throw new IllegalArgumentException("L'argomento 'sb' dev'essere diverso da null");

		String s = sb.toString();
		if (s.equals(""))
			return sb.append("0");

		if (s.equals("0"))
			return sb;

		while (sb.toString().length() > 1 && sb.toString().charAt(0) == '0')
			sb = new StringBuilder(sb.substring(1));

		return sb;
	}

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

	private char readAndPeekChar() throws IOException {
		readChar();
		return peekChar();
	}
}
