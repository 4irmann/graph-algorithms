package dataStructures;
//
// CLASS: priorityQueueStruct
//
/* Struktur fuer priorityQueue. Enthaelt ein Object und die zugehoerige Prioritaet */

class priorityQueueStruct {
	
	// Variablen, Daten
	Object element;	// Object
	int priority;	// zugehoerige Prioritaet

	// CONSTRUCTOR:
	priorityQueueStruct(Object o,int prio) {
		element = o;
		priority = prio;
	}

}

