package graphAlgorithms;
import dataStructures.*;
//
// CLASS: stronglyConnectedComponents
// 
/* Stellt Methoden zur Ermittlung der starken Zusammenhangskomponenten eines gerichteten Graphen zur Verfuegung */

public class stronglyConnectedComponents {
	
	// Variablen, Daten
	private boolean visited[];				// hier wird festgehalten, ob ein Knoten schon besucht wurde (true), oder nicht (false)
	private int j;							// Index fuer terminationOrder
	private boolean buildTerminationOrder;	// hier wird festgelegt, ob terminationOrder gebildet werden soll oder nicht
	private int	terminationOrder[];			// zum Speichern der Terminationsreihenfolge fuer recursiveSCC
	private parentArray parent;				// in diesem parentArray werden alle starken Zusammenhangskomponenten abgelegt
	
	/* PROCEDURE: recursiveVisit
	Entspricht dem Tiefensuch-Verfahren (dfs).
	Besucht alle Knoten in einer Zusammenhangskomponente ausgehend von einem
	Startknoten k. Dabei wird die Richtung der Kanten beruecksichtigt.
	Falls buildTerminationOrder == true:
	Bei Beendigung von recursiveVisit, d.h. wenn alle Zielknoten des
	zu besuchenden Knotens rekursiv besucht wurden, wird die Nummer des Knoten in
	das terminationOrder-array von hinten nach vorne eingetragen und der Index
	Zaehler j erniedrigt. 
	Falls buildTerminationOrder == false:
	Bei Beendigung von recursiveVisit, d.h. wenn alle Zielknoten des zu
	besuchenden Knotens rekursiv besucht wurden, wird die Kante zwischen
	aktuellem Knoten und vorher besuchtem Knoten (lastVisited) in ein parentArray eingetragen,
	in dem alle starken Zusammenhangskomponenten gespeichert werden */
	private void recursiveVisit(vertex lastVisited,vertex k) {
		edge ed; 
		visited[k.nr-1] = true;			// Knoten als besucht markieren
		adjlStruct as = (adjlStruct) k;
		while(as.hasNextElement()) {	// solange noch Kanten vorhanden
			as = as.nextElement();
			ed = (edge) as;
			if (!visited[ed.v2.nr-1]) recursiveVisit(k,ed.v2);	// falls Zielknoten noch nicht besucht, rekursiv besuchen
		}
		if (buildTerminationOrder) {		// soll Terminationreihenfolge gebildet werden ?
			terminationOrder[j] = k.nr-1;	// ja -> Knotennummer in terminationOrder-array eintragen
			j--;							// Index-Zaehler erniedrigen
		} 
		else 
			if (lastVisited == null) parent.array[k.nr-1] = 0;	// Vorgaengerknoten ist nicht vorhanden -> k==Wurzel
			else parent.array[k.nr-1] = lastVisited.nr;			// ansonsten Kante zwischen k und Vorgaenger ins parentArray
	}

	/* FUNCTION: recursiveSCC
	Implementierung eines Algorithmus zur Ermittlung der starken Zusammenhangskomponenten
	eines gerichteten Graphen. Bildet zuerst eine Terminationsreihenfolge im Originalgraphen
	(Vorwaertsgraphen) mittels Tiefensuche. Anschliessend wird ein Rueckwaertsgraph gebildet 
	(die Richtung aller Kanten wird vertauscht). Dessen Zusammenhangskomponenten werden in der
	Terminationsreihenfolge mittels Tiefensuche besucht und dabei wird ein parentArray mit allen 
	starken Zusammenhangskomponenten gebildet. Zurueckgeliefert wird schliesslich eine 
	Adjazenzliste, die alle starken Zusammenhangskomponenten repraesentiert. */
	public adjl recursiveSCC(adjl a) {

		// Anzahl der Knoten
		int p = a.size();
		
		// Terminationsreihenfolge der Knoten ermitteln
		terminationOrder = new int[p];
		j = p-1;	// j wird so initialisiert, dass es aufs letzte Element von order zeigt
		visited = new boolean[p];
		int k;
		for (k=0; k<p; k++) visited[k] = false;	// noch keinen Knoten besucht
		buildTerminationOrder = true;			// Terminationsreihenfolge bilden
		for (k=0; k<p; k++) if (!visited[k]) recursiveVisit(null,(vertex) a.elementAt(k)); // alle Zusammenhangskomponenten rekursiv durchlaufen
		
		// Rueckwaertsgraphen bilden
		vertex v,v1;
		edge ed,ed1; 
		adjlStruct as;
		adjl Gr = new adjl(); 
		for (k=0; k<p; k++) Gr.addVertex(((vertex) a.elementAt(k)).Clone());
		for (k=0; k<p; k++) {
			v = (vertex) a.elementAt(k); 
			as = (adjlStruct) v;
			while (as.hasNextElement()) {
				as = as.nextElement();
				ed = (edge) as;
				ed1 = ed.Clone();
				ed1.v2 = (vertex) Gr.elementAt(v.nr-1);
				v1 = (vertex) Gr.elementAt(ed.v2.nr-1);
				Gr.addEdge(v1,ed1);
			}
		}

		// Rueckwaertsgraphen in Terminationsreihenfolge des Vorwaertsgraphen durchlaufen
		parent = new parentArray(p);
		for (k=0; k<p; k++) visited[k] = false;	// noch keinen Knoten im Rueckwaertsgraphen besucht
		buildTerminationOrder = false;			// keine Terminationsreihenfolge bilden
		for (k=0; k<p; k++)						// alle Zusammenhangskomponenten im Rueckwaertsgraphen in Terminationsreihenfolge des Vorwaertsgraphen durchlaufen 
			if (!visited[terminationOrder[k]]) recursiveVisit(null,(vertex) Gr.elementAt(terminationOrder[k]));
			
		// parentArray in Adjazenliste umwandeln und diese zurueckliefern
		return new adjl(Gr,parent); 
	}

}


