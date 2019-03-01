package dataStructures;
//
// CLASS: smsoftTextGraphParametersException
//
/* Wird bei Formatfehlern ausgeloest */

public class smsoftTextGraphParametersException extends Exception {
	
	/* CONSTRUCTOR:
	s = Fehlermeldung */
	public smsoftTextGraphParametersException(String s) {
		super(s);
	}

	/* FUNCTION: getMessage
	Ueberschreibt getMessage in Oberklasse throwable */
	public String getMessage() {
		return "smsoftText error -> "+super.getMessage();
	}

}
