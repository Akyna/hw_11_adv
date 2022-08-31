package com.amboiko.sax;

import com.amboiko.sax.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
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

public class SaxHandler extends DefaultHandler {

    private static final String MODEL_VERSION = "modelVersion";
    private static final String GROUP_ID = "groupId";
    private static final String ARTIFACT_ID = "artifactId";
    private static final String VERSION = "version";
    private static final String PROPERTIES = "properties";
    private static final String MAVEN_COMPILER_SOURCE = "maven.compiler.source";
    private static final String MAVEN_COMPILER_TARGET = "maven.compiler.target";

    private static final String DEPENDENCIES = "dependencies";
    private static final String DEPENDENCY = "dependency";

    private static final String BUILD = "build";
    private static final String PLUGINS = "plugins";
    private static final String PLUGIN = "plugin";
    private static final String CONFIGURATION = "configuration";
    private static final String ARCHIVE = "archive";
    private static final String MANIFEST = "manifest";
    private static final String ADD_CLASSPATH = "addClasspath";
    private static final String MAIN_CLASS = "mainClass";


    boolean isModelVersion = false;
    boolean isGroupId = false;
    boolean isArtifactId = false;
    boolean isVersion = false;

    boolean isProperties = false;
    boolean isMavenCompilerSource = false;
    boolean isMavenCompilerTarget = false;

    boolean isDependencies = false;
    boolean isDependency = false;

    boolean isBuild = false;
    boolean isPlugins = false;
    boolean isPlugin = false;
    boolean isConfiguration = false;
    boolean isArchive = false;
    boolean isManifest = false;
    boolean isAddClasspath = false;
    boolean isMainClass = false;


    private final Project project = new Project();
    private List<Dependency> tmpDependencies;
    private Dependency tmpDependency;
    private List<Property> tmpProperties;
    private Property tmpProperty;
    private Build tmpBuild;
    private List<Plugin> tmpPlugins;
    private Plugin tmpPlugin;
    private Configuration tmpConfiguration;
    private Archive tmpArchive;
    private List<Manifest> tmpManifests;
    private Manifest tmpManifest;

    private StringBuilder data = null;

    public Project getProject() {
        return project;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase(MODEL_VERSION)) {
            isModelVersion = true;
        } else if (qName.equalsIgnoreCase(GROUP_ID)) {
            isGroupId = true;
        } else if (qName.equalsIgnoreCase(ARTIFACT_ID)) {
            isArtifactId = true;
        } else if (qName.equalsIgnoreCase(VERSION)) {
            isVersion = true;
        } else if (qName.equalsIgnoreCase(PROPERTIES)) {
            tmpProperties = new ArrayList<>();
            isProperties = true;
        } else if (qName.equalsIgnoreCase(MAVEN_COMPILER_SOURCE)) {
            tmpProperty = new Property();
            isMavenCompilerSource = true;
        } else if (qName.equalsIgnoreCase(MAVEN_COMPILER_TARGET)) {
            isMavenCompilerTarget = true;
        } else if (qName.equalsIgnoreCase(DEPENDENCIES)) {
            tmpDependencies = new ArrayList<>();
            isDependencies = true;
        } else if (qName.equalsIgnoreCase(DEPENDENCY)) {
            tmpDependency = new Dependency();
            isDependency = true;
        } else if (qName.equalsIgnoreCase(BUILD)) {
            tmpBuild = new Build();
            isBuild = true;
        } else if (qName.equalsIgnoreCase(PLUGINS)) {
            tmpPlugins = new ArrayList<>();
            isPlugins = true;
        } else if (qName.equalsIgnoreCase(PLUGIN)) {
            tmpPlugin = new Plugin();
            isPlugin = true;
        } else if (qName.equalsIgnoreCase(CONFIGURATION)) {
            tmpConfiguration = new Configuration();
            isConfiguration = true;
        } else if (qName.equalsIgnoreCase(ARCHIVE)) {
            tmpArchive = new Archive();
            isArchive = true;
        } else if (qName.equalsIgnoreCase(MANIFEST)) {
            tmpManifests = new ArrayList<>();
            isManifest = true;
        } else if (qName.equalsIgnoreCase(ADD_CLASSPATH)) {
            tmpManifest = new Manifest();
            isAddClasspath = true;
        } else if (qName.equalsIgnoreCase(MAIN_CLASS)) {
            tmpManifest = new Manifest();
            isMainClass = true;
        }

        // create the data container
        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase(MODEL_VERSION)) {
            project.setModelVersion(data.toString());
            isModelVersion = false;
        } else if (qName.equalsIgnoreCase(GROUP_ID)) {
            if (isDependency) {
                tmpDependency.setGroupId(data.toString());
            } else if (isPlugin) {
                tmpPlugin.setGroupId(data.toString());
            } else {
                project.setGroupId(data.toString());
            }
            isGroupId = false;
        } else if (qName.equalsIgnoreCase(ARTIFACT_ID)) {
            if (isDependency) {
                tmpDependency.setArtifactId(data.toString());
            } else if (isPlugin) {
                tmpPlugin.setArtifactId(data.toString());
            } else {
                project.setArtifactId(data.toString());
            }
            isArtifactId = false;
        } else if (qName.equalsIgnoreCase(VERSION)) {
            if (isDependency) {
                tmpDependency.setVersion(data.toString());
            } else {
                project.setVersion(data.toString());
            }
            isVersion = false;
        } else if (qName.equalsIgnoreCase(PROPERTIES)) {
            project.setProperties(tmpProperties);
            isProperties = false;
        } else if (qName.equalsIgnoreCase(MAVEN_COMPILER_SOURCE)) {
            tmpProperty.setName(MAVEN_COMPILER_SOURCE);
            tmpProperty.setValue(data.toString());
            tmpProperties.add(tmpProperty);
            isMavenCompilerSource = false;
        } else if (qName.equalsIgnoreCase(MAVEN_COMPILER_TARGET)) {
            tmpProperty.setName(MAVEN_COMPILER_TARGET);
            tmpProperty.setValue(data.toString());
            tmpProperties.add(tmpProperty);
            isMavenCompilerTarget = false;
        } else if (qName.equalsIgnoreCase(DEPENDENCIES)) {
            project.setDependencies(tmpDependencies);
            isDependencies = false;
        } else if (qName.equalsIgnoreCase(DEPENDENCY)) {
            tmpDependencies.add(tmpDependency);
            isDependency = false;
        } else if (qName.equalsIgnoreCase(BUILD)) {
            project.setBuild(tmpBuild);
            isBuild = false;
        } else if (qName.equalsIgnoreCase(PLUGINS)) {
            tmpBuild.setPlugins(tmpPlugins);
            isPlugins = false;
        } else if (qName.equalsIgnoreCase(PLUGIN)) {
            tmpPlugins.add(tmpPlugin);
            isPlugin = false;
        } else if (qName.equalsIgnoreCase(CONFIGURATION)) {
            tmpPlugin.setConfiguration(tmpConfiguration);
            isConfiguration = false;
        } else if (qName.equalsIgnoreCase(ARCHIVE)) {
            tmpConfiguration.setArchive(tmpArchive);
            isArchive = false;
        } else if (qName.equalsIgnoreCase(MANIFEST)) {
            tmpArchive.setManifests(tmpManifests);
            isManifest = false;
        } else if (qName.equalsIgnoreCase(ADD_CLASSPATH)) {
            tmpManifest.setName(ADD_CLASSPATH);
            tmpManifest.setValue(data.toString());
            tmpManifests.add(tmpManifest);
            isAddClasspath = false;
        } else if (qName.equalsIgnoreCase(MAIN_CLASS)) {
            tmpManifest.setName(MAIN_CLASS);
            tmpManifest.setValue(data.toString());
            tmpManifests.add(tmpManifest);
            isMainClass = false;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
//        System.out.println(new String(ch, start, length));
//        System.out.println("=======================");
        data.append(new String(ch, start, length));
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

