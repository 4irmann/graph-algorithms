package graphAlgorithms;
import dataStructures.*;
//
// CLASS: maximalFlow
//
/* Stellt Methoden zur Ermittlung des maximalen Flusses eines Netzwerks zur Verfuegung */

public class maximalFlow {

	private adjl a;					// diese Adjazenzliste wird zurueckgegeben
	private parentArray parent;		// zunehmende Pfade werden hier gespeichert
	private int visited[];			// visited speichert den Zustand eines Knoten und die Restkapazitaeten im zunehmenden Pfad 
									// besucht & Restkapazitaet >0, nicht besucht == 0
	private int p;					// speichert Knotenanzahl der uebergebenen Adjazenzmatrix
	private int delta;				// speichert von findPath ermitteltes deltaFluss
	private final static int infinite = 2147483647;	// int ist in Java eine 32 bit Integer

	/* FUNCTION: restGraph
	Bildet aus adjl a (global innerhalb von maximalFlow) Restgraph-Adjazenzliste und liefert
	diese zurueck. */
	private adjl restGraph() {
		
		// Knoten von Originalgraph in neuen Restgraph kopieren
		int k;
		adjl Gf = new adjl();
		for (k=0;k<p;k++) Gf.addVertex(((vertex) a.elementAt(k)).Clone());
		
		// Kanten in Restgraph eintragen
		int v,w;
		vertex ve; networkEdge ned,ned1; edge ed; adjlStruct as;
		for (k=0;k<p;k++) {
			ve = (vertex) a.elementAt(k);
			as = (adjlStruct) ve;
			while (as.hasNextElement()) {
				as = as.nextElement();
				ned = (networkEdge) as;
				v = ve.nr-1; w = ned.v2.nr-1;
				ned1 = (networkEdge) a.edgeAt(w,v);
				
				if (ned1 != null) {					// 1. cf((v,w)) = c((v,w)-f((v,w))+f((w,v))
					ed = new edge();
					ed.v2 = (vertex) Gf.elementAt(w);
					ed.weight = ned.capacity - ned.flow + ned1.flow;
					Gf.addEdge((vertex) Gf.elementAt(v),ed);
				}
				else {
					if (ned.flow < ned.capacity) {	// Noch Restkapazitaet vorhanden ? (ansonsten faellt Kante weg)
						ed = new edge();			// 2.1 cf((v,w) = c((v,w))-f((v,w))
						ed.v2 = (vertex) Gf.elementAt(w);
						ed.weight = ned.capacity-ned.flow;
						Gf.addEdge((vertex) Gf.elementAt(v),ed);
					}
					if (ned.flow > 0) {				// Fluss > 0 ? (ansonsten faellt Kante weg)
						ed = new edge();			// 2.2 cf((w,v) = f((v,w))
						ed.v2 = (vertex) Gf.elementAt(v);
						ed.weight = ned.flow;
						Gf.addEdge((vertex) Gf.elementAt(w),ed);
					}
				}
			}
		}
		
		// Start-/Endknoten in Restgraphen eintragen
		Gf.source = (vertex) Gf.elementAt(a.source.nr-1);
		Gf.target = (vertex) Gf.elementAt(a.target.nr-1);

		return Gf;
	}
		
	/* FUNCTION: visitBfs
	Hierbei handelt es sich um eine leicht abgeaenderte Implementierung einer iterativen Breitensuche (bfs).
	Diese sucht in einem Restgraph einen Pfad vom Startknoten s zum Zielknoten t.
	Falls ein Pfad gefunden wird, ist es einer mit einer minimalen Anzahl von Kanten (Eigenschaft bfs).
	Die Restkapazitaeten werden bei der Suche im visited-array unter den Indizes der Zielknoten
	der Kanten (mit den Restkapazitaeten) gespeichert. Falls ein Pfad zwischen s und t gefunden
	wurde, wird true, andernfalls false zurueckgeliefert. */
	private boolean visitBfs(vertex s,vertex t) {
		vertex v; edge ed; adjlStruct as;
		queue q = new queue(s);		// Startknoten in queue
		visited[s.nr-1] = infinite;	// Startknoten hat keine Kante zu einem Vorgaenger
		do {
			v = (vertex) q.pull(); 
			as = (adjlStruct) v;
			while (as.hasNextElement()) {	// solange Kanten vorhanden
				as = as.nextElement();
				ed = (edge) as;
				if (visited[ed.v2.nr-1] == 0) {			// Knoten noch nicht besucht (==0)
					parent.array[ed.v2.nr-1] = v.nr;	// Pfad eintragen
					visited[ed.v2.nr-1] = ed.weight;	// Gewicht bzw. Restkapazitaet eintragen, Knoten besucht
					if (ed.v2 == t) return true;		// t gefunden -> true zurueck (= vorzeitig abbrechen)
					q.push(ed.v2);						// ansonsten Zielknoten in Warteschlange
				}
			}
		} while(!q.empty());	// solange noch Knoten in Warteschlange
		return false;			// falls t nicht gefunden wurde, false zurueckliefern
	}

	/* FUNCTION: findPath
	Bildet zuerst einen Restgraph von adjl a (global in maximalFlow) und
	sucht anschliessend mittels visitBfs einen zunehmenden Pfad von s nach t 
	mit einer minimalen Anzahl von Kanten. Falls kein Pfad gefunden werden konnte,
	liefert findPath false zurueck, d.h. der maximal Fluss wurde erreicht.
	Ansonsten wird in dem zurueckgegebenen Pfad die minimale Restkapazitaet gesucht
	und in delta (global in maximalFLow) eingetragen. Anschliessend wird true 
	zurueckgegeben.*/
	private boolean findPath() {
		
		// Restgraph bilden
		adjl Gf = restGraph();

		// Daten fuer visitBsf initialisieren
		int k;
		for (k=0;k<p;k++) visited[k] = 0;
		for (k=0;k<p;k++) parent.array[k] = 0;
		
		// Pfad zwischen s und t im Restgraphen mittels Breitensuche suchen
		if (!visitBfs(Gf.source,Gf.target)) return false;	// keinen gefunden -> zrueck mit false
		else { // andernfalls, minimale Restkapazitaet im gefunden Pfad ermitteln und in delta eintragen:
			int i = Gf.target.nr-1;		
			delta = visited[i];
			while (i != Gf.source.nr-1) {
				if (visited[i] < delta) delta = visited[i];
				i = parent.array[i]-1;
			}
		} 
		return true; // Pfad gefunden
	}

	/* FUNCTION: edmondsKarp
	Implementierung eines Algorithmus zur Berechnung des maximalen Flusses in einem Netzwerk.
	Baut auf dem Algorithmus von Ford-Fulkerson auf. Ledgiglich die Funktion findPath ist in
	Edmonds-Karp anders realisiert. Zurueckgeliefert wird eine Adjazenzliste, die ein
	Netzwerk mit dem maximalen Fluss repraesentiert. */
	public adjl edmondsKarp(adjl ad) {
		
		//Anzahl der Knoten
		p = ad.size();	// p ist global in maximalFlow

		// Knoten-Nr des Zielknotens
		int t = ad.target.nr-1;

		// Daten initialisieren
		a = ad.Clone();			 // uebergebene Adjazenzliste in neue Adjazenzliste kopieren
		visited = new int[p];	 // noch keinen Knoten besucht
		parent = new parentArray(p);
		
		// Zunehmende Pfade in a mit deltaFluss suchen und deltaFluss in Pfad eintragen
		int k,j;
		networkEdge ned;
		while(findPath()) {	// Pfad suchen, delta ermitteln
			k = t; j = parent.array[k];	// Falls Pfad gefunden, diesen durchlaufen und dabei Fluesse aktualisieren
			while (j > 0) {		
				j--;
				ned = (networkEdge) a.edgeAt(j,k);	// Zu Vorwaertskanten wird delta addiert
				if (ned != null) ned.flow += delta;
				ned = (networkEdge) a.edgeAt(k,j);
				if (ned != null) ned.flow -= delta;	// Zu Rueckwaertskanten wird delta subtrahiert
				k = j; j = parent.array[k];			
			}
		}

		// Adjazenzliste mit maximalFlow zurueckliefern
		return a;
	}

}


