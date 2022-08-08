package com.tuling.xml;

import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.parsing.PropertyParser;
import org.apache.ibatis.parsing.XNode;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class TestXml {

    Document document;
    @Before
    public void before() throws ParserConfigurationException, IOException, SAXException {

        String resource = "mybatis-config.xml";

        Reader reader = Resources.getResourceAsReader(resource);

        // 1
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(false);
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(false);
        factory.setCoalescing(false);
        factory.setExpandEntityReferences(true);

        // 2
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setEntityResolver(new XMLMapperEntityResolver());

        //document 表示整个文档（DOM 树的根节点）
        document= builder.parse(new InputSource(reader));
    }

    @Test
    public void test01() throws XPathExpressionException {
        // 3
        XPathFactory factory = XPathFactory.newInstance();
        // 4
        XPath xPath = factory.newXPath();
        // 5  node表示一个节点
        Node node = (Node) xPath.evaluate("/configuration", document, XPathConstants.NODE);

        System.out.println(toStringNode(node));
    }


    public  static String toStringNode(Node node){
        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append(node.getNodeName());
        NamedNodeMap attributeNodes = node.getAttributes();
        if (attributeNodes != null) {
            for (int i = 0; i < attributeNodes.getLength(); i++) {
                Node attribute = attributeNodes.item(i);
                String value = PropertyParser.parse(attribute.getNodeValue(), null);
                //System.out.println(attribute.getNodeName()+":"+ value);
                builder.append(" ");
                builder.append(attribute.getNodeName());
                builder.append("=\"");
                builder.append(value);
                builder.append("\"");
            }
        }
        NodeList nodeList = node.getChildNodes();
        if (nodeList != null) {
            builder.append(nodeList.getLength()==0?">":">\n");
            for (int i = 0, n = nodeList.getLength(); i < n; i++) {
                Node cNode = nodeList.item(i);
                if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                    //System.out.println(cNode.getNodeName()+":"+ cNode.getNodeValue());

                    builder.append(toStringNode(cNode))  ;
                }
            }

            builder.append("</");
            builder.append(node.getNodeName());
            builder.append(">");
        }
        builder.append("\n");
        return  builder.toString();
    }

    public Properties getChildrenAsProperties(Node node) {
        Properties properties = new Properties();
        NodeList childNodes = node.getChildNodes();
        for (int i = 0, n = childNodes.getLength(); i < n; i++) {
            Node child=childNodes.item(n);
            String name = child.getNodeName();
            String value = child.getNodeValue();
            if (name != null && value != null) {
                properties.setProperty(name, value);
            }
        }
        return properties;
    }
}
