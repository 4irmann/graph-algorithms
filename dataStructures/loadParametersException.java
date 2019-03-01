package dataStructures;
//
// CLASS: loadParametersException
//
/* Wird bei Lade-Fehlern ausgeloest */

public class loadParametersException extends Exception {

	// URL (in String-Form), die den Fehler ausloeste
	private String url;

	/* CONSTRUCTOR:
	e = Fehlermeldung der vorangegangenen exception
	u = Fehlerausloesende url (in String-Form) */
	public loadParametersException (String e, String u) {
		super(e);
		url = u;
	}

	/* FUNCTION: getMessage
	Ueberschreibt getMessage in Oberklasse throwable */
	public String getMessage() {
		return "parameters loading error -> "+super.getMessage()+" : "+url+" !";
	}

}







