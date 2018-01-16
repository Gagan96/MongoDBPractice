package Models;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class City {

    private String city_name;
    private Country country_name;
    private int latitud;
    private int longitud;

    public City(String city_name, Country country_name, int latitud, int longitud) {
        this.city_name = city_name;
        this.country_name = country_name;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public Country getCountry_name() {
        return country_name;
    }

    public void setCountry_name(Country country_name) {
        this.country_name = country_name;
    }

    public int getLatitud() {
        return latitud;
    }

    public void setLatitud(int latitud) {
        this.latitud = latitud;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public void inserCity(String cityName, Country country, int latitud, int longitud){

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("world");
        MongoCollection<Document> collectionCities = database.getCollection("cities");
        List<Document> documents = new ArrayList<Document>();
        Document document = new Document("city_name", cityName).append("country_name", country).append("latitud", latitud).append("longitud", longitud);
        if (database.getCollection("cities").find().equals(document.get("carcode"))){
            //comprobar carcode y nombre ciudad
        }
        collectionCities.insertOne(document);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (latitud != city.latitud) return false;
        if (longitud != city.longitud) return false;
        if (city_name != null ? !city_name.equals(city.city_name) : city.city_name != null) return false;
        return country_name != null ? country_name.equals(city.country_name) : city.country_name == null;
    }

    @Override
    public int hashCode() {
        int result = city_name != null ? city_name.hashCode() : 0;
        result = 31 * result + (country_name != null ? country_name.hashCode() : 0);
        result = 31 * result + latitud;
        result = 31 * result + longitud;
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                "city_name='" + city_name + '\'' +
                ", country_name=" + country_name +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}
