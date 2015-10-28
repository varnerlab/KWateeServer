Introduction
-------------

Kwatee is an open source mathematical model code generation system for the [Julia programming language](http://julialang.org).  Kwatee is written in Java, and uses a plugin architetcure to generate Julia code from simple model specifications. KwateeServer (and all the plugins developed by [Varnerlab](http://www.varnerlab.org)) are open source and available under an [MIT license](https://opensource.org/licenses/MIT). 

__How does Kwatee work?__ Model input specification files are transformed into model code using specific Kwatee plugins. Kwatee server is responsible for loading the plugins and executing the code generation job given a Configuration.xml file. [Varnerlab](http://www.varnerlab.org) has developed several plugins already, however if you want to generate a model type that we do not support, no problem you can write your own plugin!

__Reference__: 

[![DOI](https://zenodo.org/badge/18914/varnerlab/KwateeServer.svg)](https://zenodo.org/badge/latestdoi/18914/varnerlab/KwateeServer)

[Jeffrey D. Varner (2015) Kwatee: A Code Generation System for Biochemical Models in the Julia Programming Language http://dx.doi.org/10.5281/zenodo.32628](http://dx.doi.org/10.5281/zenodo.32628)

Installation on Linux or Mac OS X
--------------
There are a few requirements to install before you can use Kwatee.

* You'll need a Java Virual Machine (VM) installed on your computer (version 7 or above). You can download a Java VM from [Oracle] (https://java.com/en/download/) for every platform.
* Varnerlab plugins require libSBML to parse Systems Biology Markup Language (SBML) files. You can download libSBML from [SBML.org](http://sbml.org/Software/libSBML). Make sure to get the Java plugin for the library. If you are unfamilar with SBML, you can started with:

	[Bornstein, B. J., Keating, S. M., Jouraku, A., and Hucka M. (2008) LibSBML: An API Library for SBML. Bioinformatics, 24(6):880–881, doi:10.1093/bioinformatics/btn051] (http://www.ncbi.nlm.nih.gov/pmc/articles/PMC2517632/)

* To execute the generated code, you will need [Julia](http://julialang.org) (and potentially some Julia packages depending upon the model type) installed.

##### Installation:
There are a few *simple* steps to install Kwatee on your system, assuming you already have Java, Julia and libSBML installed.

1. Create a top level directory on your filesystem where your server/plugin jars will go, call it anything you want e.g., KwateeServer.
2. Under the top level directory (e.g., KwateeServer), create two subdirectories, "dist" and "plugins".  
3. Download the Kwatee server jar file (or compile the source code using the included [Gradle](http://gradle.org) build script). Put the Kwatee server jar file in the dist subdirectory e.g., /KwateeServer/dist/[jar file name].jar
4. Download the Generate.sh file and place it in the top level server directory e.g., KwateeServer. You may need to make Generate.sh executable, if so issue the command:

	~~~
	chmod +x ./Generate.sh
	~~~
	
5. Place the libSBML.jar file (from your libSBML installation) into the "plugins" subdirectory,
6. Place any [plugins].jar files in the "plugins" directory.

##### Testing:

To test your Kwatee installation, we have included example model specifications and Configuration.xml files in the examples subdirectory of this project. To execute these examples, navigate to the desired example and follow the instructions in the README.
	
##### What Kwatee plugins are currently avaialbe and where do I get them?

[Varnerlab](http://www.varnerlab.org) has developed several Kwatee plugins that we use in our research at Purdue University. Each of these plugins is open source and available under an [MIT license](https://opensource.org/licenses/MIT):

* [Kwatee Cell Free Model Plugin](https://github.com/varnerlab/Kwatee-CellFreeModel-Plugin): The cell free plugin generates Julia simulation code for cell free metabolic networks. The cell free model uses the hybrid modeling framework of Wayman et al:

	[Wayman J, Sagar A and J. Varner (2015) Dynamic Modeling of Cell Free Biochemical Networks using Effective Kinetic Models. Processes DOI:10.3390/pr3010138](http://www.mdpi.com/2227-9717/3/1/138)
	
	__Requirements:__ libSBML and the SUNDIALS package in Julia.

* [Kwatee GRN/Signal Transduction Model Plugin](https://github.com/varnerlab/Kwatee-GRN-Plugin): The gene regulatory network (GRN)/signal transduction plugin generates Julia simulation code for dynamic signal transduction/gene expression models. 

	__Requirements:__ The SUNDIALS package in Julia.


How do I execute a Kwatee code generation job?
-----------

You execute a code generation job by calling the Generate.sh shell script (included in this repository) in the root directory of KwateeServer. Generate.sh takes a single argument, the path to your model generation configuration file (e.g., Configuration.xml)

On Mac OS X or Linux:

~~~
./Generate.sh [path to your Configuration.xml file]
~~~

You also call Kwatee directly from the command line as (from the top level server directory):

~~~
java -Xmx2000M -Djava.library.path=/usr/local/lib -classpath ./dist/kwatee-1.0-SNAPSHOT.jar \
org.varnerlab.kwatee.foundation.VLCGGenerator [path to your Configuration.xml file]
~~~

We have not yet tested Kwatee on Microsoft Windows. Stay tuned!

