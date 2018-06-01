# Paul the Octopus (Java Quick Starter)

A basic project to use for the Paul the Octopus challenge (World Cup 2018) written in Java.

## Getting started

Steps to install and configure the project:

1. Install [JDK 8 SE](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). Check if the installation was successful running:
    ```
    > java -version
    java version 1.8.0_65
    Java(TM) SE Runtime Environment (build 1.8.0_65-b17)
    Java HotSpot(TM) 64-Bit Server VM (build 25.65-b01, mixed mode)
    ```

2. Clone the repository

    ```
    > git clone https://github.com/rcluiz/paul-octopus-java
    ```

3. Install [Gradle 4.7](https://gradle.org/releases/) or later. After that, make sure that the GRADLE_HOME environment variable is properly set and exported.

    ```
    > export GRADLE_HOME=<my_path>/gradle
    > echo $GRADLE_HOME
    <my_path>/gradle
    ```
   
   Test your gradle installation:
   
    ```
    > gradle -version
    ------------------------------------------------------------
    Gradle 4.7
    ------------------------------------------------------------
    
    Build time:   2018-04-18 09:09:12 UTC
    Revision:     b9a962bf70638332300e7f810689cb2febbd4a6c
    
    Groovy:       2.4.12
    Ant:          Apache Ant(TM) version 1.9.9 compiled on February 2 2017
    JVM:          1.8.0_65 (Oracle Corporation 25.65-b01)
    OS:           Mac OS X 10.13.4 x86_64

    ```
          
4. Install the lastest version of the [gcloud tool](https://cloud.google.com/sdk/downloads). Initialize the tool using:
    ```
    > gcloud init
    > gcloud auth application-default login
    (use your @ciandt.com login for the step above)
    > gcloud config set project project-paul-the-octopus  
    ```

    *Important*: gcloud is part of any GCE VM. If you are using it instead of your own environment, you can skip this step. 

5. Test your installation

    ```
    > ./paul.sh
    ```
    
6. Run the prediction

    ```
    > ./paul.sh -c predict -p AveragePredictor -f
    ```
    
7. Upload the file to your bucket using the following command:

   ```
    > ./paul.sh -c upload -u rafaelcl
	```
	
