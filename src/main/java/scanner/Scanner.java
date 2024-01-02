package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

import java.util.ArrayList;
import java.util.HashMap;

import token.Token;
import token.TokenType;

/**
 * componente per la scansione di file di testo di input per generare token
 */
public class Scanner {
	private int riga = 1;
	private PushbackReader buffer;
	private Token nextTk = null;

	/**
	 * set di caratteri da saltare nella fase di scansione
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
	 * set di caratteri alfabetici minuscoli e maiuscoli
	 */
	private static final ArrayList<Character> letters;
	static {
		letters = new ArrayList<>();
		for (int i = (int) 'a'; i <= (int) 'z'; i++)
			letters.add((char) i);
		for (int i = (int) 'A'; i <= (int) 'Z'; i++)
			letters.add((char) i);
	}

	/**
	 * set di cifre numeriche
	 */
	private static final ArrayList<Character> digits;
	static {
		digits = new ArrayList<>();
		for (int i = (int) '0'; i <= (int) '9'; i++)
			digits.add((char) i);
	}

	/**
	 * map di simboli di operazione, di assegnamento e ';' con il TokenType
	 * corrispondente
	 */
	private static final HashMap<String, TokenType> opAssHM;
	static {
		opAssHM = new HashMap<>();
		opAssHM.put("+", TokenType.PLUS);
		opAssHM.put("-", TokenType.MINUS);
		opAssHM.put("*", TokenType.MULT);
		opAssHM.put("/", TokenType.DIVIDE);
		opAssHM.put(";", TokenType.SEMICOLON);
		opAssHM.put("=", TokenType.ASSIGN);
		opAssHM.put("+=", TokenType.PLUS_ASSIGN);
		opAssHM.put("-=", TokenType.MINUS_ASSIGN);
		opAssHM.put("*=", TokenType.MULT_ASSIGN);
		opAssHM.put("/=", TokenType.DIVIDE_ASSIGN);
	}

	/**
	 * map of keyword with the corresponding TokenType
	 */
	private static final HashMap<String, TokenType> keywordsHM;
	static {
		keywordsHM = new HashMap<>();
		keywordsHM.put("print", TokenType.PRINT);
		keywordsHM.put("float", TokenType.FLOAT_KW);
		keywordsHM.put("int", TokenType.INT_KW);
	}

	/**
	 * Scanner constructor that sets the path where the input file is located
	 * 
	 * @param fileName absolute path of the text file to be scanned
	 * @throws FileNotFoundException error if the path indicated does not contain
	 *                               the file
	 */
	public Scanner(String fileName) throws FileNotFoundException {
		this.buffer = new PushbackReader(new FileReader(fileName));
	}

	/**
	 * read token without consuming
	 * 
	 * @return
	 * @throws LexicalException
	 * @throws IOException
	 */
	public Token peekToken() throws LexicalException, IOException {
		if (this.nextTk == null)
			this.nextTk = nextToken();
		return nextTk;
	}

	/**
	 * reads the token while consuming it
	 * 
	 * @return Token corrente
	 * @throws LexicalException
	 * @throws IOException
	 */
	public Token nextToken() throws LexicalException, IOException {
		if (this.nextTk != null) {
			Token tk = this.nextTk;
			this.nextTk = null;
			return tk;
		}

		Character c = null;
		c = peekChar();

		// Avanza nel buffer scatando i caratteri in skipChars, incrementando riga se
		// legge '\n'.
		while (skipChars.contains(c)) {
			if (c == '\n')
				this.riga++;
			readChar();
			c = peekChar();
		}

		// Se ha raggiunto la fine del file ritorna il Token EOF
		if (c == (char) -1)
			return new Token(TokenType.EOF, this.riga);

		// Se `c` é una lettere alfabetica, allora é una KEYWORD o ID
		if (letters.contains(c))
			return scanId();

		// Se `c` é presente in opAssHM, allora é un operatore o un delimitatore
		if (opAssHM.containsKey(c.toString()))
			return scanOp();

		// Se `c` é una cifra numerica, allora é INT_VAL o FLOAT_VAL
		if (digits.contains(c) || c == '.')
			return scanNumber();

		/*
		 * Altrimenti il carattere NON É UN CARATTERE LEGALE, bisogna sollevare
		 * un'eccezione lessicale
		 */
		throw new LexicalException("Il carattere '" + c + "' NON É UN CARATTERE LEGALE");
	}

	/**
	 * legge uno o due caratteri per riconoscere l'operatore
	 * 
	 * @return Token di tipo operatore (SUM, MINUS, ...) o SEMICOLON
	 * @throws LexicalException
	 * @throws IOException
	 */
	private Token scanOp() throws LexicalException, IOException {
		Character fc, sc; // First Char, Second Char;

		fc = readChar();

		sc = peekChar();

		String s = fc.toString() + sc.toString();
		if (opAssHM.containsKey(s))
			readChar();
		else
			s = fc.toString();

		return new Token(opAssHM.get(s), riga);
	}

	/**
	 * legge tutte le lettere minuscole e maiuscole e cifre numeriche
	 * 
	 * @return un Token ID o il Token associato KEYWORD
	 * @throws LexicalException ci sono caratteri che non ci devono essere in un
	 *                          ID/KEYWORD
	 * @throws IOException      errore riguardo a input file non esistente
	 */
	private Token scanId() throws LexicalException, IOException {
		StringBuilder sb = new StringBuilder();
		Character c;

		c = peekChar();

		do {
			sb.append(c);
			readChar();
			c = peekChar();
		} while (letters.contains(c) || digits.contains(c));

		TokenType tt = keywordsHM.get(sb.toString());

		if (tt != null)
			return new Token(tt, riga);
		else
			return new Token(TokenType.ID, riga, sb.toString());
	}

	/**
	 * legge sia un intero che un float
	 * 
	 * @return un Token INT_VAL o FLOAT_VAL
	 * @throws LexicalException ci sono caratteri al di fuori di quelle numeriche
	 *                          e/o il '.'
	 * @throws IOException      errore riguardo a input file non esistente
	 */
	private Token scanNumber() throws LexicalException, IOException {
		StringBuilder sb = new StringBuilder();
		Character c;

		c = peekChar();

		if (c == '.')
			throw new LexicalException("Non puoi creare un numero iniziando con il punto");

		do {
			sb.append(c);
			readChar();
			c = peekChar();
		} while (digits.contains(c));

		if (c.equals('.')) {
			readChar();
			return new Token(TokenType.FLOAT_VAL, riga,
					sb.toString() + c + scanFloat(sb).toString());
		}

		if ((skipChars.contains(c) || opAssHM.containsKey(c.toString())))
			if (sb.length() > 1 && sb.charAt(0) == '0')
				throw new LexicalException(
						"Errore in scanNumber() alla riga " + riga
								+ ", non puoi scrivere un numero intero con uno zero davanti");
			else
				return new Token(TokenType.INT_VAL, riga, sb.toString());

		throw new LexicalException(
				"Errore in scanNumber() alla riga " + riga + ", sono arrivato a '" + sb.toString()
						+ "' ma ho trovato '" + c + "'");
	}

	/**
	 * legge i caratteri come decimali di un numero (dopo il '.')
	 * 
	 * @param integerPart parte intera del numero
	 * @return parte decimale del numero
	 * @throws LexicalException
	 * @throws IOException
	 */
	private StringBuilder scanFloat(StringBuilder integerPart) throws LexicalException, IOException {
		StringBuilder sb = new StringBuilder();
		Character c;

		c = peekChar();

		while (digits.contains(c)) {
			sb.append(c);
			readChar();
			c = peekChar();
		}

		if (letters.contains(c))
			throw new LexicalException("Errore in scanFloat() alla riga " + riga + ", sono arrivato a '"
					+ integerPart.toString() + '.' + sb.toString()
					+ "' ma ho trovato una lettera alfabetica '" + c + "'");

		if (sb.toString().length() > 5)
			throw new LexicalException("Errore in scanFloat() alla riga " + riga
					+ ", il valore float puó contenere fino a 5 cifre decimali numeriche");

		return sb;
	}

	/**
	 * legge il buffer consumando un carattere
	 * 
	 * @return il primo carattere in coda
	 * @throws IOException errore riguardo a input file non esistenti
	 */
	private char readChar() throws IOException {
		try {
			return ((char) this.buffer.read());
		} catch (IOException e) {
			throw new IOException("Non funziona readChar() alla riga " + riga, e.getCause());
		}
	}

	/**
	 * legge il buffer senza consumare il carattere
	 * 
	 * @return il primo carattere in coda
	 * @throws IOException errore riguardo a input file non esistenti
	 */
	private char peekChar() throws IOException {
		char c;
		try {
			c = (char) buffer.read();
			buffer.unread(c);
		} catch (IOException e) {
			throw new IOException("Non funziona peekChar() alla riga " + riga, e.getCause());
		}
		return c;
	}
}
