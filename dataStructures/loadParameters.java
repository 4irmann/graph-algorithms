package dataStructures;
import java.net.*;
import java.io.*;
import smsoftText.*;
import standardtext.*;

//
// CLASS: loadParameters
//
/* Laedt eine Textdatei uebers Internet oder lokal von Platte (als Parameter wird ein URL uebergeben)
   und Liefert die Textdatei als String-Objekt zurueck */

public class loadParameters {
	
	// Speichert die documentbase entspricht absolutem Pfad der Textdatei
	private URL documentBase;
	
	/* CONSTRUCTOR: mit url als documentbase
	u = documentbase */
	public loadParameters(URL u) {
		documentBase = u;
	}

	// CONSTRUCTOR: empty
	public loadParameters() {
	}
	
	/* FUNCTION: getParameters
	url = absolute URL oder relative URL (hier ist Voraussetzung, dass documentbase nicht null ist) */
	public String getParameters(URL url) throws loadParametersException {
        
		// Variablen, Objekte
		String returnValue = new String();
		DataInputStream dis;

		// try-Block faengt Ladefehler ab
		try {
    
			URL ParametersUrl = new URL(documentBase,url.toString()); 
			URLConnection ParametersConnection = ParametersUrl.openConnection();
			dis = new DataInputStream(ParametersConnection.getInputStream());
			returnValue = readParameters(dis);
			dis.close();
    
		} catch (MalformedURLException e) {
			throw new loadParametersException("The requested url could not be retrieved",e.getMessage()); 
		}
		catch (IOException e) {
			throw new loadParametersException("The requested url could not be retrieved",e.getMessage()); 
		}
	
		return returnValue;		
	}
	
	/* FUNCTION: getParameters
	url = absolute URL oder relative URL (hier ist Voraussetzung, dass documentbase nicht null ist) */
	public String getParameters(String resourcePath) throws loadParametersException {
        
		// Variablen, Objekte
	
		String returnValue = new String();
		DataInputStream dis;

		// try-Block faengt Ladefehler ab
		try {
			ClassLoader cl = getClass().getClassLoader();
			dis = new DataInputStream(cl.getResourceAsStream(resourcePath));
			if (dis == null) {
				throw new IOException("error loading resource: "+resourcePath);
			}
			returnValue = readParameters(dis);
			dis.close();
		}
		catch (IOException e) {
			throw new loadParametersException("The requested resource could not be retrieved",e.getMessage()); 
		}
	
		return returnValue;		
	}
	
	public String readParameters(DataInputStream dis) throws IOException {
		String inputLine;
		String returnValue = new String();
		while ((inputLine = dis.readLine()) != null) {
			returnValue = returnValue.concat(inputLine); // Zeile fuer Zeile an return-String anhaengen
			returnValue = returnValue.concat("\n");		 // zusaetzlich linefeed anhaengen
		}  
		return returnValue;
	}

}


