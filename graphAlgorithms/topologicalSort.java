package graphAlgorithms;
import dataStructures.*;
//
// CLASS: topologicalSort 
//
/* Stellt Methoden zum topologischen Sortieren von gerichteten azyklischen Graphen zur Verfuegung */

public class topologicalSort {

	// Variablen,Daten
	private adjl sorted;		// Adjazenzliste, in der die Knoten in sortierter Reihenfolge abgelegt werden
	private boolean visited[];	// hier wird festgehalten, ob ein Knoten schon besucht wurde (true), oder nicht (false)
	private int j;				// Zaehler fuer Index in sorted
						  
	/* PROCEDURE: recursiveVisit
	Entspricht dem Tiefensuch-Verfahren (dfs). 
	Besucht alle Knoten in einer Zusammenhangskomponente. Dabei wird die
	Richtung der Kanten beruecksichtigt.
	Bei Beendigung von recursiveVisit, d.h. wenn alle Zielknoten des
	zu besuchenden Knotens rekursiv besucht wurden, wird der Knoten in
	die sorted-Adjazenzliste von hinten nach vorne eingetragen und der
	Zaehler j erniedrigt. */
	private void recursiveVisit(vertex k) {
		edge ed; vertex help;
		visited[k.nr-1] = true;			// Knoten als besucht markieren
		adjlStruct as = (adjlStruct) k;
		while(as.hasNextElement()) {	// solange noch Kanten an Knoten haengen
			as = as.nextElement();
			ed = (edge) as;
			if (!visited[ed.v2.nr-1]) recursiveVisit(ed.v2);	// die Knoten, zu denen die Kanten fuehren, rekursiv besuchen
		}
		help = k.Clone();
		if (help.text == null) help.text = (new Integer(help.nr)).toString();
		help.nr = j+1;
		sorted.setElementAt(help,j); j--;	// Knoten in sorted-Adjazenzliste von hinten nach vorne eintragen
	}

	/* FUNCTION: recursiveTopologicalSort
	Implementation eines Algorithmus zum topologischen Sortieren von gerichteten
	azyklischen Graphen. Liefert Adjazenzliste mit Knoten in sortierter Reihenfolge zurueck. */
	public adjl recursiveTopologicalSort(adjl a) {
		
		// Anzahl der Knoten
		int p = a.size();
		
		// j wird so initialisiert, dass es aufs letzte Element von sorted zeigt
		j = p-1;
		
		// Initialisierung
		sorted = new adjl();
		sorted.setSize(p);
		visited = new boolean[p];
		int k;
		for (k=0; k<p; k++) visited[k] = false;	// kein Knoten wurde besucht
		
		// alle Zusammenhangskomponenten rekursiv durchlaufen und sortieren
		for (k=0; k<p; k++) if (!visited[k]) recursiveVisit((vertex) a.elementAt(k)); 
		
		return sorted;
	}

}

