package sortingAlgorithms;
import java.util.Vector;
//
// class: sortableVector
//
/** 
* Macht die Vector-Klasse durch diverse Sortiermethoden sortierbar. 
* Voraussetzung ist, das alle Objekte im Vector das interface <a href="compareable.html">compareable</a>
* implementiert haben, und darueber hinaus vom gleichen Typ sind. Ist dies nicht der Fall,
* so wird die Programmausfuehrung mit einer exception beendet. */

public class sortableVector extends Vector {

// *** quickSort

	// method: partition
	/** sortiert eine Quicksort-Partition */
	private int partition(int l,int r) {
		
		compareable v = (compareable) elementAt(r), help; 
		Object t;
		int i=l-1, j=r;
		
		do {
			do {
				i++;
				help = (compareable) elementAt(i);
			} 
			while (help.compared(v) == compareable.BELOW);
			do {
				if (j > i) j--; else break;
				help = (compareable) elementAt(j);
			} 
			while (help.compared(v) == compareable.ABOVE);
			t = elementAt(i); 
			setElementAt(elementAt(j),i); 
			setElementAt(t,j);					
		} 
		while (j>i);
		setElementAt(elementAt(i),j);		
		setElementAt(elementAt(r),i);		
		setElementAt(t,r);						
		return i;
	}
		
	// method: quickSort
	/** 
	* Rekursive Implementation des Quicksort-Algorithmus, entnommen aus [Algorithmen,Robert Sedgewick, 1992].<br>
	* Leistungsfaehigkeit: Quicksort benoetigt im Mittel ungefaehr 2NlnN Vergleiche.<br>
	* Gut geeignet als Mehrzweck-Sortierverfahren.
	* @param l linke Position im Vector, entspricht Startposition (0 <= l < N).
	* @param r rechte Position im Vector, entspricht Endposition (0 <= r < N).<br><br>
	* <dt> Der Vector wird von l bis r sortiert. */
	public void quickSort(int l,int r) {
		if (r>l) {							
			int i = partition(l,r);
			quickSort(l,i-1);
			quickSort(i+1,r);
		}
   	}

	// method: quickSort ohne Parameter 
	/**  
	* Arbeitet wie quickSort(int l,int r).<br> 
	* Der einzige Unterschied ist, dass der komplette Vector (0..N-1) sortiert wird.*/
	public void quickSort() {
		quickSort(0,size()-1);
	}

// *** heapSort
	
	/** Variable fuer heapSort, speichert Groesse des heaps */
	private int n;
	
	// method: downheap
	/** 
	* heap von einem Knoten nach unten aufbauen bzw. korrigieren 
	* @param k Position des Ausgangsknoten im heap.
	* @param l linke Position im Vector, entspricht Startposition. */
	private void downheap(int k,int l) { // l ist offset
		int j;
		compareable v = (compareable) elementAt(l+k-1),help;
		while (k <= (n/2)) {	// solange noch im Bereich der Knoten (Blaetter werden nicht behandelt)
			j = k+k;
			if (j<n) {
				help = (compareable) elementAt(l+j-1);
				if (help.compared((compareable) elementAt(l+j+1-1)) == compareable.BELOW) j++;
			}
			help = (compareable) elementAt(l+j-1);
			if ((v.compared(help) == compareable.ABOVE) ||
				(v.compared(help) == compareable.EQUAL)) break;
			setElementAt(elementAt(l+j-1),l+k-1); k=j;
		}
		setElementAt(v,l+k-1);
	}
	
	// method: heapSort
	/** 
	* Iterative Implementation des Heapsort-Algorithmus, entnommen aus [Algorithmen,Robert Sedgewick, 1992].<br>
	* Leistungsfaehigkeit: Heapsort benoetigt weniger als 2NlgN Vergleiche.
	* @param l linke Position im Vector, entspricht Startposition (0 <= l < N).
	* @param r rechte Position im Vector, entspricht Endposition (0 <= r < N).<br> <br>
	* <dt> Der Vector wird von l bis r sortiert. */
	public void heapSort(int l,int r) {
		
	// heap "Bottom-Up" aufbauen:
		n = r-l+1;			// Groesse des heaps = Anzahl der zu sortierenden Elemente
		for (int k=n/2; k>=1; k--) downheap(k,l);	// Blattebene braucht nicht beruecksichtigt zu werden (deshalb n/2)
		
	// Elemente aus dem heap in richtiger Reihenfolge entfernen:
		compareable v = (compareable) elementAt(r), help;		
		Object t;
		do {
			t = elementAt(l); setElementAt(elementAt(l+n-1),l); setElementAt(t,l+n-1);	// erstes und letztes Element im heap vertauschen (= Groesstes Element an letzte Position)
			n--; downheap(1,l);	// heap verkleinern und von der Wurzel aus neu aufbauen
		}
		while (n > 1);
	}

	// method: heapSort ohne Parameter 
	/**  
	* Arbeitet wie heapSort(int l,int r).<br> 
	* Der einzige Unterschied ist, dass der komplette Vector (0..N-1) sortiert wird.*/
	public void heapSort() {
		heapSort(0,size()-1);
	}

}

