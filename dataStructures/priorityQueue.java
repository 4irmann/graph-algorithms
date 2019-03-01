package dataStructures;
//
// CLASS: priorityQueue
//
/* Hierbei handelt es sich um eine Teilimplementation des Abstrakten Datentyps Prioritaetswarteschlange.
   Die Implementation verwendet zur Speicherung der Prioritaeten einen binaeren heap. */

public class priorityQueue {
	
	// Variablen, Objekte
	private priorityQueueStruct pqs[];		// array bzw. heap von 1..N, bei dem in jeder priorityQueueStruct ein Objekt und die zugehoerige Prioritaet abgelegt wird
	private int size;						// Groesse der priorityQueue (= Anzahl der Elemente die die priorityQueue maximal aufnehmen kann) 
	private int elementCount;				// Zaehler fuer Elemente, die sich momentan in der priorityQueue befinden

	/* CONSTRUCTOR:
	siz = Groesse der priorityQeue (= Anzahl der Elemente die die priorityQueue maximal aufnehmen kann) */
	public priorityQueue(int siz) {
		pqs = new priorityQueueStruct[siz+1]; // siz+1, da heap von 1..N
		size = siz;
	}

	/* PROCEDURE: upHeap
	Stellt heap-Bedingungen von unten nach oben her (falls verletzt), wobei
	k = Ausgangsposition im heap (1..N) */
	private void upHeap(int k) {
		priorityQueueStruct v = pqs[k];
		if (k > 1) 
			while (pqs[k/2].priority <= v.priority) {
				pqs[k] = pqs[k/2];
				k = k/2;
				if (k == 1) break;
			}
		pqs[k] = v;
	}
		
	/* PROCEDURE: downHeap
	Stellt heap-Bedingungen von oben nach unter her (falls verletzt), wobei
	k = Ausgangsposition im heap (1..N) */
	private void downHeap(int k) { 
		int j;
		priorityQueueStruct v = pqs[k];
		while (k <= (elementCount/2)) {	// solange noch im Bereich der Knoten (Blaetter werden nicht behandelt)
			j = k+k;
			if (j<elementCount) 
				if (pqs[j].priority < pqs[j+1].priority) j++;
			if (v.priority >= pqs[j].priority) break;
			pqs[k] = pqs[j]; k=j;
		}
		pqs[k] = v;
	}
	
	/* FUNCTION: isElement
	Prueft, ob Object schon in priorityQueue vorhanden.
	Falls Object nicht vorhanden, wird <0 zurueckgeliefert, ansonsten
	der Index des gefunden Objects im heap */
	private int isElement(Object o) {
		for (int i=1; i<=elementCount;i++) 
			if (pqs[i].element == o) return i;
		return -1;
	}

	/* FUNCTION: updateLarger
	Fuegt entweder ein neues Object in die priorityQueue ein, oder
	ersetzt die Prioritaet eines schon vorhandenen Objects durch
	eine neue Prioritaet (vorausgesetzt, die neue Prioritaet ist groesser
	als die alte). Falls das Object eingefuegt bzw. seine Prioritaet aktualisiert
	werden konnte, wird true zurueckgegeben, ansonsten false. */
	public boolean updateLarger(Object o,int priority) {
		int index = isElement(o);		// ist Object schon in priorityQueue ?
		if (index < 0) {				// nein -> Object zu priorityQueue hinzufuegen
			if (elementCount < size) {	// noch Platz in priorityQueue ?
				 elementCount++;		// Elementanzahl in priorityQueue erhoehen
				 pqs[elementCount] = new priorityQueueStruct(o,priority); // neues Object ans Ende des heaps
				 upHeap(elementCount);	// heap-Bedingungen von unten nach oben wieder herstellen
				 return true;			// alles ok
			}
			else return false;			// kein Platz mehr
		}
		else {							// ja -> Object ist schon in priorityQueue
			if (pqs[index].priority < priority) {	// falls neu uebergebene priority groesser ist,
				pqs[index].priority = priority;		// alte durch neue ersetzen
				upHeap(index);						// heap-Bedingungen vom Index des Objects aus nach oben wieder herstellen
				return true;						// alles ok
			}
			else return false;						// falls neu uebergebene priority kleiner ist, false zurueckliefern
		}
	}

	/* FUNCTION: remove
	Liefert das Element mit der hoechsten Prioritaet aus der priorityQueue zurueck */
	public Object remove() {
		Object help;
		if (elementCount > 0) {			// sind ueberhaupt noch Elemente in der priorityQueue ?
			help = pqs[1].element;		// Object an Wurzel des heaps nach help retten
			pqs[1] = pqs[elementCount]; // letztes Element des heaps nach Wurzel kopieren
			pqs[elementCount] = null;	// letztes Element des heaps loeschen
			elementCount--;				// Anzahl der Elemente im heap verringern
			downHeap(1);				// heap-Bedingungen wieder herstellen
			return help;				// Object in help zurueckliefern
		}
		return null;					// keine Elemente im heap ? -> null zurueck
	}

	/* FUNCTION: empty
	Liefert true, falls priorityQueue leer ist */
	public boolean empty() {
		if (elementCount > 0) return false;
		return true;
	}

}

