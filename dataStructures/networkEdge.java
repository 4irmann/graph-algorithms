package dataStructures;
import java.awt.Color;
import java.awt.Graphics;
//
// CLASS: networkEdge
//
/* Repraesentiert Kante eines Netzwerks. Enthaelt typische Attribute einer Netzwerk-Kante wie
   Kapazitaet, Fluss. Oberklasse ist edge und somit enthaelt networkEdge auch die Attribute
   Gewicht, Farbe usw.. Die Methoden Clone und draw der Oberklasse werden von networkEdge
   ueberschrieben. */

public class networkEdge extends edge {
	
	// Variablen, Attribute
	public int capacity;
	public int flow;

	/* CONSTRUCTOR: normal
	ve = Zielknoten, auf den die Kante zeigt */
	public networkEdge(vertex ve) {
		super(ve);
	}

	// CONSTRUCTOR: kein Zielknoten
	public networkEdge() {
		this(null);
	}

	/* FUNCTION: Clone
	Erstellt 1:1 Kopie der Kante im Speicher und liefert diese zurueck.
	Lediglich die Zeiger v1,v2 werden auf null initialisiert ! */
	public edge Clone() {
		networkEdge ne = new networkEdge();
		ne.weight = weight;
		ne.capacity = capacity;
		ne.flow = flow;
		ne.c = new Color(c.getRGB());
		return ne;
	}
	
	/* PROCEDURE: draw
	Stellt Kante graphisch dar */
	public void draw(vertex v,Graphics g) {
		Color c1;
		if (v != v2) {	 // reflexive Kanten nicht darstellen, da sonst Knotentext nicht mehr lesbar
			c1 = g.getColor();
			g.setColor(c);
			g.drawLine(v.x+(v.size/2),v.y+(v.size/2),v2.x+(v2.size/2),v2.y+(v2.size/2));
			Integer i = new Integer(capacity);
			g.setColor(Color.red);
			g.drawString(i.toString(),v2.x-((v2.x-v.x)/4)+17,v2.y-((v2.y-v.y)/4)+25);
			i = new Integer(flow);
			g.setColor(Color.green);
			g.drawString(i.toString(),v2.x-((v2.x-v.x)/3)+17,v2.y-((v2.y-v.y)/3)+25);
			g.setColor(c1);
		}
	}

}

