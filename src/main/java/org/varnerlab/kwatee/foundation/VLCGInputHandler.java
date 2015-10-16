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
 *
 * Created by jeffreyvarner on 10/8/15.
 */
public interface VLCGInputHandler {


    /**
     * Used by Kwatee to pass required properties to the InputHandler.
     * When constructing a model there are many properties
     * that are required; these properties may change from one instance to another.
     * For example, the input file path is obviously required but is also variable.
     * Universal is written to store and communicate these properties in an
     * XMLPropTree object.  The XMLPropTree object is then made accessible
     * to the InputHandler via the setProperties Method.
     *
     * When creating a custom plugin, write the setProperties method
     * to properly make use of the "instance specific" information in the
     * XMLPropTree object.
     *
     *
     * For more see the javadoc associated with XMLPropTree class.
     * @param properties_tree an VLCGTransformationPropertyTree object containing various properties
     */
    void setPropertiesTree(VLCGTransformationPropertyTree properties_tree);

    /**
     * Reads a user defined resource from someplace
     * @param object    Currently not implemented by Universal, expect null object.
     * @throws Exception    Input exceptions related to opening and reading files.
     */
    void loadResource(Object object) throws Exception;

    /**
     * Generates an SBML model object from the input data structure.
     * @param object  Currently not implemented by Universal, expect null object.
     * @return SBML model object (default)
     * @throws Exception
     */
    Object getResource(Object object) throws Exception;
}
