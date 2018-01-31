package sax;

import Models.City;
import Models.Country;
import Models.Queries;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;

public class MainSax {
    private static MongoClient mongoClient = new MongoClient();
    private static MongoDatabase database = mongoClient.getDatabase("world");
    public static MongoCollection<Document> countryCollection = database.getCollection("countries");
    public static MongoCollection<Document> cityCollection = database.getCollection("cities");

    public static void connect() {
        deleteDatabase();

//        mongoClient = new MongoClient();
//        database = mongoClient.getDatabase("world");
//        countryCollection = database.getCollection("countries");
//        cityCollection = database.getCollection("cities");


        try {
            fill();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    private static void fill() throws XPathExpressionException {
        List<Document> citiesDocument = new ArrayList<>();

        ArrayList<Country> world = World.getDocument("world.xml");
        for (Country country : world) {
            countryCollection.insertOne(new Document("_id", country.getCarCode())
                    .append("countryName", country.getCountryName()));

            ArrayList<City> cities = country.getCities();
            for (City city : cities) {
                if (city.getProvince().isEmpty()) { // No tiene provincia
                    citiesDocument.add(new Document("_id",country.getCarCode()+'_'+city.getProvince()+'_'+city.getCityName()).append("cityName", city.getCityName())
                            .append("location", new Document("latitude", city.getLatitude())
                                    .append("longitude", city.getLongitude()))
                            .append("country", city.getCityCountry().getCarCode()));
                } else { // Tiene provincia
                    citiesDocument.add(new Document("_id",country.getCarCode()+'_'+city.getProvince()+'_'+city.getCityName()).append("cityName", city.getCityName())
                            .append("province", city.getProvince())
                            .append("location", new Document("latitude", city.getLatitude())
                                    .append("longitude", city.getLongitude()))
                            .append("country", city.getCityCountry().getCarCode()));
                }
            }
        }

        cityCollection.insertMany(citiesDocument);

        // TODO indexes
        IndexOptions uniqueOption = new IndexOptions().unique(true);

        //countryCollection.createIndex()

    }

    private static void deleteDatabase() {
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("world");
        database.drop();
        mongoClient.close();
    }

    public static void disconnect() {
        mongoClient.close();
    }

    public static void main(String[] args) {
        connect();
        //Queries.findCityByName("Barcelona");

        Country country = new Country("test","test");

        City city= new City("test","test",0.0,0.0,country);

        Queries.insertCountry(country);
        Queries.insertCity(city,country);
        Queries.insertCity(city,country); // no da error porque el id es automatico
        Queries.print(Queries.findCityByName("test"));
        Queries.updateCityName("test","test1");
        Queries.print(Queries.findCityByName("test1"));
        Queries.deleteCityByName("test1");
        Queries.print(Queries.findCityByName("test1"));

        System.out.println();

        Queries.insertCountry(country);
        Queries.print(Queries.findCountryByName("test"));
        Queries.updateCountryName("test","test1");
        Queries.print(Queries.findCountryByName("test1"));
        Queries.deleteCountryByName("test1");
        Queries.print(Queries.findCountryByName("test1"));

        disconnect();
    }
}
