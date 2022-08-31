package com.amboiko.dom;

import com.amboiko.dom.model.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DomParser {
    DocumentBuilder documentBuilder;
    Document document;
    Project project;

    public DomParser() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        documentBuilder = factory.newDocumentBuilder();
        project = new Project();
    }

    void parseDocument() throws IOException, SAXException {
        document = documentBuilder.parse(new File("pom.xml"));

        project.setModelVersion(getDocumentValueBy("modelVersion"));
        project.setGroupId(getDocumentValueBy("groupId"));
        project.setArtifactId(getDocumentValueBy("artifactId"));
        project.setVersion(getDocumentValueBy("version"));
        project.setDependencies(getDependencies());
        project.setProperties(getProperties());
        project.setBuild(getBuild());
    }

    private Build getBuild() {
        final Build build = new Build();
        build.setPlugins(getPlugins());

        return build;
    }

    private List<Plugin> getPlugins() {
        final List<Plugin> result = new ArrayList<>();
        NodeList list = document.getElementsByTagName("plugins");

        for (int i = 0; i < list.getLength(); i++) {
            NodeList nodeList = list.item(i).getChildNodes();
            Node current;

            for (int j = 0; j < nodeList.getLength(); j++) {
                current = nodeList.item(j);
                if (current.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) current;
                    Plugin plugin = new Plugin();
                    plugin.setGroupId(getElementValueBy("groupId", eElement));
                    plugin.setArtifactId(getElementValueBy("artifactId", eElement));
                    plugin.setConfiguration(getConfiguration(eElement.getElementsByTagName("configuration").item(0)));
                    result.add(plugin);
                }
            }
        }
        return result;

    }

    private Configuration getConfiguration(Node node) {
        Configuration configuration = new Configuration();
        Archive archive = new Archive();
        List<Manifest> list = new ArrayList<>();

        Node first = ((Element) node).getElementsByTagName("manifest").item(0);
        NodeList nodeList = first.getChildNodes();
        Node current;
        for (int i = 0; i < nodeList.getLength(); i++) {
            current = nodeList.item(i);
            if (current.getNodeType() == Node.ELEMENT_NODE) {
                Manifest manifest = new Manifest();
                manifest.setName(current.getNodeName());
                manifest.setValue(current.getTextContent());
                list.add(manifest);
            }
        }

        archive.setManifests(list);
        configuration.setArchive(archive);
        return configuration;
    }

    private List<Property> getProperties() {
        final List<Property> result = new ArrayList<>();
        Node node = document.getElementsByTagName("properties").item(0);

        NodeList nodeList = node.getChildNodes();
        Node current;
        for (int i = 0; i < nodeList.getLength(); i++) {
            current = nodeList.item(i);
            if (current.getNodeType() == Node.ELEMENT_NODE) {
                Property property = new Property();
                property.setName(current.getNodeName());
                property.setValue(current.getTextContent());
                result.add(property);
            }
        }
        return result;
    }

    private List<Dependency> getDependencies() {
        final List<Dependency> result = new ArrayList<>();
        NodeList list = document.getElementsByTagName("dependencies");

        for (int i = 0; i < list.getLength(); i++) {
            NodeList nodeList = list.item(i).getChildNodes();
            Node current;

            for (int j = 0; j < nodeList.getLength(); j++) {
                current = nodeList.item(j);
                if (current.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) current;
                    Dependency dependency = new Dependency();
                    dependency.setGroupId(getElementValueBy("groupId", eElement));
                    dependency.setArtifactId(getElementValueBy("artifactId", eElement));
                    dependency.setVersion(getElementValueBy("version", eElement));
                    result.add(dependency);
                }
            }
        }

        return result;
    }

    String getDocumentValueBy(final String tagName) {
        return document.getElementsByTagName(tagName).item(0).getTextContent();
    }

    String getElementValueBy(final String tagName, final Element element) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    public Project getProject() {
        return project;
    }

    public boolean validateXMLSchema() {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("pom.xsd");

        try {
            Source schemaSource = new StreamSource(inputStream);
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(schemaSource);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File("pom.xml")));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }
}
