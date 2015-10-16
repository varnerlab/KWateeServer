package org.varnerlab.kwatee.foundation;

// imports -
import java.util.Hashtable;
import java.util.ArrayList;
import java.io.File;

/**
 * Copyright (c) 2015 Varnerlab,
 * School of Chemical Engineering,
 * Purdue University, West Lafayette IN 47907 USA.
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


public class VLCGTranslator implements VLCGExecutionHandler, VLCGConfigurable {

    // instance variables -
    private Hashtable<String,String> _properties_table = new Hashtable<String,String>();

    @Override
    public void doExecute(Object object) throws Exception {

        if (object == null){
            throw new Exception("ERROR: The translator is missing transformation configuration tree. Check your configuration file path.");
        }

        // Cast the tree, and let's go ...
        VLCGTransformationPropertyTree transformation_property_tree = (VLCGTransformationPropertyTree)object;

        // Ok, so when I get here I have to create the dirs in case they are not already there -
        ArrayList<String> arrList = new ArrayList();
        arrList.add(transformation_property_tree.lookupPropertyValueFromTreeUsingXPath(".//path[@symbol='KWATEE_SOURCE_OUTPUT_PATH']/@path_location"));
        arrList.add(transformation_property_tree.lookupPropertyValueFromTreeUsingXPath(".//path[@symbol='KWATEE_NETWORK_OUTPUT_PATH']/@path_location"));
        arrList.add(transformation_property_tree.lookupPropertyValueFromTreeUsingXPath(".//path[@symbol='KWATEE_DEBUG_OUTPUT_PATH']/@path_location"));
        int NUM_OF_DIRS = arrList.size();
        for (int dir_index = 0;dir_index<NUM_OF_DIRS;dir_index++)
        {
            // Get the dirname -
            String strDirName = arrList.get(dir_index);
            if (strDirName!=null && !strDirName.isEmpty())
            {
                // Ok, so we are ready to create custom network and src directories -

                // src dir -
                File srcFile = new File(strDirName);
                srcFile.mkdir();
            }
        }

        // Get the input and output handler class names -
        String strClassNameInput = transformation_property_tree.lookupPropertyValueFromTreeUsingXPath(".//Handler/InputHandler/@input_classname");
        String strClassNameOutput = transformation_property_tree.lookupPropertyValueFromTreeUsingXPath(".//Handler/OutputHandler/@output_classname");

        // Ok, so we need to get the package for the input and output handlers -
        String strPackageSymbolInputHandler = transformation_property_tree.lookupPropertyValueFromTreeUsingXPath(".//Handler/InputHandler[@input_classname='"+strClassNameInput+"']/@package");
        String strPackageSymbolOutputHandler = transformation_property_tree.lookupPropertyValueFromTreeUsingXPath(".//Handler/OutputHandler[@output_classname='"+strClassNameOutput+"']/@package");

        // Look up the package name -
        String strPackageNameInputPackage = transformation_property_tree.lookupPropertyValueFromTreeUsingXPath(".//package[@symbol='"+strPackageSymbolInputHandler+"']/@package_name");
        String strPackageNameOutputPackage = transformation_property_tree.lookupPropertyValueFromTreeUsingXPath(".//package[@symbol='"+strPackageSymbolOutputHandler+"']/@package_name");

        // Formulate the fully qualified java name -
        String strFQClassNameInputPackage = strPackageNameInputPackage+"."+strClassNameInput;
        String strFQClassNameOutputPackage = strPackageNameOutputPackage+"."+strClassNameOutput;

        // Create an instance of the factory and build the handlers -
        VLCGHandlerFactory factory = VLCGHandlerFactory.getInstance();
        VLCGInputHandler inputHandler = (VLCGInputHandler)factory.buildObject(strFQClassNameInputPackage);
        VLCGOutputHandler outputHandler = (VLCGOutputHandler)factory.buildObject(strFQClassNameOutputPackage);

        // Set property tree on the handlers -
        inputHandler.setPropertiesTree(transformation_property_tree);
        outputHandler.setPropertiesTree(transformation_property_tree);

        // load the input files -
        inputHandler.loadResource(null);

        // Ok, when I get here I've loaded the model source. I need to create
        // and instance of the output handler and then execute the write command'
        // Before I call the output handler, I need to get the stuff from the resource
        // and put it into our wrappers -
        Object model = inputHandler.getResource(null);

        // Ok, so we have the ModelContainer - dump it to the output handler
        outputHandler.writeResource(model);
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
}
