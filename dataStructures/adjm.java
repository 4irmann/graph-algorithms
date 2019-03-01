package dataStructures;
//
// CLASS: Adjazenzmatrix
//
/*  In diesem Objekt kann die Struktur von gerichteten Graphen gespeichert werden.
	Dabei werden die Knoten in einem array verwaltet (vertex-array). Die Kanten werden in einer
	p x p - Matrix (edge[][]) abgelegt, durch die die Richtung der Kanten festgelegt wird.
	bestimmte Attribute des Graphen, wie:
	1. Knotenattribute: Knotentext, Knotenfarbe, usw.
	2. Kantenattribute: Gewicht, Fluss, Kapazitaet usw.
	werden in den Objekten vertex und edge gespeichert.
	Wichtig ist, dass das .nr-Attribut im Objekt vertex, immer
	mit dem Index+1 des Knoten-arrays uebereinstimmt. D.h., falls ein Knoten
	im Knoten-array an Pos. 0 gespeichert ist, sollte sein .nr-Attribut den
	Wert 1 haben. Dies ist fuer fast alle Methoden, Algorithmen usw. Voraussetzung */
	
public class adjm {
	
	// Variablen, Objekte
	private int elementCount;		// Anzahl der Knoten im Graphen
	private vertex vertices[];		// in diesem array werden alle Knoten gespeichert
	private edge matrix[][];		// eigentliche Adjazenzmatrix, repraesentiert alle Kanten
	
	/* CONSTRUCTOR: Importfilter fuer Adjazenzliste
	Setzt Adjazenzliste in eine Adjazenzmatrix um */
	public adjm(adjl a) {					
	
		// Anzahl der Knoten
		int p = a.size();
		elementCount = p;
		
		// Knoten in adjm uebertragen
		int k;
		vertices = new vertex[p];
		for (k=0; k<p;k++) vertices[k] = ((vertex) a.elementAt(k)).Clone();	
		
		// Kanten in adjm uebertragen
		vertex v; adjlStruct as; edge ed;
		matrix = new edge[p][p];
		for (k=0; k<p;k++) {
			v = (vertex) a.elementAt(k);
			as = (adjlStruct) v;
			while (as.hasNextElement()) {
				as = as.nextElement();
				ed = (edge) as;
				matrix[v.nr-1][ed.v2.nr-1] = ed.Clone();
			}
		}

	}
		
	/* PROCEDURE: setVertexAt
	Setzt Knoten in Knoten-array */
	public void setVertexAt(int i,vertex v) {
		vertices[i] = v;
	}

	/* FUNCTION: vertexAt
	Liefert Knoten aus Knoten-array */
	public vertex vertexAt(int i) {
		return vertices[i];
	}

	/* PROCEDURE: setEdgeAt
	Setzt Kante in Matrix */
	public void setEdgeAt(int x,int y,edge ed) {
		matrix[x][y] = ed;
	}

	/* FUNCTION: edgeAt
	Liefert Kante aus Matrix */
	public edge edgeAt(int x,int y) {
		return matrix[x][y];
	}

	/* FUNCTION: weightAt 
	Liefert Gewicht der Kante aus Matrix */
	public int weightAt(int x,int y) {
		return matrix[x][y].weight;
	}

	/* PROCEDURE: setWeightAt 
	Setze Gewicht der Kante aus Matrix */
	public void setWeightAt(int x,int y,int w) {
		matrix[x][y].weight = w;
	}

	/* FUNCTION: size
	Liefert Anzahl der Knoten in Knoten-array */
	public int size() {
		return elementCount;
	}

}

