package Models;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;

public class MainMONGO {

    public static void main(String[] args) {

        deleteDatabase();

        Country India = new Country("blabla","blablabla");
        City city = new City("ambala",India,5,10);
        Country country = new Country();
        ArrayList<City> cities = new ArrayList<>();
        cities.add(city);
        country.insertCountry(country);
        country.updateCountryName(country,"hola");
        city.insertCity(city,country);
        //country.deleteCountryByName("test","test");

    }

    public static void deleteDatabase() {

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("world");
        database.drop();
        mongoClient.close();
    }
}
