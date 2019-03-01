package dataStructures;
import java.util.Enumeration;
// 
// CLASS: arrangeCircle
//
/* Ordnet die Knotenkoordinaten einer Adjazenliste kreisfoermig an.
   Oberklasse ist abstrakte Klasse arrangeGraph */

public class arrangeCircle extends arrangeGraph {
	
	// Variablen
	private int xabs,yabs;
	private double radius;	
	
	/* CONSTRUCTOR: 
	x = X-Koordinate Kreismittelpunkt
	y = Y-Koordinate Kreismittelpunkt
	r = Radius */
	public arrangeCircle(int x,int y,double r) {
		xabs = x;
		yabs = y;
		radius = r;
	}

	// PROCEDURE: arrange
	public void arrange(adjl a) {
		
		// Variablen, Objekte
		double x,y;
		double segment = 2*Math.PI / (double) a.size(), angle= 0;  // Kreis in |Knoten|-Segmente unterteilen

		// Koordinaten berechnen und in Knoten eintragen
		vertex v;
		Enumeration e = a.elements();
		while(e.hasMoreElements()) {
			v = (vertex) e.nextElement();
			x = radius*Math.cos(angle);
			y = radius*Math.sin(angle);
			v.x = xabs+(int) Math.rint(x);
			v.y = yabs+(int) Math.rint(y);
			angle += segment;
		}
	}

}

