package dataStructures;
import java.util.Enumeration;
// 
// CLASS: arrangeOffset
//
/* Kopiert die Knotenkoordinaten einer Quell-Adjazenliste in eine Ziel-Adjazenzliste 
   mit einem offset. Voraussetzung ist, dass im CONSTRUCTOR eine Quell-Adjazenzliste uebergeben
   wird, die von der Knotenanzahl mit der uebergebenen Ziel-Adjaenzliste in der
   PROCEDURE arrange uebereinstimmt. Quell-Adjazenzliste == Ziel-Adjazenzliste ist ebenfalls
   moeglich. Oberklasse ist abstrakte Klasse arrangeGraph */

public class arrangeOffset extends arrangeGraph {

	// Variablen, Objekte
	private adjl a;	
	private int xoff;
	private int yoff;
	private String titl;
	private int xTitleOf=0;
	private int yTitleOf=0;

	/* CONSTRUCTOR: normal
	ad = Quell-Adjazenzliste
	xof = x-offset
	yof = y-offset */
	public arrangeOffset(adjl ad,int xof,int yof) {
		a = ad;
		xoff = xof;
		yoff = yof;
		xTitleOf=0;
		yTitleOf=0;
		titl=null;
	}

	/* CONSTRUCTOR: siehe normal, mit Festlegung des Titels der Ziel-Adjazenzliste
	tit = Titel
	xTitleO = Offset des Titels bezueglich des x-offset
	yTitleO = Offset des Titles bezueglich des y-offset */
	public arrangeOffset(adjl ad,int xof, int yof, String tit, int xTitleO,int yTitleO) {
		this(ad,xof,yof);
		titl = tit;
		xTitleOf = xTitleO;
		yTitleOf = yTitleO;
	}

	// PROCEDURE: arrange
	public void arrange(adjl a1) {
		vertex v,v1;
		Enumeration e = a.elements(),e1 = a1.elements();
		while(e.hasMoreElements()) {
			v = (vertex) e.nextElement();
			v1 = (vertex) e1.nextElement();
			v1.x = v.x+xoff;
			v1.y = v.y+yoff;
		}
		if (titl != null) {
			a1.title = titl;
		}
		a1.xTitle = a.xTitle+xTitleOf+xoff;
		a1.yTitle = a.yTitle+yTitleOf+yoff;
	}

}

