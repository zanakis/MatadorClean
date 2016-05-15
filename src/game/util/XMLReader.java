package game.util;

import java.io.File;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLReader {

	private static Document document;

	public XMLReader(String filePath){
		loadDocument(filePath);
	}

	// References a file. and parsing it to the documentproperty
	public static void loadDocument(String filePath) {
		try{
                    
			File file =  new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			document = dBuilder.parse(file);
			document.getDocumentElement().normalize(); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	// Gets stringNumber element text in elements named stringname
	public String getElement(String stringName, int stringNumber){
		NodeList  nList = document.getElementsByTagName(stringName);
		return nList.item(stringNumber).getTextContent();
	}
}
