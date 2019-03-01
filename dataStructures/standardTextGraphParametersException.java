package dataStructures;
//
// CLASS: standardTextGraphParametersException
//
/* Wird bei Formatfehlern ausgeloest */

public class standardTextGraphParametersException extends Exception {

	/* CONSTRUCTOR:
	s = Fehlermeldung */
	public standardTextGraphParametersException(String s) {
		super(s);
	}

	/* FUNCTION: getMessage
	Ueberschreibt getMessage in Oberklasse throwable */
	public String getMessage() {
		return "standardText error -> "+super.getMessage();
	}

}


