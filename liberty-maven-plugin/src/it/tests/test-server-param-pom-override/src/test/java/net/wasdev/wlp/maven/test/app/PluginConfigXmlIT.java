package net.wasdev.wlp.maven.test.app;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.codehaus.plexus.util.FileUtils;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import static junit.framework.Assert.*;

public class PluginConfigXmlIT {
    
    public final String CONFIG_XML = "target/liberty-plugin-config.xml";
    public final String TARGET_BOOTSTRAP_PROPERTIES = "target/liberty/usr/servers/test/bootstrap.properties";
    public final String TARGET_JVM_OPTIONS = "target/liberty/usr/servers/test/jvm.options";
    
    @Test
    public void testConfigPropFileExist() throws Exception {
        File f = new File(CONFIG_XML);
        assertTrue(f.getCanonicalFile() + " doesn't exist", f.exists());
    }
    
    @Test
    public void testBootstrapPropertiesFileElements() throws Exception {
        File in = new File(CONFIG_XML);
        FileInputStream input = new FileInputStream(in);
        
        // get input XML Document
        DocumentBuilderFactory inputBuilderFactory = DocumentBuilderFactory.newInstance();
        inputBuilderFactory.setIgnoringComments(true);
        inputBuilderFactory.setCoalescing(true);
        inputBuilderFactory.setIgnoringElementContentWhitespace(true);
        inputBuilderFactory.setValidating(false);
        DocumentBuilder inputBuilder = inputBuilderFactory.newDocumentBuilder();
        Document inputDoc = inputBuilder.parse(input);
        
        // parse input XML Document
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/liberty-plugin-config/bootstrapPropertiesFile";
        NodeList nodes = (NodeList) xPath.compile(expression).evaluate(inputDoc, XPathConstants.NODESET);
        assertEquals("Number of bootstrapPropertiesFile element ==>", 0, nodes.getLength());
        
        expression = "/liberty-plugin-config/bootstrapProperties";
        nodes = (NodeList) xPath.compile(expression).evaluate(inputDoc, XPathConstants.NODESET);
        assertEquals("Number of bootstrapProperties element ==>", 1, nodes.getLength());
        
        assertEquals("verify target server bootstrap.properties", "# Generated by liberty-maven-plugin\nlocation=pom.xml\n",
                FileUtils.fileRead(TARGET_BOOTSTRAP_PROPERTIES).replaceAll("\r",""));
    }
    
    @Test
    public void testJvmOptionsFileElements() throws Exception {
        File in = new File(CONFIG_XML);
        FileInputStream input = new FileInputStream(in);
        
        // get input XML Document
        DocumentBuilderFactory inputBuilderFactory = DocumentBuilderFactory.newInstance();
        inputBuilderFactory.setIgnoringComments(true);
        inputBuilderFactory.setCoalescing(true);
        inputBuilderFactory.setIgnoringElementContentWhitespace(true);
        inputBuilderFactory.setValidating(false);
        DocumentBuilder inputBuilder = inputBuilderFactory.newDocumentBuilder();
        Document inputDoc = inputBuilder.parse(input);
        
        // parse input XML Document
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "/liberty-plugin-config/jvmOptionsFile";
        NodeList nodes = (NodeList) xPath.compile(expression).evaluate(inputDoc, XPathConstants.NODESET);
        assertEquals("Number of jvmOptionsFile element ==>", 0, nodes.getLength());
        
        expression = "/liberty-plugin-config/jvmOptions";
        nodes = (NodeList) xPath.compile(expression).evaluate(inputDoc, XPathConstants.NODESET);
        assertEquals("Number of jvmOptions element ==>", 1, nodes.getLength());
        
        assertEquals("verify target server jvm.options", "# Generated by liberty-maven-plugin\n-Xmx768m\n",
                FileUtils.fileRead(TARGET_JVM_OPTIONS).replaceAll("\r",""));
    }
}
