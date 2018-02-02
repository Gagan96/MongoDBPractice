package Models;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
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
            for (Document doc : findCountryByName(city.getCityName())) {
                if (doc.get("cityName").equals(document.get("cityName"))
                        && doc.get("countryName").equals(document.get("countryName"))
                        && doc.get("province").equals(document.get("province")))
                {
                    throw new Exception("This city already exists");
                }
            }

            cityCollection.insertOne(document);

        }catch (Exception e){
            System.out.println("Ya existe este city");
        }

    }

    public static void deleteCityByName(City city, Country country){

        try {
            DeleteResult deleteResult = null;
            FindIterable<Document> cityName = findSpecifiedCity(city,country);
            Document document = new Document("cityName", city.getCityName()).append("latitude", city.getLatitude()).append("longitude", city.getLongitude()).append("country", country.getCarCode());
            for (Document doc : cityName) {
                if (doc.get("cityName").equals(document.get("cityName"))
                        && doc.get("country").equals(document.get("country"))
                        && doc.get("province").equals(document.get("province")))
                {
                    deleteResult = cityCollection.deleteOne(document);
                }
            }
            if (deleteResult.getDeletedCount()==0 || deleteResult == null) throw new Exception();
        } catch (Exception e) {
            System.out.println("No existe este city"+city.getCityName());
        }

    }

    //crear un haystack para encontrar ciudades cerca de una ciudad por longitude o latitude

    public static void updateCityName(City currCityName, City newCityName){


        Document tempDoc = new Document();
        tempDoc.put("cityName", newCityName.getCityName());
        Document tempUpdateOp = new Document("$set", tempDoc);
        cityCollection.updateOne(findCityByName(currCityName.getCityName()).first(), tempUpdateOp);
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

    public static FindIterable<Document> findSpecifiedCity(City city, Country country) {
        Document regQuery = new Document();
        regQuery.append("$regex", "^(?)" + Pattern.quote(city.getCityName()));
        regQuery.append("$options", "i");

        Document findQuery = new Document();
        findQuery.append("cityName", regQuery).append("country",country.getCarCode());

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

    public static Document findCountryByCode(String code) {
        Document regQuery = new Document();
        regQuery.append("$regex", "^(?)" + Pattern.quote(code));
        regQuery.append("$options", "i");

        Document findQuery = new Document();
        findQuery.append("_id", regQuery);
        FindIterable<Document> result = countryCollection.find(findQuery);

        return result.first();
    }



    public static void insertCountry(Country country){

        try {
            Document document = new Document("_id", country.getCarCode()).append("countryName", country.getCountryName());
            countryCollection.insertOne(document);
        } catch (Exception e){
            System.out.println("Ya existe este pais");
        }

    }

    public static void deleteCountry(String code){

        Document countryToDel = findCountryByCode(code);

        try {
            countryCollection.deleteOne(countryToDel);
            cityCollection.deleteMany(Filters.eq("country",countryToDel.get("_id")));
        } catch (Exception e){
            System.out.println("Not deleted");
        }

    }

    public static void updateCountryName(String countryName, String newCountryName){

        Document tempDoc = new Document();
        tempDoc.put("countryName", newCountryName);
        Document tempUpdateOp = new Document("$set", tempDoc);
        countryCollection.updateOne(Filters.eq("countryName", countryName), tempUpdateOp);
    }

}
