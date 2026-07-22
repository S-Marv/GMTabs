package dev.wick.gmtabs.files;

import dev.wick.gmtabs.tab.TabConfig;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ConfigFile {
	private final File file;
	private static final DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();

	public ConfigFile(String filePath){
		this.file = new File(filePath);
	}

	public ConfigFile(File file){
		this.file = file;
	}

	public void save(List<TabConfig> configurations){
		try {
			System.out.println(file.getAbsolutePath());
			ConfigXMLBuilder builder = new ConfigXMLBuilder(factory.newDocumentBuilder());
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource(builder.build(configurations));
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (Exception e){
			throw new RuntimeException(e);
		}
	}

	public List<TabConfig> load(){
		try{
			return parse(factory.newDocumentBuilder().parse(file));
		} catch (ParserConfigurationException | IOException | SAXException e) {
			//Logs.logException(e);
			throw new RuntimeException(e);
		}
	}

	public static List<TabConfig> load(InputStream inputStream) throws IOException, SAXException {
		Document document = makeBuilder().parse(inputStream);
		inputStream.close();
		return parse(document);
	}

	private static List<TabConfig> parse(Document document){
		ConfigXMLReader reader = new ConfigXMLReader();
		return reader.parse(document);
	}

	private static DocumentBuilder makeBuilder(){
		try {
			return factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
}
