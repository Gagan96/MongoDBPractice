package Models;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.Objects;


public class City {

    private String cityName;
    private String province;
    private double latitude;
    private double longitude;
    private Country cityCountry;


    public City(String cityName, String province, double latitude, double longitude, Country cityCountry) {
        this.cityName = cityName;
        this.province = province;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityCountry = cityCountry;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvince() {
        if(province==null) return "";
        else return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Country getCityCountry() {
        return cityCountry;
    }

    public void setCityCountry(Country cityCountry) {
        this.cityCountry = cityCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Double.compare(city.latitude, latitude) == 0 &&
                Double.compare(city.longitude, longitude) == 0 &&
                Objects.equals(cityName, city.cityName) &&
                Objects.equals(province, city.province) &&
                Objects.equals(cityCountry, city.cityCountry);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cityName, province, latitude, longitude, cityCountry);
    }

    @Override
    public String toString() {
        return "City{" +
                "cityName='" + cityName + '\'' +
                ", province='" + province + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", cityCountry=" + cityCountry.getCountryName() +
                '}';
    }
}
