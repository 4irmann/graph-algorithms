package dataStructures;
import java.util.Enumeration;
// 
// CLASS: arrangeRaster
//
/* Ordnet die Knotenkoordinaten einer Adjazenliste rasterfoermig an.
   Oberklasse ist abstrakte Klasse arrangeGraph */

public class arrangeRaster extends arrangeGraph {

	// Variablen
	private int xstep,ystep,width;

	/* CONSTRUCTOR: 
	x = Schrittweite zwischen Knoten in x-Richtung
	y = Schrittweite zwischen Knoten in y-Richtung
	w = max. Breite des Rasters */
	public arrangeRaster(int x,int y,int w) {
		xstep = x;
		ystep = y;
		width = w;
	}
	
	// PROCEDURE: arrange
	public void arrange(adjl a) {
		int x=xstep,y=ystep,w=width;
		vertex v;
		Enumeration e = a.elements();
		while(e.hasMoreElements()) {
			v = (vertex) e.nextElement();
			v.x = x;
			v.y = y;
			if (x+xstep <= w) x += xstep; 
			else {
				x = xstep;
				y += ystep;
			}
		}
	}

}

