package token;

public class Token {
	
	private int riga;
	private TokenType tipo;
	private String valore;

	public Token(TokenType tipo, int riga, String valore) {
		this.tipo = tipo;
		this.riga = riga;
		this.valore = valore;
	}

	public Token(TokenType tipo, int riga) {
		this.tipo = tipo;
		this.riga = riga;
		this.valore = null;
	}

	public int getRiga() {
		return this.riga;
	}

	public TokenType getTipo() {
		return this.tipo;
	}

	public String getValore() {
		return this.valore;
	}

	@Override
	public String toString() {
		if (valore != null)
			return "<" +
					tipo +
					", riga:" + riga +
					", valore:" + valore +
					'>';
		else
			return "<" +
					tipo +
					", riga:" + riga +
					'>';
	}
}
