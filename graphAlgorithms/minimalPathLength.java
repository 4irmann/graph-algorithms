package graphAlgorithms;
import dataStructures.*;
//
// CLASS: minimalPathLength
//
/* Stellt verschiedene Algorithmen zur Ermittlung von Pfaden minimaler Laenge (minimal path length (mpl))
   in einer gewichteten Adjazenzliste zur Verfuegung.
   Oberklasse ist priorityTraverse (wichtig fuer Djikstra). */

public class minimalPathLength extends priorityTraverse {
	
	/* FUNCTION: getPriority
	Gehoert zu Djikstra. Ist Funktion der abstrakten Klasse priorityTraverse.
	In dieser Funktion wird die Prioritaet fuer lpfs (list-priority-first-search)
	in Klasse priorityTraverse festgelegt. */
	int getPriority(vertex v,edge e) {
		return e.weight+priority[v.nr-1];
	}

	/* FUNCTION: dijkstra
	Implementierung des Algorithmus von Djikstra zur Ermittlung von Pfaden minimaler Laenge (minimal path length) 
	fuer gewichtete Graphen ausgehend von einem Startknoten. Die Richtung der Kanten der Adjazenzliste wird 
	beruecksichtigt.
	a = Adjazenzliste die den Graphen repraesentiert
	Liefert parentArray zurueck, das die Pfade minimaler Laenge repraesentiert. */
	public parentArray djikstra(adjl a) {
		return lpfs(a);
	}

	/* FUNCTION: floyd
	Implementierung des Algorithmus von Floyd zur Ermittlung von Pfaden minimaler Laenge (minimal path length)
	zwischen allen Knoten eines gerichteten gewichteten Graphen (Abstandsmatrix).
	Liefert Adjazenzliste zurueck, die alle Pfade minimaler Laenge repraesentiert. */
	public adjl floyd(adjl ad) {
		
		// Adjazenzliste in Adjazenzmatrix kopieren
		adjm a = new adjm(ad);
		
		// Knotenanzahl
		int p=a.size();
		
		// gesamte Matrix p^3-mal durchlaufen (Abstandsmatrix bilden)
		int k,w;
		edge ed;
		for(k=0;k<p;k++) 
			for (int i=0;i<p;i++)
				for (int j=0;j<p;j++) {
					if ((a.edgeAt(i,k) != null) && (a.edgeAt(k,j) != null)) {
						w = a.weightAt(i,k)+a.weightAt(k,j); 
						if (a.edgeAt(i,j) == null) {
							ed = new edge();
							ed.weight = w;
							a.setEdgeAt(i,j,ed);
						}
						else if (w < a.weightAt(i,j)) a.setWeightAt(i,j,w);
					}
				}
			
		// Adjazenzmatrix (Abstandsmatrix) in neue Adjazenzliste kopieren
		return new adjl(a);
	}

}

