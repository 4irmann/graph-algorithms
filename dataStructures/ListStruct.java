package dataStructures;
// 
// CLASS: ListStruct
//
/* Struktur fuer eine verkettete Liste. D.h. alle Objekte, die sich von dieser Klasse ableiten 
   (wie Knoten, Kanten), koennen miteinander verkettet werden, wobei data (siehe Variablen, Objekte)
   auf ein eigentliches Datenobjekt verweisen kann. */

class ListStruct {
	
	// Variablen, Objekte
	ListStruct next;
	ListStruct prev;
	Object data;
	
	/* CONSTRUCTOR: normal
	Das uebergebene Object wird in data eingetragen */
	ListStruct(Object o) {
		data = o;
	}
	
	// CONSTRUCTOR: data mit null initialisieren
	ListStruct() {
		data = null;
	}

}



