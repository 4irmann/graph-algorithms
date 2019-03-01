package dataStructures;
//
// CLASS: List
//
/* Verkettete Liste (mit elementaren Funktionen/Prozeduren) fuer Objekte vom Typ Object */

public class List {
	
	// Variablen, Objekte
	int elementCount;
	ListStruct top;
	ListStruct bottom;
	ListStruct link;

	/* CONSTRUCTOR: normal
	Fuegt uebergebenes Object gleich in die neue Liste ein */
	public List(Object o) {
		addElement(o);
	}

	// CONSTRUCTOR: leere Liste
	public List() {
	}

	/* PROCEDURE: addElement
	Haengt uebergebenes Object ans Ende der Liste */
	public final void addElement(Object o) {		
		ListStruct help;
		if (top == null) {
			top = new ListStruct(o);
			link = bottom = top;
			elementCount++;
		}
		else {
			help = top;
			top.next = new ListStruct(o);
			top = top.next;
			top.prev = help;
			elementCount++;
		}
	}

	/* PROCEDURE: push
	Siehe addElement */
	public final void push(Object o) {
		addElement(o);
	}

	/* FUNCTION: peekTop
	Liefert letztes Element der Liste zurueck */
	public final Object peekTop() {
		if (top != null) return top.data;
		return null;
	}

	/* FUNCTION: peekBottom
	Liefert erstes Element der Liste zurueck */
	public final Object peekBottom() {
		if (bottom != null) return bottom.data;
		return null;		
	}

	/* FUNCTION: empty
	Liefert bei leerer Liste true zurueck */
	public final boolean empty() {
		if (top == null) return true;
		return false;
	}

	/* PROCEDURE: resetElements
	Setzt den internen Zeiger bezueglich hasMoreElements, nextElement auf den Anfang der Liste */
	public final void resetElements() {
		link = bottom;
	}

	/* FUNCTION: hasMoreElements
	Dient zum Durchlaufen der Liste, liefert true, falls noch weitere
	Elemente vorhanden. Voraussetzung z.B. fuer einen Durchlauf durch die
	komplette Liste ist, dass mittels resetElements der interne Zeiger auf
	den Anfang der Liste gesetzt wurde */
	public boolean hasMoreElements() {
		if (link != null) return true;
		return false;
	}

	/* FUNCTION: nextElement
	Liefert das Element, auf welches der interne Zeiger (siehe resetElements,hasMoreElements)
	verweist. Anschliessend wird der interne Zeiger aufs nachfolgende Element gesetzt. */
	public Object nextElement() {
		if (link != null) {
			Object help = link.data;
			link = link.next; 
			return help;
		}
		else return null;
	}

}

