# Simple Text Editor Server

Requirements

> As an public user, I want to be able manage my text files in the browser. 
Therefore I need an web application, where I can read, create, edit, update and delete my files. 

Data Model

* TextFileEntity
     * id: Long 
     * name: String (Unique)
     * size: Long
     * content: Text
     * creationDate: Date
     * updateDate: Date
     * createdBy: String
     * updatedBy: String
     * version: Long
     

REST API:
* @GET      /texts Return all Text Files (sorted by 'creationDate')
* @POST     /texts Create new Text File
* @PUT      /texts Update existing Text File
* @DELETE   /texts/{id} Delete existing Text File

## Technology Stack

* Java 1.8
    * Streams 
    * Lambdas
* Third Party Libraries
    * Spring Boot (2.0.4.RELEASE)
    * Commons-Lang3 (Apache License)
    * Junit (EPL 1.0 License)
* Build Tool
    * Maven (Apache License)
* Code-Analyses
    * Sonar
    * Jacoco
* VCS 
    * GitHub (Public)
    
## License

MIT (unless noted otherwise)
