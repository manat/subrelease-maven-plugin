package com.github.manat.subrelease.writer;

import com.github.manat.subrelease.model.Artifact;
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
import java.util.List;

/**
 * Manipulates pom information using XPath.
 */
public class XpathPomWriter implements PomWriter {

    private Document pomDoc;

    public XpathPomWriter(Path pomFile) {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = domFactory.newDocumentBuilder();
            pomDoc = builder.parse(pomFile.toFile());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO : Incomplete
     *
     * @param artifacts the given SNAPSHOT dependencies
     * @return
     */
    @Override
    public boolean updateSnapshotVersion(List<Artifact> artifacts) {
        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression;
        try {
            xPathExpression = xPath.compile("//dependency[version[contains(., '-SNAPSHOT')]]");
            NodeList nl = (NodeList) xPathExpression.evaluate(pomDoc, XPathConstants.NODESET);

            if (nl == null || nl.getLength() == 0) {
                return false;
            }

            for (int i = 0; i < nl.getLength(); i++) {
                NodeList children = nl.item(i).getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    if (children.item(j) instanceof Element) {
                        Element e = (Element) children.item(j);
                        if ("version".equals(e.getTagName())) {
                            String release = e.getTextContent().trim().replace("-SNAPSHOT", "");
                            e.setNodeValue(release);
                        }
                    }
                }
            }

            return true;

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return false;
    }
}
