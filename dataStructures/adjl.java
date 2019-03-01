package dataStructures;
import java.util.*;
import java.awt.Graphics;
import java.awt.Color;
//
// CLASS: adjl
//
/*  In diesem Objekt kann die Struktur von gerichteten Graphen gespeichert werden.
	Dabei werden die Knoten in einem array verwaltet (Vector) und an jedem Knoten haengt eine
	verkettete Liste (siehe adjlStruct) mit Kanten, die von diesem Knoten wegfuehren.
	bestimmte Attribute des Graphen, wie: 
	1. Knotenattribute: Knotentext, Knotenfarbe, usw.
	2. Kantenattribute: Gewicht, Fluss, Kapazitaet usw.
	werden in den Objekten vertex und edge gespeichert.
	Wichtig ist, dass das .nr-Attribut im Objekt vertex, immer
	mit dem Index+1 der adjl uebereinstimmt. D.h., falls ein Knoten
	in der adjl an Pos. 0 gespeichert ist, sollte sein .nr-Attribut den
	Wert 1 haben. Dies ist fuer fast alle Methoden, Algorithmen usw. Voraussetzung */
public class adjl extends Vector {
	
	// Titel der Adjazenzliste mit Koordinaten (nur fuer graphische Darstellung)
	public String title;
	public int xTitle;
	public int yTitle;

	// Start-/Endknoten fuer Netzwerk
	public vertex source;
	public vertex target;

	// Variablen, Objekte fuer Importfilter
	private int tokenCount;												// Zaehler fuer aktuelle token-Nummer
	private StringTokenizer st;											// tokenizer zum Zerhacken des Parameterstrings
	private String delimiters = " ,\n\r\t\"";							// tokenizer-Trennzeichen: whitespaces und ,"
	private String delimiterArray[] = {" ",",","\n","\r","\t","\""};
	private int delimiterCount = 6;

	/* FUNCTION: getNextToken
	Liefert naechstes token aus dem tokenizer, oder null, falls letztes token
	ein Trennzeichen war.
	Loest exception aus, falls beim Aufruf kein token im Tokenizer mehr vorhanden.
	Strings wie "hallo Welt" werden erkannt und komplett (mit Trennzeichen)
	als tokens zurueckgegeben. */
	private String getNextToken() throws dataFormatException {			
		String help,help1;
		boolean delimiter;
		try {
			do {
				help = st.nextToken(); tokenCount++;		// naechstes token 
				delimiter = false;							
				int i=0;
				while (i<delimiterCount) {					// ist token Trennzeichen ?
					if (help.equals(delimiterArray[i])) {
						delimiter = true;
						break;
					}
					i++;
				}
				if (delimiter && help.equals("\"")) {		// evt. ueberpruefen, ob String vorliegt
					help = null;
					while (true) {
						help1 = st.nextToken();	tokenCount++;
						if (!help1.equals("\"")) 
							if (help == null) help = help1;
							else help = help.concat(help1);
						else {
							delimiter = false;
							break;
						}
					}
				}
				if (delimiter && !st.hasMoreElements()) {	// war letztes token ein Trennzeichen ?
					help = null;							// falls ja -> null-String zurueckliefern
					break;
				}
			} 
			while (delimiter);							// solange naechstes token delimiter wiederholen
		} catch (NoSuchElementException e) {
			throw new dataFormatException("Data format error",tokenCount); 
		}

		return help;	// token zurueckliefern
	} 

	/* FUNCTION: stringToInt
	Loest exception aus, falls String keine korrekte Zahl enthaelt */
	private int stringToInt(String s) throws dataFormatException {
		Integer i;
		try {
			i = new Integer(s);		// temporaeres Integer-Objekt erzeugen, Parameter ist String
		} catch (NumberFormatException e) { 
			throw new dataFormatException("Integer-parameter expected !",tokenCount); 
		}
		return i.intValue();			// Integer-Wert als int zurueckliefern
	} 	
	
	/* FUNCTION: stringFlagToBoolean
	Vergleicht einen String auf zwei Werte und liefert entweder true oder false zurueck.
	Loest exception aus, falls keiner der zwei Werte mit String uebereinstimmt */
	private boolean stringFlagToBoolean(String parameter,String flagTrue,String flagFalse) throws dataFormatException {
		if (parameter.equals(flagTrue)) return true;		// true zurueck, falls true-Flag als Parameter
		if (parameter.equals(flagFalse)) return false;		// false zurueck, falls false-Flag als Parameter
		throw new dataFormatException("\""+flagTrue+"\" or \""+flagFalse+"\" expected, not: \""+parameter+"\"",tokenCount);
	} 

	/* CONSTRUCTOR: Importfilter fuer smsoftText-Format
	Wandelt einen Graphen im smsoftText-Format in eine Adjazenzliste um. 
	Loest exception aus, falls smsoftText fehlerhaft */
	public adjl(smsoftTextGraphParameters stgp) throws smsoftTextGraphParametersException {
		
		// Tokenizer initialisieren : Trennzeichen werden als tokens zurueckgeliefert
		st = new StringTokenizer(stgp.text,delimiters,true);	
		
		// Variablen, Objekte
		boolean weighted=false;	// Standard: nicht gewichtet 
		boolean directed=false;	// Standard: nicht gerichtet
		vertex v; edge ed,ed1;
		int help;
	
		// try-Block faengt Fehler im smsoftText ab
		try {
			
			// flags setzen
			getNextToken(); weighted = stringFlagToBoolean(getNextToken(),"TRUE","FALSE");	// gewichtet: "TRUE"
			getNextToken(); directed = stringFlagToBoolean(getNextToken(),"TRUE","FALSE");  // gerichtet: "FALSE"
			
			// Knoten einlesen
			while (true) {
				help = stringToInt(getNextToken());
				if (help < size()) break;
				v = new vertex(help+1); 
				v.text = getNextToken();	
				v.x = stringToInt(getNextToken()); v.y = stringToInt(getNextToken());
				addVertex(v);
			}
			
			// Kanten (evt. mit Gewicht) einlesen
			String help1;
			while (true) {
				ed = new edge();
				v = (vertex) elementAt(help);
				ed.v2 = (vertex) elementAt(stringToInt(getNextToken()));
				if (weighted) ed.weight = stringToInt(getNextToken());
				v.addElement(ed);
				if (!directed) {
					ed1 = ed.Clone();
					ed1.v2 = v;
					ed.v2.addElement(ed1);
				}
				if (st.hasMoreElements()) help1 = getNextToken(); else break;
				if (help1 == null) break; else help = stringToInt(help1);
			}
		} catch (dataFormatException e) {
			throw new smsoftTextGraphParametersException(e.getMessage());
		}
	} 
	
	// CONSTRUCTOR: siehe Importfilter fuer smsoftText-Format, erweitert mit Objekt zum Arrangieren der Adjazenzliste
	public adjl(smsoftTextGraphParameters stgp,arrangeGraph ag) throws smsoftTextGraphParametersException {
		this(stgp);
		ag.arrange(this);
	}

	/* CONSTRUCTOR: Importfilter fuer standardText-Format (=erweitertes smsoftText-Format)
	Wandelt einen Graphen im standardText-Format in eine Adjazenzliste um.
	Loest exception aus, falls standardText fehlerhaft */
	public adjl(standardTextGraphParameters stgp) throws standardTextGraphParametersException {
		
		// Tokenizer initialisieren : Trennzeichen werden als tokens zurueckgeliefert
		st = new StringTokenizer(stgp.text,delimiters,true);	
		
		// Variablen, Objekte
		boolean weighted=false;	// Standard: nicht gerichtet
		boolean directed=false;	// Standard: nicht gewichtet
		boolean network=false;	// Standard: kein Netzwerk
		vertex v; edge ed,ed1;
		int help;
		String help1;
	
		// try-Block faengt Fehler im standardText ab
		try {
			
			// Flags setzen
			getNextToken(); weighted = stringFlagToBoolean(getNextToken(),"TRUE","FALSE");	// gewichtet: z.B. TRUE
			getNextToken(); directed = stringFlagToBoolean(getNextToken(),"TRUE","FALSE");  // gerichtet: z.B. FALSE
			getNextToken(); network = stringFlagToBoolean(getNextToken(),"TRUE","FALSE");	// network: z.B. TRUE
			if (network) {
				weighted=false;
				directed=true;
			}

			// Knoten eintragen
			help = 0;
			while (true) {
				if (network) {
					help1 = getNextToken();
					if (help1.equals("source:")) break;
					else help = stringToInt(help1);
				}
				else {
					help = stringToInt(getNextToken());
					if (help < size()) break;
				}
				v = new vertex(help+1); 
				v.text = getNextToken();	
				v.x = stringToInt(getNextToken()); v.y = stringToInt(getNextToken());
				addVertex(v);
			}

			// fuer Netzwerk: Start-/Endknoten eintragen 
			if (network) {
				source = (vertex) elementAt(stringToInt(getNextToken()));					// z.B. source: 0
				source.c = Color.red;
				getNextToken(); target = (vertex) elementAt(stringToInt(getNextToken()));	// z.B. target: 5
				target.c = Color.green;
				help = stringToInt(getNextToken());
			}
			
			// Kanten eintragen (evt. mit Gewicht, Fluss, Kapazitaet)
			while (true) {
				if (network) ed = new networkEdge(); else ed = new edge();
				v = (vertex) elementAt(help);
				ed.v2 = (vertex) elementAt(stringToInt(getNextToken()));
				if (weighted) ed.weight = stringToInt(getNextToken());
				if (network) {
					((networkEdge) ed).capacity = stringToInt(getNextToken());
					((networkEdge) ed).flow = stringToInt(getNextToken());
				}
				v.addElement(ed);
				if (!directed) {
					ed1 = ed.Clone();
					ed1.v2 = v;
					ed.v2.addElement(ed1);
				}
				if (st.hasMoreElements()) help1 = getNextToken(); else break;
				if (help1 == null) break; else help = stringToInt(help1);
			}
		} catch (dataFormatException e) {
			throw new standardTextGraphParametersException(e.getMessage());
		}
	} 

	// CONSTRUCTOR: siehe Importfilter fuer smsoftText-Format, erweitert mit Objekt zum Arrangieren der Adjazenzliste
	public adjl(standardTextGraphParameters stgp,arrangeGraph ag) throws standardTextGraphParametersException {
		this(stgp);
		ag.arrange(this);
	}

	/* CONSTRUCTOR: Importfilter fuer parentArray
	Wandelt ein parentArray in eine neue Adjazenzliste um.
	Voraussetzung ist, dass sich das uebergebene parentArray auf
	die uebergebene Adjazenzliste bezieht, und alle Kanten im parentArray
	auch in der uebergebenen Adjazenzliste vorhanden sind. */
	public adjl(adjl a,parentArray pa) {
		int i;
		for (i=0; i<a.size(); i++) addVertex(((vertex) a.elementAt(i)).Clone());
		for (i=0; i<a.size(); i++) 
			if (pa.array[i] != 0) { 
				adjlStruct as = (adjlStruct) a.elementAt(pa.array[i]-1);
				while (as.hasNextElement()) {
					if (((edge) as.nextElement()).v2.nr-1 == i) {
						edge ed = ((edge) as.nextElement()).Clone();
						ed.v2 = (vertex) elementAt(i);
						addEdge((vertex) elementAt(pa.array[i]-1),ed);
						break;
					}
					as = as.nextElement();
				}
			}
	}

	// CONSTRUCTOR: siehe Importfilter fuer parentArray, erweitert mit Objekt zum Arrangieren der Adjazenzliste
	public adjl(adjl a,parentArray pa,arrangeGraph ag) {
		this(a,pa);
		ag.arrange(this);
	}
	
	/* CONSTRUCTOR: Importfilter fuer Adjazenzmatrix
	setzt Adjazenzmatrix in eine neue Adjazenzliste um */
	public adjl(adjm ad) {		
		
		// Variablen
		int k,l,p = ad.size();
				
		// Knoten in adjl uebertragen
		adjl a = new adjl();
		for (k=0; k<p;k++) addVertex(ad.vertexAt(k).Clone());
				
		// Kanten in adjl uebertragen
		edge ed;
		for (k=0; k<p;k++)
			for (l=0;l<p;l++) {
				ed = ad.edgeAt(k,l);
				if (ed != null) {
					ed = ed.Clone();
					ed.v2 = (vertex) elementAt(l);
					addEdge((vertex) elementAt(k),ed);
				}
			}
	}

	// CONSTRUCTOR: fuer leere Adjazenzliste
	public adjl() {
	}

	// PROCEDURE: addVertex
	public final void addVertex(vertex v) {
		addElement(v);
	}

	/* PROCEDURE: addEdge
	Haengt Kante an uebergebenen Knoten an. Voraussetzung ist, dass
	der uebergebene Kante einen Zeiger auf den Zielknoten enthaelt. */
	public final void addEdge(vertex v,edge ed) {
		Enumeration e = elements();
		while (e.hasMoreElements()) 
			if (((vertex) e.nextElement()) == v) v.addElement(ed);
	}

	/* FUNCTION: edgeAt
	Liefert Kante zwischen Knoten A und Knoten B zurueck, wobei nur
	der Index von A und B uebergeben werden muss. */
	public edge edgeAt(int a,int b) {
		edge ed;
		adjlStruct as = (adjlStruct) elementAt(a);
		while (as.hasNextElement()) {
			as = as.nextElement();
			ed = (edge) as;
			if (ed.v2.nr-1 == b) return ed;
		}
		return null;
	}

	/* PROCEDURE: draw
	Adjazenzliste graphisch darstellen.
	g ist Graphics-Objekt, in das gezeichnet wird */
	public void draw(Graphics g) {
		
		// Kanten zeichnen
		vertex v; adjlStruct as;
		Enumeration e = elements();
		while (e.hasMoreElements()) {
			 v = (vertex) e.nextElement();
			 as = (adjlStruct) v;
			 while (as.hasNextElement()) {
				 as = as.nextElement();
				 ((edge) as).draw(v,g);
			 }
		}
		
		// Knoten zeichnen
		e = elements();
		while (e.hasMoreElements()) ((vertex) e.nextElement()).draw(g);
		
		// evt. Titel zeichnen
		if (title != null) g.drawString(title,xTitle,yTitle);
	}
	
	/* FUNCTION: Clone
	Erstellt 1:1 Kopie von Adjazenzliste im Speicher und liefert Kopie zurueck. */
	public adjl Clone() {
		
		adjl a = new adjl();
		
		// Knoten kopieren
		int k,p=size();
		for (k=0; k<p;k++) a.addVertex(((vertex) elementAt(k)).Clone());

		// Kanten kopieren
		edge ed,ed1; adjlStruct as;
		for (k=0; k<p;k++) {
			as = (adjlStruct) elementAt(k);
			while (as.hasNextElement()) {
				as = as.nextElement();
				ed = (edge) as;
				ed1 = ed.Clone();
				ed1.v2 = (vertex) a.elementAt(ed.v2.nr-1);
				a.addEdge((vertex) a.elementAt(k),ed1);
			}
		}

		// Titel der Adjazenzliste kopieren 
		if (title != null) a.title = new String(title);
		a.xTitle = xTitle;
		a.yTitle = yTitle;

		// Start-/Endknoten fuer Netzwerk kopieren
		if (source != null) a.source = (vertex) a.elementAt(source.nr-1);
		if (target != null) a.target = (vertex) a.elementAt(target.nr-1);
		
		return a;
	}

}

