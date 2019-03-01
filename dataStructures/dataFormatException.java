package dataStructures;
//
// CLASS: dataFormatException
//
/* Wird z.B. von Importfiltern bei Datenformat-Fehlern ausgeloest */

public class dataFormatException extends Exception {
	
	// Variablen
	private int tokenCount;
	
	/* CONSTRUCTOR:
	s = Fehlermeldung
	t = token-Zaehler */
	public dataFormatException(String s,int t) {
		super(s);		// errorMessage mittels Konstruktor von Oberklasse eintragen lassen
		tokenCount = t;
	}

	/* FUNCTION: getMessage
	Ueberschreibt getMessage in Oberklasse throwable */
	public String getMessage() {
		Integer i = new Integer(tokenCount);
		return super.getMessage()+" : at token "+i.toString()+" !";
	}

}

