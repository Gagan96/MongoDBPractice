package Models;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Country {

    private String carcode;
    private String country_name;
    private ArrayList<City> cities;

    public Country(){}
    public Country(String carcode, String country_name) {
        this.carcode = carcode;
        this.country_name = country_name;
    }

    public Country(String carcode, String country_name, ArrayList<City> cities) {
        this.carcode = carcode;
        this.country_name = country_name;
        this.cities = cities;
    }

    public String getCarcode() {
        return carcode;
    }

    public void setCarcode(String carcode) {
        this.carcode = carcode;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    public void inserCountry(String carcode, String country_name, ArrayList<City> cities){

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("world");
        MongoCollection<Document> collectionCities = database.getCollection("countries");
        List<Document> documents = new ArrayList<Document>();
        Document document = new Document("carcode", carcode).append("country_name", country_name).append("cities", cities);
        /*if (database.getCollection("cities").find().equals(document.get("carcode"))){
            //comprobar carcode y nombre ciudad
        }*/
        collectionCities.insertOne(document);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        if (carcode != null ? !carcode.equals(country.carcode) : country.carcode != null) return false;
        if (country_name != null ? !country_name.equals(country.country_name) : country.country_name != null)
            return false;
        return cities != null ? cities.equals(country.cities) : country.cities == null;
    }

    @Override
    public int hashCode() {
        int result = carcode != null ? carcode.hashCode() : 0;
        result = 31 * result + (country_name != null ? country_name.hashCode() : 0);
        result = 31 * result + (cities != null ? cities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Country{" +
                "carcode='" + carcode + '\'' +
                ", country_name='" + country_name + '\'' +
                ", cities=" + cities +
                '}';
    }
}
