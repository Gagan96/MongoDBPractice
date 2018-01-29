package sax;

import Models.City;
import Models.Country;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.util.ArrayList;

public class World {
    private static Document doc;

    public static ArrayList<Country> getDocument(String path) throws XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {
            builder = factory.newDocumentBuilder();
            doc = builder.parse(path);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return getWorld();
    }

    private static NodeList getCountries() throws XPathExpressionException {

        // Create XPathFactory object
        XPathFactory xPathFactory = XPathFactory.newInstance();
        // Create XPath object
        XPath xPath = xPathFactory.newXPath();

        XPathExpression expGetCountry = xPath.compile("/world/country");
        NodeList countryNodes = (NodeList) expGetCountry.evaluate(doc, XPathConstants.NODESET);

        return countryNodes;
    }

    private static ArrayList<Country> getWorld() throws XPathExpressionException {
        ArrayList<ArrayList<String>> world = new ArrayList<>();

        // Create XPathFactory object
        XPathFactory xPathFactory = XPathFactory.newInstance();
        // Create XPath object
        XPath xPath = xPathFactory.newXPath();

        XPathExpression expGetCitiesFromCountry = xPath.compile("//city");
        XPathExpression expCountryLang = xPath.compile("//language");
        XPathExpression expLat = xPath.compile("//latitude");
        XPathExpression expLon = xPath.compile("//longitude");
        XPathExpression expProvince = xPath.compile("//province");

        NodeList countryNodes = getCountries();

        final int countryNodesLength = countryNodes.getLength();
        ArrayList<Country> countryList = new ArrayList<>();

        for (int i = 0; i < countryNodesLength; i++) {
            Node ccn = countryNodes.item(i).cloneNode(true); // ccn = Current Country Node

            /* CREAR PAIS */
            Country currentCountry = new Country(ccn.getAttributes().getNamedItem("car_code").getNodeValue(),
                    ccn.getChildNodes().item(1).getTextContent());

//            /* IDIOMAS */
//            NodeList langNodes = (NodeList) expCountryLang.evaluate(ccn, XPathConstants.NODESET);
//            ArrayList<String> languages = new ArrayList<>();
//            for (int j = 0; j < langNodes.getLength(); j++) {
//                languages.add(langNodes.item(j).cloneNode(true).getTextContent());
//                //System.out.println("\t"+langNodes.item(j).cloneNode(true).getTextContent());
//            }
//            currentCountry.setLanguages(languages);

            NodeList provinces = (NodeList) expProvince.evaluate(ccn, XPathConstants.NODESET); // Preparar provincia

            /* CIUDADES */
            NodeList cityNodes = (NodeList) expGetCitiesFromCountry.evaluate(ccn, XPathConstants.NODESET);
            ArrayList<City> cities = new ArrayList<>();
            final int cityNodesLength = cityNodes.getLength();

            for (int j = 0; j < cityNodesLength; j++) {
                Node ccin = cityNodes.item(j).cloneNode(true); // ccin = Current City Node

                String name = "";
                String province = "";
                String provinceId;
                double latitude = 0;
                double longitude = 0;

                /* PROVINCIA CIUDAD */
                if (ccin.getAttributes().getNamedItem("province") != null) {
                    provinceId = ccin.getAttributes().getNamedItem("province").getNodeValue();
                    for (int k = 0; k < provinces.getLength(); k++) {
                        if (provinces.item(k).getAttributes().getNamedItem("id").getNodeValue().equals(provinceId)){
                            province = provinces.item(k).getChildNodes().item(1).getTextContent();
                        }
                    }
                }

                /* CREAR CIUDAD */
                NodeList infoCityList = ccin.getChildNodes();
                for (int k = 0; k < infoCityList.getLength(); k++) {
                    switch (infoCityList.item(k).getNodeName()) {
                        case "name":
                            name = infoCityList.item(k).getTextContent();
                            break;
                        case "latitude":
                            latitude = Double.parseDouble(infoCityList.item(k).getTextContent());
                            break;
                        case "longitude":
                            longitude = Double.parseDouble(infoCityList.item(k).getTextContent());
                    }
                }

                cities.add(new City(name, province, latitude, longitude, currentCountry));
            }

            /* GUARDAR */
            currentCountry.setCities(cities);
            countryList.add(currentCountry);


        }


        return countryList;
    }
}
