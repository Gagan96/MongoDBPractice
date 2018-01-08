package XPath;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class MyReader extends DomReader {

    public MyReader(String xml) throws ParserConfigurationException, SAXException, IOException {
        super(xml);
    }

    public List<String> listCountry() {
        return super.extractList("/world/country/name/text()");
    }

    public String getCodigo(String country){
        return super.extractValue("/world/country[name='"+country+"']/@car_code");
    }

    public String getLatitud(String city, String country){
        return super.extractValue("/world/country[name='" + country + "']//city[name='"+scapeQuote(city)+"']/latitude/text()");
    }

    public String getLongitud(String city, String country){
        return super.extractValue("/world/country[name='" + country + "']//city[name='"+scapeQuote(city)+"']/longitude/text()");
    }


    public List<String> listCity(String country) {
        return super.extractList("/world/country[name='" + country + "']//city/name[1]/text()");
    }

    public List<String> latitud(String country) {
        return super.extractList("/world/country[name='" + country + "']//city/latitude/text()");
    }
    public List<String> longitud(String country) {
        return super.extractList("/world/country[name='" + country + "']//city/longitude/text()");
    }

    public static String scapeQuote(String s){


        return s.replace("'","&apos;");
    }

}
