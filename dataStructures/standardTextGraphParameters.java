package dataStructures;
//
// CLASS: standardTextGraphParameters
//
/* Speichert Graphenparameter in einem Format aehnlich dem Text-Exportformat des 
   Programmes Graf v1.3h von smsoft. In diesem Format koennen gerichtete, gewichtete Graphen 
   und Netzwerke gespeichert werden. */

public class standardTextGraphParameters {
	
	// Eigentliche Graphenparameter in Form eines Strings
	public String text;

	// CONSTRUCTOR:
	public standardTextGraphParameters(String s) {
		text = s;
	}

}

