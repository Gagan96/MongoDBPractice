package Models;

import java.util.ArrayList;

public class MainMONGO {

    public static void main(String[] args) {
        Country India = new Country("blabla","blablabla");
        City city = new City("ambala",India,5,10);
        Country country = new Country();
        ArrayList<City> cities = new ArrayList<>();
        cities.add(city);
        country.inserCountry("test","test",cities);
    }
}
