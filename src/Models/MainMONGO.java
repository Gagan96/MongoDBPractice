package Models;

import XPath.MyReader;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class MainMONGO {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        //deleteDatabase();

//        Country India = new Country("blabla","blablabla");
//        City city = new City("ambala",India,5,10);
//        Country country = new Country();
//        ArrayList<City> cities = new ArrayList<>();
//        cities.add(city);
//        country.insertCountry(country);
//        country.updateCountryName(country,"hola");
//        city.insertCity(city,country);
        //country.deleteCountryByName("test","test");

        MyReader reader = new MyReader("world.xml");
        for (int i=0;i<reader.countriesList().size();i++) {
            System.out.println(reader.countriesList().get(i));
        }
        System.out.println(reader.countriesList().size());
    }

}
