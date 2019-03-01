package dataStructures;
//
// CLASS: queue
//
/* Implementierung des Abstrakten Datentyps Warteschlange (FIFO). Kann Ojekte vom Typ Object aufnehmen.
   Oberklasse ist List. */

public class queue extends List {
		
	/* CONSTRUCTOR: normal
	Fuegt uebergebenes Object gleich in neue Warteschlange ein */
	public queue(Object o) {
		super(o);
	}

	// CONSTRUCTOR: leere Warteschlange
	public queue() {
	} 

	/* FUNCTION: hasMoreElements
	Liefert true zurueck, falls noch Elemente in Warteschlange (siehe auch empty in Oberklasse List).
	Ueberschreibt hasMoreElements in Oberklasse List. */
	public boolean hasMoreElements() {
		if (!empty()) return true;
		return false;
	}

	/* FUNCTION: nextElement
	Liefert naechstes Element aus Warteschlange, anschliessend
	wird das Element aus der Warteschlange entfernt.
	Ueberschreibt nextElement in Oberklasse List. */
	public final Object nextElement() {
		if (hasMoreElements()) {
			Object o = bottom.data;
			bottom = bottom.next;
			if (bottom == null) top = link = bottom;
			else bottom.prev = null;
			elementCount--;
			return o;
		}
		else return null;
	}
	
	/* FUNCTION: pull
	Siehe nextElement */
	public final Object pull() {
		return nextElement();
	}

	/* FUNCTION: peek
	Funktioniert wie nextElement, mit dem Unterschied, dass das Element
	nicht aus der Warteschlange entfernt wird. */
	public final Object peek() {
		return peekBottom();
	}

}





