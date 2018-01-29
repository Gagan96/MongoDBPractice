package Models;

import java.util.ArrayList;
import java.util.Objects;

public class Country {

    private String carCode;
    private String countryName;
    private ArrayList<City> cities;

    public Country(){}

    public Country(String carCode, String countryName) {
        this.carCode = carCode;
        this.countryName = countryName;
        cities = new ArrayList<>();
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void setCities(ArrayList<City> cities) {
        this.cities = cities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(carCode, country.carCode) &&
                Objects.equals(countryName, country.countryName) &&
                Objects.equals(cities, country.cities);
    }

    @Override
    public int hashCode() {

        return Objects.hash(carCode, countryName, cities);
    }

    @Override
    public String toString() {
        String tmp = "";
        for (int i = 0; i < cities.size(); i++) {
            City city = cities.get(i);
            tmp += "\n\t" + (i + 1) + " : " + city.getCityName() + " (" + city.getProvince() + ") - (lat:" + city.getLatitude() + ", lon:" + city.getLongitude() + ")";
        }

        return "Country{" +
                "carCode='" + carCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", cities=" +
                tmp +
                '}';
    }
}
