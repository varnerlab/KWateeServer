package org.varnerlab.kwatee.foundation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

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
 * Created by jeffreyvarner on 10/10/15.
 */

public class VLCGCopyrightFactory {

    // instance variables -
    private Date current_date = new Date();
    private SimpleDateFormat date_formatter = new SimpleDateFormat("yyyy");
    private String _path_to_copyright_file = "/copyright.properties";
    private Properties _copyright_properties = new Properties();
    private static VLCGCopyrightFactory _shared_instance = null;

    // return shared instance -
    public static VLCGCopyrightFactory getSharedInstance(){

        if (_shared_instance == null){
            _shared_instance = new VLCGCopyrightFactory();
            return _shared_instance;
        }
        return _shared_instance;
    }

    private VLCGCopyrightFactory() {

        // init me -
        initialize();
    }

    private void initialize() {

        // header buffer -
        try {

            // load the mapping properties file -
            _copyright_properties.load(VLCGCopyrightFactory.class.getResourceAsStream(_path_to_copyright_file));
        }
        catch (IOException error){
            error.printStackTrace();
        }
    }

    public String getJuliaCopyrightHeader() {

        // Method attributes -
        StringBuffer buffer = new StringBuffer();

        // Build the copyright notice -
        buffer.append("# ----------------------------------------------------------------------------------- #\n");
        buffer.append("# Copyright (c) ");
        buffer.append(date_formatter.format(current_date));
        buffer.append(" ");
        buffer.append(_copyright_properties.getProperty("kwatee.copyright.holder"));
        buffer.append("\n");
        buffer.append("# ");
        buffer.append(_copyright_properties.getProperty("kwatee.copyright.address"));
        buffer.append("\n");
        buffer.append("# ");
        buffer.append(_copyright_properties.getProperty("kwatee.copyright.city"));
        buffer.append(" ");
        buffer.append(_copyright_properties.getProperty("kwatee.copyright.state"));
        buffer.append(" ");
        buffer.append(_copyright_properties.getProperty("kwatee.copyright.zip"));
        buffer.append(" ");
        buffer.append(_copyright_properties.getProperty("kwatee.copyright.country"));
        buffer.append("\n");
        buffer.append("\n");
        buffer.append("# Permission is hereby granted, free of charge, to any person obtaining a copy\n");
        buffer.append("# of this software and associated documentation files (the \"Software\"), to deal\n");
        buffer.append("# in the Software without restriction, including without limitation the rights \n");
        buffer.append("# to use, copy, modify, merge, publish, distribute, sublicense, and/or sell \n");
        buffer.append("# copies of the Software, and to permit persons to whom the Software is \n");
        buffer.append("# furnished to do so, subject to the following conditions:\n");
        buffer.append("#\n");
        buffer.append("# The above copyright notice and this permission notice shall be included in\n");
        buffer.append("# all copies or substantial portions of the Software.\n");
        buffer.append("#\n");
        buffer.append("# THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n");
        buffer.append("# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n");
        buffer.append("# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n");
        buffer.append("# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n");
        buffer.append("# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n");
        buffer.append("# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n");
        buffer.append("# THE SOFTWARE.\n");
        buffer.append("# ----------------------------------------------------------------------------------- #\n");

        // return -
        return buffer.toString();
    }
}
