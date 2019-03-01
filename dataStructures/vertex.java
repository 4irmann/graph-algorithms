package dataStructures;
import java.awt.Color;
import java.awt.Graphics;
//
// CLASS: vertex
//
/* Repraesentiert Knoten eines Graphen. Enthaelt typische Attribute eines Knoten wie
   Nummer,Text, Koordinaten, Farbe usw.. Die Knotennummer-1 sollte mit dem Index des array-Elements,
   in dem der Knoten gespeichert wird, uebereinstimmen.
   Im Zusammenhang mit einer Adjazenzliste enthaelt vertex  einen Teil der Struktur 
   der Adjazenzliste, da vertex, falls vorhanden, auf eine Liste mit 
   Kanten, die von vertex (Ausgangsknoten) wegfuehren, verweist. 
   vertex ist die Urklasse fuer alle Zugriffe von Graphenalgorithmen usw. auf Knoten. 
*/

public class vertex extends adjlStruct {
	
	// Variablen, Attribute
	public int nr;			// Knotennummer
	public String text;		// Knotentext
	public int x;			// Knoten-X-Koordinate
	public int y;			// Knoten-Y-Koordinate
	public int size;		// Knoten-Groesse 
	public Color c;			// Knotenfarbe
	
	/* CONSTRUCTOR: mit Nummer
	i = Nummer des Knoten */
	public vertex(int i) {
		nr = i;
		c = Color.black;
		size = 33;
	}

	// CONSTRUCTOR: ohne Nummer
	public vertex() {
		this(0);
	}

	/* FUNCTION: Clone
	Erstellt 1:1 Kopie des Knoten im Speicher und liefert diesen zurueck.
	Lediglich der Zeiger auf die evt. vorhandene Kantenliste, die am Knoten haengt, 
	wird auf null initialisiert. */ 
	public vertex Clone() {
		vertex v = new vertex();
		v.nr = nr;
		if (text != null) v.text = new String(text);
		v.x = x;
		v.y = y;
		v.size = size;
		v.c = new Color(c.getRGB());
		return v;
	}				

	/* PROCEDURE: draw
	Stellt Knoten graphisch dar */
	public void draw(Graphics g) {
		Color c1 = g.getColor();
		Integer i = new Integer(nr);
		g.setColor(c);
		g.fillOval(x,y,size,size);
		g.setColor(Color.white);
		if (text == null)
			g.drawString(i.toString(),x+(size/2),y+(size/2));
		else g.drawString(text,x+(size/2)-3,y+(size/2)+3);
		g.setColor(c1);
	}

}

