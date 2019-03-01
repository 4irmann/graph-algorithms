package dataStructures;

//
// CLASS: parentArray
//
/* In diesem Objekt kann die Struktur von Baeumen gespeichert werden.
   Dabei werden die Kanten der Baeume in Form von int-Zeigern in einem int-array abgelegt. 
   Die int-Zeiger enthalten einen Index auf die Nummer des Vorgaengerknoten-1 (parent).
   Die Nummern der Knoten entsprechen somit den Indizes und Inhalten-1 des int-arrays. 
   Ist ein int-Zeiger == 0, so ist der Knoten mit Knotennummer == Index des int-Zeigers entweder
   Wurzel, oder kein Element des Baumes (auf jeden Fall besitzt er keinen Vorgaengernoten). 
   Ein parentArray kann auch die Struktur ganzer Waelder speichern. */
public class parentArray {
	
	// Variablen, Objekte
	private int elementCount;	// Groesse des parentArray (entspricht in der Regel der Anzahl aller Knoten)
	public int array[];			// eigentliches array

	// CONSTRUCTOR:
	public parentArray(int size) {
		array = new int[size];
		elementCount = size;
	}

	/* FUNCTION: size
	Liefert die Groesse des parentArray zurueck */
	public int size() {
		return elementCount;
	}

}

