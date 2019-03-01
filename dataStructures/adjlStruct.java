package dataStructures;
//
// CLASS: adjlStruct
//
/* Elementare Struktur zur Verkettung von Knoten und Kanten. D.h.
   alle Objekte, die sich von dieser Klasse ableiten (wie Knoten, Kanten), koennen
   miteinander verkettet werden */
public class adjlStruct {
	
	// Zeiger auf naechstes Element
	private adjlStruct next;
	
	/* PROCEDURE: addElement
	Element ans Ende der verketteten Liste haengen */
	public final void addElement(adjlStruct as) {
		adjlStruct help = this;
		while (help.next != null) help = help.next;
		help.next = as;
	}

	/* FUNCTION: hasNextElement 
	haengt an diesem Element ein weiteres ? */
	public final boolean hasNextElement() {
		if (next != null) return true;
		return false;
	}

	/* FUNCTION: nextElement
	naechstes Element zurueckliefern */
	public final adjlStruct nextElement() {
		return next;
	}

}

