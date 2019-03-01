package graphAlgorithms;
import dataStructures.*;
//
// CLASS: transitiveClosure
//
/* Stellt Methoden zur Ermittlung des transitiven Abschlusses (Erreichbarkeit von  Zielknoten w von Startknoten v aus) eines gerichteten Graphen zur Verfuegung. 
   Funktioniert aehnlich wie der Algorithmus von Floyd. */

public class transitiveClosure {

	/* FUNCTION: warshall
	Implementierung des Algorithmus von Warshall zur Ermittlung der Erreichbarkeit von Zielknoten w von
	Startknoten v aus zwischen allen Knoten eines gerichteten Graphen.
	Liefert Adjazenzliste zurueck, die die Erreichbarkeit zwischen allen Knoten repraesentiert. */
	public adjl warshall(adjl ad) {
		
		// Adjazenzliste in Adjazenzmatrix kopieren
		adjm a = new adjm(ad);
		
		// Knotenanzahl
		int p=a.size();
		
		// gesamte Matrix p^3-mal durchlaufen
		int k;
		for(k=0;k<p;k++) 
			for (int i=0;i<p;i++)
				for (int j=0;j<p;j++) 
					if ((a.edgeAt(i,k) != null) && (a.edgeAt(k,j) != null)) a.setEdgeAt(i,j,a.edgeAt(i,k));

		for (k=0;k<p;k++) a.setEdgeAt(k,k,new edge());	// jeder Knoten ist von sich selbst aus erreichbar (reflexiv)
	
		// Adjazenzmatrix in neue Adjazenzliste kopieren
		return new adjl(a);
	}

}

