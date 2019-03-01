package graphAlgorithms;
import dataStructures.*;
//
// CLASS: elementaryTraverse
//
/* Stellt elementare Methoden zum Durchlaufen/Durchsuchen von Graphen zur Verfuegung */

public class elementaryTraverse {
	
	// visited speichert den Zustand eines Knoten (besucht true, nicht besucht false)
	private boolean visited[];

	// fuer bfs/dfs-Baum (besuchte Knoten in Baumdarstellung)
	private parentArray parent; 

	/* PROCEDURE: visit
	Besucht alle Knoten in der Zusammenhangskomponente des uebergebenen Knoten. 
	Entweder nach dem Breitensuch-Verfahren (bfs) oder nach dem Tiefensuch-Verfahren (dfs).
	Die Richtung der Kanten wird dabei beruecksichtigt.
	l = Speichertyp (pipe (bfs) oder stack (dfs))
	v = Startknoten */
	private void visit(List l,vertex v) {
		edge ed; adjlStruct as;
		l.addElement(v); visited[v.nr-1] = true; // Startknoten in Speicher (pipe,Stack) einfuegen und als besucht markieren
		do {
			v = (vertex) l.nextElement();	// Knoten aus Speicher (pipe,stack) holen 
			as = (adjlStruct) v;
			while (as.hasNextElement()) {	// solange Kanten an Knoten haengen
				as = as.nextElement();
				ed = (edge) as;
				if (!visited[ed.v2.nr-1]) {	// Knoten schon besucht ?
					l.addElement(ed.v2); visited[ed.v2.nr-1]=true;	// Knoten in Speicher einfuegen (pipe, stack) und als besucht markieren
					parent.array[ed.v2.nr-1] = v.nr;				// Knoten in parentArray eintragen
				}
			}
		} while(!l.empty());	// solange Speicher (pipe,stack) nicht leer
	}
   
	/* FUNCTION: traverseAdjl
	Durchsucht bzw. durchlaeuft eine Adjazenzliste, entweder nach dem 
	Breitensuch-Verfahren (bfs) oder nach dem Tiefensuch-Verfahren (dfs).
	Die Richtung der Kanten wird dabei beruecksichtigt.
	l = Speichertyp (pipe (bfs) oder stack (dfs))
	a = zu durchsuchende Adjazenzliste
	zurueckgegeben wird ein parentArray mit dem entsprechenden Suchbaum / Suchbaeumen */
	private parentArray traverseAdjl(List l,adjl a) {
		
		// Anzahl der Knoten 
		int p = a.size();

		// tree und visited-Array initialisieren
		parent = new parentArray(p);
		visited = new boolean[p];
							
		// Alle Knoten/Kanten durchlaufen
		for (int i=0;i<p;i++) 
			if (!visited[i]) visit(l,(vertex) a.elementAt(i)); // Knoten Element von V3 ? ja -> Nachbarn von Knoten besuchen usw.
			
		return parent;
	}
   	
	/* FUNCTION: bfs
	Durchlaeuft eine Adjazenzliste nach dem Breitensuch-Verfahren bfs (breadth-first-search).
	Dabei wird die Richtung der Kanten beruecksichtigt.
	a = zu durchlaufende Adjazenzliste
	Liefert bfs-tree(s) in Form eines parentArray zurueck */
	public parentArray bfs(adjl a) {
		return traverseAdjl(new queue(),a);
	}

	/* FUNCTION: dfs
	Durchlaeuft eine Adjazenzliste nach dem Tiefensuchverfahren dfs (depth-first-search).
	Dabei wird die Richtung der Kanten beruecksichtigt.
	a = Adjazenzliste
	Liefert dfs-tree(s) in Form eines parentArray zurueck */
	public parentArray dfs(adjl a) {
		return traverseAdjl(new stack(),a);
	}

}
