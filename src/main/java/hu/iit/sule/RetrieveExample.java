package hu.iit.sule;

import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.xmldb.api.*;
import javax.xml.transform.OutputKeys;
import org.exist.xmldb.EXistResource;
public class RetrieveExample {

    /**
     * In the following simple example, a document is retrieved from the eXist server and printed to standard output.
     */

    private static String URI = "xmldb:exist://localhost:8080/exist/xmlrpc";
    private static String collection ="/db/videos/";
    private static String resource = "videos.xml";
    /**
     * args[0] Should be the name of the collection to access
     * args[1] Should be the name of the resource to read from the collection
     */
    public static void main(String args[]) throws Exception {

        final String driver = "org.exist.xmldb.DatabaseImpl";


        // initialize database driver
        Class cl = Class.forName(driver);
        Database database = (Database) cl.newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);

        Collection col = null;
        XMLResource res = null;
        try {
            // get the collection
            col = DatabaseManager.getCollection(URI + collection);
            col.setProperty(OutputKeys.INDENT, "no");
            res = (XMLResource)col.getResource(resource);

            if(res == null) {
                System.out.println("document not found!");
            } else {
                System.out.println(res.getContent());
            }
        } finally {
            //dont forget to clean up!

            if(res != null) {
                try { ((EXistResource)res).freeResources(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }

            if(col != null) {
                try { col.close(); } catch(XMLDBException xe) {xe.printStackTrace();}
            }
        }
    }
}