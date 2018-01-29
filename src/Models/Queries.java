package Models;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import sax.MainSax;


import java.util.regex.Pattern;

public class Queries {
    private static MongoCollection<Document> countryCollection = MainSax.countryCollection;
    private static MongoCollection<Document> cityCollection = MainSax.cityCollection;

    public static void insertCity(City city, Country country){

        if (country.getCarCode().equals(null)) System.out.println("No es possible porque no existe tal pais");
        else {
            Document document = new Document("_id", country.getCarCode() + city.getProvince() + city.getCityName()).append("cityName", city.getCityName()).append("latitude", city.getLatitude()).append("longitude", city.getLongitude()).append("country", country.getCarCode());
            MainSax.cityCollection.insertOne(document);
        }

    }

    //preguntar domreader lunes 29
    public static void deleteCityByName(String city_name){

        cityCollection.deleteOne(Filters.eq("cityName",city_name));
    }

    public static void updateCityName(String city_name, String newCityName){

        Document tempDoc = new Document();
        tempDoc.put("cityName", newCityName);
        Document tempUpdateOp = new Document("$set", tempDoc);
        cityCollection.updateOne(Filters.eq("cityName", city_name), tempUpdateOp);
    }

    public static void findCityByName(String name) {
        Document regQuery = new Document();
        regQuery.append("$regex", "^(?)" + Pattern.quote(name));
        regQuery.append("$options", "i");

        Document findQuery = new Document();
        findQuery.append("cityName", regQuery);

        FindIterable<Document> result = cityCollection.find(findQuery);

        for (Document doc :
                result) {
            System.out.println(doc.toJson());
        }
        //return (List<Document>) result;

    }
    ////////////////////////////////////////////////////////////////////////////


    public static void findCountryByName(String name) {
        Document regQuery = new Document();
        regQuery.append("$regex", "^(?)" + Pattern.quote(name));
        regQuery.append("$options", "i");

        Document findQuery = new Document();
        findQuery.append("countryName", regQuery);

        FindIterable<Document> result = countryCollection.find(findQuery);

        for (Document doc :
                result) {
            System.out.println(doc.toJson());
        }

    }

    public static String findCountryCode(String name) {
        Document regQuery = new Document();
        regQuery.append("$regex", "^(?)" + Pattern.quote(name));
        regQuery.append("$options", "i");

        Document findQuery = new Document();
        findQuery.append("countryName", regQuery);
        FindIterable<Document> result = countryCollection.find(findQuery);

        return result.first().getString("_id");
    }

    public static void insertCountry(Country country){

        try {
            Document document = new Document("_id", country.getCarCode()).append("countryName", country.getCountryName());
            countryCollection.insertOne(document);
        } catch (Exception e){
            System.out.println("Ya existe este pais");
        }

    }

    //preguntar domreader lunes 29
    public static void deleteCountryByName(String country_name){

        countryCollection.deleteOne(Filters.eq("countryName",country_name));
    }

    public static void updateCountryName(String countryName, String newCountryName){

        Document tempDoc = new Document();
        tempDoc.put("countryName", newCountryName);
        Document tempUpdateOp = new Document("$set", tempDoc);
        countryCollection.updateOne(Filters.eq("countryName", countryName), tempUpdateOp);
    }

}
