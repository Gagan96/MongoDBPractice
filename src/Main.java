
import XPath.MyReader;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        deleteDatabase();

        MongoClient mongoClient = new MongoClient();
        MyReader r;
        r = new MyReader("world.xml");

        List<String> country = r.listCountry();

        try {
            MongoDatabase database = mongoClient.getDatabase("world");
            MongoCollection<Document> collection = database.getCollection("countries");
            MongoCollection<Document> collectionCities = database.getCollection("cities");
            List<Document> documents = new ArrayList<Document>();


            for (String nameCountry : country) {
                collection.insertOne(new Document("country_name", nameCountry));

                List<String> cities = r.listCity(nameCountry);



                for (String cityName : cities) {
                    String latitud = r.getLatitud(cityName, nameCountry);
                    String longitud = r.getLongitud(cityName, nameCountry);


                    documents.add(new Document("city_name", cityName).append("country_name", nameCountry).append("latitud", latitud).append("longitud", longitud));

                }

            }

            collectionCities.insertMany(documents);

        } catch (Exception e) {
            System.out.println("Database unavailable!");
        } finally {
            mongoClient.close();
        }
    }

    public static void deleteDatabase() {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("world");
        database.drop();
        mongoClient.close();
    }

    public static void deleteCity(String cityName){
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("world");



    }

}
