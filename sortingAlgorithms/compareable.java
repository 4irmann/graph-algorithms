package sortingAlgorithms;
//
// interface: compareable
//
/**	
* Mit Hilfe dieses interfaces kann eine beliebige Klasse, welche
* das interface implementiert, vergleichbar/sortierbar gemacht werden.
* Hierzu muss in der Methode <a href="#compared">compared</a>
* ein Vergleich zwischen zwei Instanzen der implementierenden Klasse implementiert
* werden. D.h. der Anwender muss den Vergleich auf <, >, = bezueglich
* der implementierenden Klasse selbst programmieren.*/

public interface compareable {
					 	
	public final static int EQUAL=0;
	public final static int ABOVE=1;
	public final static int BELOW=2;

	// method: compared 
	/** 
	* Diese Methode muss vom Anwender in Klassen, welche compareable implementieren,
 	* implementiert werden.
	*
	* @param sobj eine Instanz vom Typ sortable, die mit this verglichen werden soll, bzw.
	* deren Feld(er) mit einem/den Feld(ern) von this verglichen werden soll(en).<br><br> 
	* @return EQUAL, ABOVE oder BELOW<br><br> 
	* @see demoDataObject#compared */
	public abstract int compared(compareable c);

}

