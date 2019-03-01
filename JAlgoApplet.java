import javax.swing.*;
import java.awt.*;
import graphAlgorithms.*;
import dataStructures.*;

public class JAlgoApplet extends JApplet {
	
	// Applet-spezifische Variablen/Konstanten
	private boolean error;
	private String errorMessage;
	private static final long serialVersionUID = 1L;
	
	// Datenstrukturen
	private adjl al1,al2,al3,al4,al5,al6,al7,al8,al9,al10,al11,al12,al13,al14,al15,al16;	// Adjazenzlisten
	private arrangeCircle ac;	// Zum kreisfoermigen Anordnen von Adjazenzlisten
	private arrangeOffset ao;	// Zum Verschieben der Koordinaten von Adjazenzlisten
	private arrangeRaster ar;	// Zum rasterfoermigen Anordnen von Adjazenzlisten
	private loadParameters lp;	// Zum Laden von Graphenparamtern (Textdateien)  
	
	// Graphenalgorithmen
	private elementaryTraverse et;
	private minimalSpanningTree mst;  
	private minimalPathLength mpl;
	private topologicalSort ts;
	private stronglyConnectedComponents scc;  //  @jve:decl-index=0:
	private transitiveClosure tc;
	private maximalFlow mf;
	
	public JAlgoApplet() {
		super();
		
		this.setSize(800, 600);
		getContentPane().setLayout(new BorderLayout());
		Graph graph = new Graph();
		getContentPane().add(new GraphPane(graph));
	}
	
	public void init() {
		
		 // try-block faengt Ladefehler und Parameterformatfehler ab
        try {
			// Initialisierung der Graphenalgorithmen
			et = new elementaryTraverse();
			mst = new minimalSpanningTree();
			mpl = new minimalPathLength();
			ts = new topologicalSort();
			scc = new stronglyConnectedComponents();
			tc = new transitiveClosure();
			mf = new maximalFlow();
				
			// Object zum Laden von Graphenparametern initialisieren
			lp = new loadParameters();
							
			// neue Adjazenzlisten von Graphenalgorithmen erzeugen lassen
			
			
			// 1. Teil (bfs - Djikstra)
				
			// Ausgangs-Graphen laden
			al1 = new adjl(new smsoftTextGraphParameters(lp.getParameters("smsoftText/Bla1.txt")));
			al1.title = "Original graph:"; al1.xTitle = 10; al1.yTitle = 30;

			// bfs, dfs
			al2	= new adjl(al1,et.bfs(al1),new arrangeOffset(al1,0,500,"breadth-first-search:",0,0));
			al3 = new adjl(al1,et.dfs(al1),new arrangeOffset(al2,0,500,"depth-first-search:",0,0));
				
			// Kruskal
			al4	= mst.kruskal(al1);
			ao = new arrangeOffset(al3,0,500,"Kruskal (mst (minimal spanning tree)):",0,0);
			ao.arrange(al4);
				
			// Prim, Djikstra
			al5 = new adjl(al1,mst.prim(al1),new arrangeOffset(al4,0,500,"Prim (mst):",0,0));
			al6 = new adjl(al1,mpl.djikstra(al1),new arrangeOffset(al5,0,500,"Djikstra (mpl (minimal path length)):",0,0));
				
			// 2. Teil (topological sort - maximal flow)
					
			// topological sort
			al7 = new adjl(new smsoftTextGraphParameters(lp.getParameters("smsoftText/DAG.TXT")));
			al7.title = "Original dag (directed acyclic graph):"; al7.xTitle = 60; al7.yTitle = 50;
			ao = new arrangeOffset(al7,-50,3000);
			ao.arrange(al7);
			al8 = ts.recursiveTopologicalSort(al7);
			ar = new arrangeRaster(40,0,800); ar.arrange(al8);
			ao = new arrangeOffset(al8,500,3150,"topological sort:",50,-50); ao.arrange(al8);
				
			// scc
			al9 = new adjl(new smsoftTextGraphParameters(lp.getParameters("smsoftText/SCC.TXT")));
			ao = new arrangeOffset(al9,0,3400,"Original dg (directed graph):",10,40); ao.arrange(al9);
			al10 = scc.recursiveSCC(al9);
			ao = new arrangeOffset(al9,500,0,"scc (strongly connected components):",20,0); ao.arrange(al10);
				
			// transitive closure / Warshall
			al11 = new adjl(new smsoftTextGraphParameters(lp.getParameters("smsoftText/SCC.TXT")));
			ao = new arrangeOffset(al11,0,3700,"Original dg (directed graph):",10,0); ao.arrange(al11);
			al12 = tc.warshall(al11);
			ac = new arrangeCircle(130,130,140); ac.arrange(al12);
			ao = new arrangeOffset(al12,600,3700,"Warshall (transitive closure):",-70,0); ao.arrange(al12);

			// Floyd
			al13 = new adjl(new smsoftTextGraphParameters(lp.getParameters("smsoftText/FLOYD.TXT")));
			ao = new arrangeOffset(al13,0,4100,"Original dg:",10,0); ao.arrange(al13);
			al14 = mpl.floyd(al13);
			ac = new arrangeCircle(130,130,100); ac.arrange(al14);
			ao = new arrangeOffset(al14,500,4100,"Floyd (mpl (minimal path length)):",0,0); ao.arrange(al14);
			
			// maximal flow / Edmonds Karp
			al15 = new adjl(new standardTextGraphParameters(lp.getParameters("standardtext/NETWORK.TXT")));
			ao = new arrangeOffset(al15,0,4500,"Original network:",10,0); ao.arrange(al15);
			al16 = mf.edmondsKarp(al15);
			ao = new arrangeOffset(al15,500,0,"Edmonds Karp (maximal flow):",0,0); ao.arrange(al16);
	
		} catch (loadParametersException e) {				// Parameterdatei-Ladefehler (Datei nicht gefunden usw.)
			errorMessage = e.getMessage();
			error = true;
		} catch (smsoftTextGraphParametersException e) {	// fehlerhafte Parameterdatei (smsoft)
			errorMessage = e.getMessage();
			error = true;
		} catch (standardTextGraphParametersException e) {	// fehlerhafte Parameterdatei (standard)
			errorMessage = e.getMessage();
			error = true;
		}
	}	 
	
	private class GraphPane extends JScrollPane {
		
		private static final long serialVersionUID = 2L;

		public GraphPane(JComponent component) {	  	  
			  super(component);
			  getViewport().setView(component);
		}
	}
	
	private class Graph extends JLabel {
	 		
		private static final long serialVersionUID = 3L;

		public Graph() {
 			super();
 			
 			// DIE PREFERRED SIZE IST GANZ WICHTIG FUER
 			// die JScrollPane. Ohne Preferred Size wird
 			// nichts gescrolled. Siehe auch Java Doku !
 			setPreferredSize(new Dimension(1200,5000));
 			
 			setHorizontalAlignment(SwingConstants.LEFT);
 		    setVerticalAlignment(SwingConstants.TOP);
 		}
 	 		
        public void paint (Graphics g) {
        	super.paint(g);
        	
        	// Antialiasing verwenden
        	Graphics2D g2d = (Graphics2D) g;
        	g2d.setRenderingHint(
        		RenderingHints.KEY_ANTIALIASING,
        			RenderingHints.VALUE_ANTIALIAS_ON);
        	
        	g.setColor(Color.ORANGE);
            g.fillRect(0, 0, getSize().width, getSize().height);
            g.setColor(UIManager.getColor("Label.foreground"));
             
        	if (error) g.drawString(errorMessage,10,10);	// Fehler aufgetreten ?
    		else {	
    			
    			// 1. Teil darstellen (bfs-Djikstra)
				al1.draw(g);
				al2.draw(g);
				
				al3.draw(g);
				al4.draw(g);
				
				al5.draw(g);
				al6.draw(g);
				
				// 2. Teil darstellen (topological sort - maximal flow)
				al7.draw(g);		
				al8.draw(g);
				
				al9.draw(g); 
				al10.draw(g);
				
				al11.draw(g);
				al12.draw(g);
				
				al13.draw(g);
				al14.draw(g);
				
				al15.draw(g);
				al16.draw(g);  			
    		}
        }
 	}
 	
 	public static void main(String[] args) {
    	run(new JAlgoApplet(), 800, 600);
  	}

  	public static void run(JApplet applet, int width, int height) {
    	JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().add(applet);
	    frame.setSize(width, height);
    	applet.init();
	    applet.start();
	    frame.setTitle("Graph Algorithms by 4irmann 1997-2007 (you see dynamically computed output)");
    	frame.setVisible(true);
  	}
 	
}
 
