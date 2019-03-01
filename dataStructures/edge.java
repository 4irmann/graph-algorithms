package dataStructures;
import java.awt.Color;
import java.awt.Graphics;
import sortingAlgorithms.*;
//
// CLASS: edge
//
/* Repraesentiert Kante eines Graphen. Enthaelt typische Attribute einer Kante wie
   Gewicht, Farbe usw.. Im Zusammenhang mit einer Adjazenzliste enthaelt edge ausserdem 
   einen Teil der Struktur der Adjazenzliste: 
   - in v1 wird dann der Ausgangsknoten (nicht unbedingt erforderlich), 
   - und in v2 der Zielknoten gespeichert.
   - Ausserdem verweist edge, falls vorhanden, auf weitere Kanten, die ueber eine verkettete
     Liste (siehe adjlStruct) ebenfalls am Ausgangsknoten (siehe v1) haengen. 
   edge ist die Urklasse fuer alle Zugriffe von Graphenalgorithmen auf Kanten. 
   Ausserdem ist die Klasse durch die Implementierung des interfaces compareable sortierbar 
   (siehe compareable, auch in dieser Klasse).
*/
 
public class edge extends adjlStruct implements compareable {
	
	// Variablen, Attribute
	public int weight;	// Gewicht
	public vertex v1;	// Ausgangsknoten
	public vertex v2;	// Zielknoten
	public Color c;		// Farbe der Kante
	
	/* CONSTRUCTOR: normal
	ve = Zielknoten, auf den die Kante zeigt */
	public edge(vertex ve) {
		v2 = ve;
		c = Color.black;
	}

	// CONSTRUCTOR: kein Zielknoten
	public edge() {
		this(null);
	}

	/* FUNCTION: Clone
	Erstellt 1:1 Kopie der Kante im Speicher und liefert diese zurueck.
	Lediglich die Zeiger v1,v2 werden auf null initialisiert ! Sowie der
	Zeiger auf weitere Kanten */
	public edge Clone() {
		edge e = new edge();
		e.weight = weight;
		e.c = new Color(c.getRGB());
		return e;
	}

	/* FUNCTION: compared
	Funktion des interfaces compareable. In dieser Funktion werden
	die Kriterien fuer einen Vergleich zwischen zwei Kanten festgelegt.
	Standardmaessig werden zwei Kanten anhand ihres Gewichts verglichen. */
	public int compared(compareable c) {
		edge e = (edge) c;
		if (e.weight > weight) return compareable.BELOW;
		if (e.weight < weight) return compareable.ABOVE;
		return compareable.EQUAL;
	}

	/* PROCEDURE: draw
	Stellt Kante graphisch dar */
	public void draw(vertex v,Graphics g) {
		Color c1;
		if (v != v2) {	 // reflexive Kanten nicht darstellen, da sonst Knotentext nicht mehr lesbar
			c1 = g.getColor();
			g.setColor(c);
			g.drawLine(v.x+(v.size/2),v.y+(v.size/2),v2.x+(v2.size/2),v2.y+(v2.size/2));
			Integer i = new Integer(weight);
			g.setColor(Color.yellow);
			g.drawString(i.toString(),v2.x-((v2.x-v.x)/3)+17,v2.y-((v2.y-v.y)/3)+25);
			g.setColor(c1);
		}
	}

}

