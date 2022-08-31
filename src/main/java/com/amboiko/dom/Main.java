package com.amboiko.dom;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            DomParser domParser = new DomParser();
            if (domParser.validateXMLSchema()) {
                domParser.parseDocument();
                System.out.println(domParser.getProject());
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}
