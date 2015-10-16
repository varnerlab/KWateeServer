package org.varnerlab.kwatee.foundation;

/**
 * Copyright (c) 2015 Varnerlab,
 * School of Chemical Engineering,
 * Purdue University, West Lafayette IN 46077 USA.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.

 * Created by jeffreyvarner on 10/8/15.
 */


// imports -
import java.util.Hashtable;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class VLCGGenerator implements VLCGConfigurable {

    // Instance variables -
    private Hashtable<String,String> _properties_table = new Hashtable<String,String>();
    private XPathFactory  _xpath_factory = XPathFactory.newInstance();
    private XPath _xpath_object = _xpath_factory.newXPath();

    // specific strings we use for properties -
    public static final String JAR_PLUGIN_DIRECTORY = "JAR_PLUGIN_DIRECTORY";

    public VLCGGenerator() {
    }

    public static void main(String[] args){

        // Method variables -
        VLCGGenerator server = new VLCGGenerator();
        VLCGTranslator translator = new VLCGTranslator();
        VLCGTransformationPropertyTree configuration_properties_tree = new VLCGTransformationPropertyTree();

        // check - do we have the configuration file?
        if (args[0] == null){
            return;
        }

        try {

            // Get the path to the transformation properties file -
            String path_to_configuration_file = args[0];

            // Configure the server -
            server.doInitializeServer(path_to_configuration_file, (VLCGConfigurable) server);

            // ok, get the plugin directory ...
            String string_plugin_directory = (String)server.getProperty(VLCGGenerator.JAR_PLUGIN_DIRECTORY);

            // Load the file -
            File plugin_file = new File(string_plugin_directory);
            File[] jar_file_array = plugin_file.listFiles();

            // ok, let's load the plugin jars -
            int NUMBER_OF_FILES = jar_file_array.length;
            for (int index=0;index<NUMBER_OF_FILES;index++)
            {
                File tmpFile = jar_file_array[index];
                VLCGPluginJarLoader.addFile(tmpFile);
            }

            // load up the transformation property tree -
            configuration_properties_tree.loadResource(path_to_configuration_file);

            // execute the translator job -
            translator.doExecute(configuration_properties_tree);
        }
        catch (Exception error) {

            // Print out error
            System.out.println("MAJOR MALFUNCTION: Local transport layer encountered an error on launch. ERROR: "+error.getMessage());

            // Send out stack trace
            System.out.println("---------------------------------- STARTING STACKTRACE ------------------------------- \n");
            error.printStackTrace();
        }
    }

    @Override
    public void setProperty(String key, String value) {

        if (key != null && value != null){
            _properties_table.put(key,value);
        }
    }

    @Override
    public String getProperty(String key) {

        if (key != null && _properties_table.containsKey(key) == true){
            return _properties_table.get(key);
        }

        return null;
    }

    public void doInitializeServer(String path_to_configuration_file, VLCGConfigurable generator_object) throws Exception {
        // check the arguments -
        if (path_to_configuration_file == null || generator_object == null){
            return;
        }

        // Load the XML prop file -
        File configFile = new File(path_to_configuration_file);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(configFile);
        doc.getDocumentElement().normalize();

        // Load the plugin directory -
        XPathExpression expression = _xpath_object.compile(".//path[@symbol='KWATEE_PLUGINS_JAR_DIRECTORY']/@path_location");
        NodeList pluginNode = (NodeList)expression.evaluate(doc, XPathConstants.NODESET);
        String pluginFilePath = pluginNode.item(0).getNodeValue();

        // Store some properties -
        generator_object.setProperty(VLCGGenerator.JAR_PLUGIN_DIRECTORY,pluginFilePath);
    }
}
