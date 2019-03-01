package dataStructures;
//
// CLASS: stack
//
/* Implementierung des Abstrakten Datentyps Kellerspeicher (LIFO). Kann Ojekte vom Typ Object aufnehmen.
   Oberklasse ist List. */

public class stack extends List {

	/* CONSTRUCTOR: normal
	Fuegt uebergebenes Object gleich in neuen Kellerspeicher ein */
	public stack(Object o) {
		super(o);
	}
	
	// CONSTRUCTOR: leerer Kellerspeicher
	public stack() {
	}

	/* FUNCTION: hasMoreElements
	Liefert true zurueck, falls noch Elemente in Kellerspeicher (siehe auch empty in Oberklasse List).
	Ueberschreibt hasMoreElements in Oberklasse List. */
	public boolean hasMoreElements() {
		if (!empty()) return true;
		return false;
	}

	/* FUNCTION: nextElement
	Liefert naechstes Element aus Kellerspeicher, anschliessend
	wird das Element aus dem Kellerspeicher entfernt.
	Ueberschreibt nextElement in Oberklasse List. */
	public final Object nextElement() {
		if (hasMoreElements()) {
			Object o = top.data;
			top = top.prev;
			if (top == null) bottom = link = top;
			else top.next = null;
			elementCount--;
			return o;
		}
		else return null;
	}

	/* FUNCTION: pop
	Siehe nextElement */
	public final Object pop() {
		return nextElement();
	}

	/* FUNCTION: peek
	Funktioniert wie nextElement, mit dem Unterschied, dass das Element
	nicht aus dem Kellerspeicher entfernt wird. */
	public final Object peek() {
		return peekTop();
	}

}

