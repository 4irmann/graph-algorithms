package graphAlgorithms;
import dataStructures.*;
//
// ABSTRACT CLASS: priorityTraverse
//
/* Stellt elementare Methoden zum Durchlaufen/Durchsuchen von Graphen nach Prioritaeten zur Verfuegung. 
   Die Funktion, welche die Prioritaet fuer eine Kante liefert, muss von Unterklassen in der 
   abstrakten Methode "getPriority" festgesetzt/implementiert werden ! */

abstract class priorityTraverse extends Object {
	
	// Variablen, Objekte
	private priorityQueue pq;	// priorityQueue zum Bereitstellen des Knotens mit der hoechsten Prioritaet 
	int priority[];				// falls > 0 besucht, < 0 benachbart, -infinite nicht besucht und nicht benachbart
	private parentArray parent;	// parentArray fuer Suchbaum, der zurueckgeliefert wird
	private final static int infinite = 2147483647;	// int ist in Java eine 32 bit Integer
	
	/* FUNCTION: getPriority
	Funktion durch die Prioritaet fuer lpfs festgelegt wird (siehe Prim, Djikstra). 
	v = Ausgangsknoten
	e = Kante, die vom Ausgangsknoten zum Zielknoten fuehrt */
	abstract int getPriority(vertex v,edge e);	

	/* PROCEDURE: visit
	Besucht alle Knoten in der Zusammenhangskomponente des uebergebenen Knoten, nach
	dem Prioritaets-Suchverfahren (priority-first-search (pfs)). Die Richtung der Kanten
	wird dabei beruecksichtigt.
	v = Startknoten 
	Anmerkung fuer Prim und Djikstra:
	Fuer das array priority[] gilt nach Beendigung von visit:
	Fuer Prim: priority[k] = g(k,parent[k]) = Gewicht der Kante zwischen Knoten k und Vorgaenger von k.
	Fuer Djikstra: priority[k] = d(w,k), w Wurzel = minimaler Abstand zwischen Wurzel und Knoten k
	Fuer beide: 0 fuer parent[k] = 0 (d.h. k ist Wurzel)
	priority[] wird von lpfs in dieser Implemtierung nicht ausgewertet, lediglich das parentArray
	wird zurueckgeliefert. */
	private void visit(vertex v) {
		adjlStruct as;
		edge ed;

		pq.updateLarger(v,infinite);		// Startknoten in noch leere priorityQueue einfuegen
		do {
			v = (vertex) pq.remove();		// naechsten Knoten aus priorityQueue
			priority[v.nr-1] = -priority[v.nr-1];	// Knoten von benachbart in besucht umwandeln
			if (priority[v.nr-1] == infinite) priority[v.nr-1] = 0;	// ist Knoten gleich Startknoten, dann ist Prioritaet = 0 (Djikstra)
			as = (adjlStruct) v;
			while (as.hasNextElement()) {	// solange noch Kanten vorhanden
				as = as.nextElement();
				ed = (edge) as;
				if (priority[ed.v2.nr-1] < 0) // ist Zielknoten noch nicht besucht (<0) ?
					if (pq.updateLarger(ed.v2,-getPriority(v,ed))) { // versuchen Zielknoten in priorityQueue einzufuegen, 
																	 // dabei Prioritaet der Funktion getPriority invertieren (*-1) -> klein = gross u.umgekehrt
						priority[ed.v2.nr-1] = -getPriority(v,ed);	 // Prioritaet der Funktion getPriority invertieren und in priority[] fuer Zielknoten eintragen
						parent.array[ed.v2.nr-1] = v.nr;			 // Vorgaenger vom Zielknoten in parentArray eintragen
					}
			}
		} while (!pq.empty());	// wiederholen, solange Knoten in der priorityQueue
	}

	
	/* FUNCTION: lpfs (list-priority-first-search)
	Durchlaeuft eine Adjazenzliste nach dem Prioritaets-Suchverfahren. Die Richtung
	der Kanten wird dabei beruecksichtigt. Zurueckgeliefert wird ein parentArray, das
	den Suchbaum / die Suchbaeume enthaelt. */
	parentArray lpfs(adjl a) {
		
		// Anzahl der Knoten
		int p = a.size();
		
		// Initialisierung
		int k;
		pq = new priorityQueue(p);	
		priority = new int[p];
		parent = new parentArray(p);
		for (k=0;k<p;k++) priority[k] = -infinite;	// alle Knoten werden Anfangs als nicht besucht initialisiert
		
		// Alle Knoten bzw. Zusammenhangskomponenten durchlaufen
		for (k=0;k<p;k++) 
			if (priority[k] == -infinite) visit((vertex) a.elementAt(k));	

		return parent;	// parentArray zurueckliefern
	}

}

