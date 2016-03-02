package org.varnerlab.kwatee.foundation;

// imports -
import java.io.File;
import java.io.FileReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.*;


/**
 * Copyright (c) 2015 Varnerlab,
 * School of Chemical Engineering,
 * Purdue University, West Lafayette IN 46077 USA.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * <p>
 * Created by jeffreyvarner on 10/8/15.
 */

public class VLCGTransformationPropertyTree implements VLCGInputHandler {

    // Instance variables -
    private XPathFactory  _xpath_factory = XPathFactory.newInstance();
    private XPath _xpath = _xpath_factory.newXPath();
    private Document _document_transformation_property_tree = null;

    @Override
    public void setPropertiesTree(VLCGTransformationPropertyTree properties_tree) {
    }

    @Override
    public void loadResource(Object object) throws Exception {

        if (object == null){
            return;
        }

        try {

            // Grab the model file -
            String strPath = (String)object;

            // Ok, let's load the model file and then hand the DOM tree back -or- allow them to run xpath hits against the tree -
            File configFile = new File(strPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);

            // Make the document factory, and the build the transformation tree -
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            _document_transformation_property_tree = dBuilder.parse(configFile);
            _document_transformation_property_tree.getDocumentElement().normalize();
        }
        catch (Exception error) {
            System.out.println("ERROR: No bueno. We have a malfunction loading the properties file. " + error.toString());
        }
    }


    @Override
    public Object getResource(Object object) throws Exception {
        return _document_transformation_property_tree;
    }


    public String lookupKwateeDebugPath() throws Exception {

        // Lookup the location of the KWATEE_DEBUG_OUTPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_DEBUG_OUTPUT_PATH']/@path_location";
        String debug_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);
        return debug_path;
    }

    public String lookupKwateeModelName() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = "./Model/@model_name";
        String model_name = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);
        return model_name;
    }

    public String lookupKwateeStoichiometricMatrixFilePath() throws Exception {

        // Lookup the location of the KWATEE_NETWORK_OUTPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_NETWORK_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//StoichiometricMatrix[@path_symbol='KWATEE_NETWORK_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }

    public String lookupKwateeCompartmentConnectivityMatrixFilePath() throws Exception {

        // Lookup the location of the KWATEE_NETWORK_OUTPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_NETWORK_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//CompartmentConnectivityMatrix[@path_symbol='KWATEE_NETWORK_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }

    /**
     * Return the fully qualified path to the NetworkFile
     * @return String - path to the NetworkFile
     */
    public String lookupKwateeNetworkFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_INPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//NetworkFile[@path_symbol='KWATEE_INPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }

    /**
     * Return the fully qualified path to the OrderFile
     * @return String - path to the OrderFile
     */
    public String lookupKwateeSpeciesOrderFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_INPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//OrderFile[@path_symbol='KWATEE_INPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }

    /**
     * Return the fully qualified path to the NetworkFile
     * @return String - path to the NetworkFile
     */
    public String lookupKwateeDataDictionaryFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//DataFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }

    /**
     * Return the fully qualified path to the NetworkFile
     * @return String - path to the NetworkFile
     */
    public String lookupKwateeDataDictionaryFunctionName() throws Exception {

        // Lookup the network file name -
        String xpath_network_filename = ".//DataFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String function_name = filename.split("\\.")[0];

        // return -
        return function_name;
    }

    public String lookupKwateeDriverFunctionName() throws Exception {

        // Lookup the network file name -
        String xpath_network_filename = ".//DriverFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String function_name = filename.split("\\.")[0];

        // return -
        return function_name;
    }

    public String lookupKwateeDriverFunctionFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//DriverFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }

    public String lookupKwateeBalanceFunctionName() throws Exception {

        // Lookup the network file name -
        String xpath_network_filename = ".//BalanceFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String function_name = filename.split("\\.")[0];

        // return -
        return function_name;
    }

    public String lookupKwateeBalanceFunctionFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//BalanceFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }


    public String lookupKwateeKineticsFunctionName() throws Exception {

        // Lookup the network file name -
        String xpath_network_filename = ".//KineticsFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String function_name = filename.split("\\.")[0];

        // return -
        return function_name;
    }

    public String lookupKwateeKineticsFunctionFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//KineticsFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }


    public String lookupKwateeControlFunctionName() throws Exception {

        // Lookup the network file name -
        String xpath_network_filename = ".//ControlFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String function_name = filename.split("\\.")[0];

        // return -
        return function_name;
    }

    public String lookupKwateeControlFunctionFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//ControlFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }

    public String lookupKwateeBoundsFunctionName() throws Exception {

        // Lookup the network file name -
        String xpath_network_filename = ".//BoundsFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String function_name = filename.split("\\.")[0];

        // return -
        return function_name;
    }

    public String lookupKwateeBoundsFunctionFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//BoundsFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }

    public String lookupKwateeTypesLibraryName() throws Exception {

        // Lookup the network file name -
        String xpath_network_filename = ".//TypesLibrary[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String function_name = filename.split("\\.")[0];

        // return -
        return function_name;
    }

    public String lookupKwateeTypesLibraryFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//TypesLibrary[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }


    public String lookupKwateeCompartmentFlowFunctionName() throws Exception {

        // Lookup the network file name -
        String xpath_network_filename = ".//FlowFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String function_name = filename.split("\\.")[0];

        // return -
        return function_name;
    }


    public String lookupKwateeCompartmentFlowFunctionFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//FlowFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }

    public String lookupKwateeDilutionRateFunctionName() throws Exception {

        // Lookup the network file name -
        String xpath_network_filename = ".//DilutionFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String function_name = filename.split("\\.")[0];

        // return -
        return function_name;
    }

    public String lookupKwateeDilutionFunctionFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//DilutionFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }

    public String lookupKwateeHeartRateFunctionName() throws Exception {

        // Lookup the network file name -
        String xpath_network_filename = ".//HeartRateFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String function_name = filename.split("\\.")[0];

        // return -
        return function_name;
    }

    public String lookupKwateeHeartRateFunctionFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//HeartRateFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }


    public String lookupKwateeCardiacDistributionFunctionName() throws Exception {

        // Lookup the network file name -
        String xpath_network_filename = ".//CardiacDistributionFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String function_name = filename.split("\\.")[0];

        // return -
        return function_name;
    }

    public String lookupKwateeCardiacDistributionFunctionFilePath() throws Exception {

        // Lookup the location of the KWATEE_INPUT_PATH -
        String xpath_input_path = ".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location";
        String input_path = lookupPropertyValueFromTreeUsingXPath(xpath_input_path);

        // Lookup the network file name -
        String xpath_network_filename = ".//CardiacDistributionFunction[@path_symbol='KWATEE_SOURCE_OUTPUT_PATH']/@filename";
        String filename = lookupPropertyValueFromTreeUsingXPath(xpath_network_filename);

        // Put these together -
        String fully_qualified_path = input_path+filename;

        // return -
        return fully_qualified_path;
    }



    public boolean isKwateeModelLargeScaleOptimized() throws Exception {

        // Lookup the network file name -
        String xpath_network_metadata = ".//Model/@large_scale_optimized";
        String metadata = lookupPropertyValueFromTreeUsingXPath(xpath_network_metadata);

        return Boolean.valueOf(metadata);
    }

    public String lookupKwateeModelUsername() throws Exception {

        // Lookup the network file name -
        String xpath_network_metadata = ".//Model/@username";
        String metadata = lookupPropertyValueFromTreeUsingXPath(xpath_network_metadata);

        return metadata;
    }

    public String lookupKwateeModelType() throws Exception {

        // Lookup the network file name -
        String xpath_network_metadata = ".//Model/@model_type";
        String metadata = lookupPropertyValueFromTreeUsingXPath(xpath_network_metadata);

        return metadata;
    }

    public String lookupKwateeModelVersion() throws Exception {

        // Lookup the network file name -
        String xpath_network_metadata = ".//Model/@model_version";
        String metadata = lookupPropertyValueFromTreeUsingXPath(xpath_network_metadata);

        return metadata;
    }

    /**
     * Return the string value obtained from executing the XPath query passed in as an argument
     * @return String - get property from uxml tree by executing string in strXPath
     */
    public String lookupPropertyValueFromTreeUsingXPath(String xpath_string) throws Exception {

        if (xpath_string == null)
        {
            throw new Exception("ERROR: Null xpath in property lookup call.");
        }

        // Method attributes -
        String property_string = null;

        try {
            Node propNode = (Node) _xpath.evaluate(xpath_string, _document_transformation_property_tree, XPathConstants.NODE);
            if (propNode != null){
                property_string = propNode.getNodeValue();
            }
            else {

                return null;
            }
        }
        catch (Exception error)
        {
            error.printStackTrace();
            System.out.println("ERROR: Property lookup failed. The following XPath "+xpath_string+" resulted in an error - "+error.toString());
        }

        return property_string;
    }
}
