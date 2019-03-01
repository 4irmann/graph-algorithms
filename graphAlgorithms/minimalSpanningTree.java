package graphAlgorithms;
import dataStructures.*;
import sortingAlgorithms.*;
//
// CLASS: minimalSpanningTree
//
/* Stellt verschiedene Algorithmen zur Ermittlung von minimal aufspannenden Baeumen (minimal spanning tree (mst))
   einer gewichteten Adjazenzliste zur Verfuegung.
   Oberklasse ist priorityTraverse (wichtig fuer Prim). */

public class minimalSpanningTree extends priorityTraverse {

	/* FUNCTION: find
	Gehoert zu kruskal. Hierbei handelt es sich um eine Implementierung eines leicht
	abgeaenderten Union-Find Abstract Data Type (ohne Parameteruebergabe von union).
	Mit Hilfe der Funktion kann man ermitteln, ob ein Kante innerhalb der gleichen
	Zusammenhangskomponente liegt, oder ob sie zu einer anderen fuehrt.
	i = Index des Startknotens der Kante
	j = Index des Endknotens der Kante
	parent = parent-array 
	Liefert true zurueck, wenn Kante in einer Zusammenhangskomponente liegt, andernfalls false */
	private boolean find(int i,int j,parentArray parent) {
		while (parent.array[i] > 0) i = parent.array[i]-1;	// von Startknoten Index i bis zur Wurzel laufen
		while (parent.array[j] > 0) j = parent.array[j]-1;	// von Startknoten Index j bis zur Wurzel laufen
		if (i != j) {	// sind Wurzeln gleich ?
			parent.array[i] = j+1;
			return false;	// nein -> Kante fuehrt zu anderer Komponente
		}				  
		return true;	// ja -> Kante liegt in einer Komponente
	}

	/* FUNCTION: kruskal
	Implementierung des Algorithmus von Kruskal zur Ermittlung von minimalen Spannbaeumen (minimal spanning tree) fuer
	zusammenhaengende gewichtete Graphen. Gerichtete Kanten der Adjazenzliste werden nicht 
	beruecksichtigt, sondern als normale Kanten betrachtet. Die Implementierung verwendet keine
	Hoehenbalancierung bzw. Pfadkomprimierung.
	a = Adjazenzliste die den Graphen repraesentiert
	Liefert Adjazenzliste zurueck, die den minimalen Spannbaum / die minimalen Spannbaeume repraesentiert. */
	public adjl kruskal(adjl a) {
		
		// Anzahl der Knoten
		int p = a.size();

		// parent-Array initialisieren
		// alle Knoten in neue Adjazenzliste (mst) eintragen
		// Kanten in einen eigenen Vector fuer die Kanten eintragen
		adjl mst = new adjl();
		parentArray parent = new parentArray(p);
		sortableVector edges = new sortableVector();	// sortierbarer Vector (array) fuer Kanten
		adjlStruct as; edge ed,ed1; vertex v;
		int i;
		for (i=0;i<p;i++) {
			parent.array[i] = 0;				// entspricht "findinit" des Union-Find-ADT (initialisiert alle Felder des parent-arrays auf 0)
			v = (vertex) a.elementAt(i);		// Knoten nach v
			mst.addVertex(v.Clone());			// Knoten "clonen" und zum mst hinzufuegen
			as = (adjlStruct) v;				// hier werden alle Kanten in einen sortierbaren Vector eingetragen
			while (as.nextElement() != null) {
				ed = (edge) as.nextElement();
				ed.v1 = v;						// zusaetzlich wird v1 initialisiert (zeigt auf Knoten, an dem die Kante haengt)
				edges.addElement(ed);
				as = as.nextElement();
			}
		}
		
		// Vector (mit allen Kanten) nach Gewicht der Kanten sortieren
		edges.quickSort();
		
		// eigentlicher Algorithmus, ruft fuer jede Kante in der original-Adjazenzliste die Funktion find auf
		for (i=0;i<edges.size();i++) {
			ed = (edge) edges.elementAt(i);
			if (!find(ed.v1.nr-1,ed.v2.nr-1,parent)) {					// falls Kante nicht in einer Zusammenhangskomponente liegt,
				ed1 = ed.Clone();										// wird sie eingefuegt
				ed1.v2 = (vertex) mst.elementAt(ed.v2.nr-1);
				mst.addEdge((vertex) mst.elementAt(ed.v1.nr-1),ed1);
			}
		}
		return mst;
	}

	/* FUNCTION: getPriority
	Gehoert zu Prim. Ist Funktion der abstrakten Klasse priorityTraverse.
	In dieser Funktion wird die Prioritaet fuer lpfs (list-priority-first-search)
	in Klasse priorityTraverse festgelegt. */
	int getPriority(vertex v,edge e) {
		return e.weight;	// Prioritaet = Gewicht
	}

	/* FUNCTION: prim
	Implementierung des Algorithmus von Prim zur Ermittlung von minimalen Spannbaeumen (minimal spanning tree) fuer
	zusammenhaengende gewichtete Graphen. Die Richtung der Kanten der Adjazenzliste wird beruecksichtigt.
	a = Adjazenzliste die den Graphen repraesentiert
	Liefert parentArray zurueck, das den minimalen Spannbaum / die minimalen Spannbaeume repraesentiert. */
	public parentArray prim(adjl a) {
		return lpfs(a);
	}

}

