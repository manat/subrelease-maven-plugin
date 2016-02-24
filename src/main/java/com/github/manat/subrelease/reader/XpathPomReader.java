package com.github.manat.subrelease.reader;

import com.github.manat.subrelease.model.Dependency;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Reads Maven pom information using xpath.
 */
public class XpathPomReader implements PomReader {

    private Document pomDoc;

    public XpathPomReader(Path pomFile) {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = domFactory.newDocumentBuilder();
            pomDoc = builder.parse(pomFile.toFile());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Dependency> getSnapshotDependencies() {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression xPathExpression = xPath
                    .compile("//dependency[version[contains(., '-SNAPSHOT')]]");
            NodeList nl = (NodeList) xPathExpression.evaluate(pomDoc, XPathConstants.NODESET);

            if (nl == null || nl.getLength() == 0) {
                return Collections.emptyList();
            }

            List<Dependency> dependencies = new ArrayList<>();
            Dependency dependency;

            for (int i = 0; i < nl.getLength(); i++) {
                NodeList children = nl.item(i).getChildNodes();
                dependency = new Dependency();

                for (int j = 0; j < children.getLength(); j++) {
                    if (children.item(j) instanceof Element) {
                        Element e = (Element) children.item(j);

                        switch (e.getTagName()) {
                        case "groupId":
                            dependency.setGroupId(e.getTextContent());
                            break;
                        case "artifactId":
                            dependency.setArtifactId(e.getTextContent());
                            break;
                        case "version":
                            dependency.setVersion(e.getTextContent());
                            break;
                        default:
                            assert false : "Node children is not compatible with Dependency.";
                        }

                    }
                }

                dependencies.add(dependency);
            }

            return dependencies;

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    /**
     * Retrieves a scm connection from pom, by looking specifically at scm/developerConnection.
     *
     * @return a scm connection indicates scm type, and connection information; {@code null}
     * otherwise
     */
    public String getScmConnection() {
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression xPathExpression = xPath.compile("//scm/developerConnection");
            NodeList nl = (NodeList) xPathExpression.evaluate(pomDoc, XPathConstants.NODESET);

            if (nl == null || nl.getLength() == 0) {
                return null;
            }

            return nl.item(0).getTextContent().trim();

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
