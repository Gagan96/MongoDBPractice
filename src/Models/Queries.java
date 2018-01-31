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

    /*
    Pendent de pulir coses encara
     */
    public static void insertCity(City city, Country country){

        try {
            Document document = new Document("cityName", city.getCityName()).append("latitude", city.getLatitude()).append("longitude", city.getLongitude()).append("country", country.getCarCode());
            cityCollection.insertOne(document);

        }catch (Exception e){
            System.out.println("Ya existe este city");
        }

    }

    public static void deleteCityByName(String city_name){

        try {
            String cityName = findCityByName(city_name).first().get("cityName").toString();
            if (cityName.isEmpty()) throw new Exception();
            cityCollection.deleteOne(new Document("cityName",city_name));
        } catch (Exception e) {
            System.out.println("No existe este city"+city_name);
        }

    }

    //crear un haystack para encontrar ciudades cerca de una ciudad por longitude o latitude

    public static void updateCityName(String city_name, String newCityName){

        Document tempDoc = new Document();
        tempDoc.put("cityName", newCityName);
        Document tempUpdateOp = new Document("$set", tempDoc);
        cityCollection.updateOne(findCityByName(city_name).first(), tempUpdateOp);
    }

    public static FindIterable<Document> findCityByName(String name) {
        Document regQuery = new Document();
        regQuery.append("$regex", "^(?)" + Pattern.quote(name));
        regQuery.append("$options", "i");

        Document findQuery = new Document();
        findQuery.append("cityName", regQuery);

        FindIterable<Document> result = cityCollection.find(findQuery);


        return result;

    }

    public static void print(FindIterable<Document> result){

                for (Document doc :
                result) {
            System.out.println(doc.toJson());
        }
    }


    ////////////////////////////////////////////////////////////////////////////


    public static FindIterable<Document> findCountryByName(String name) {
        Document regQuery = new Document();
        regQuery.append("$regex", "^(?)" + Pattern.quote(name));
        regQuery.append("$options", "i");

        Document findQuery = new Document();
        findQuery.append("countryName", regQuery);

        FindIterable<Document> result = countryCollection.find(findQuery);

        return result;

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
