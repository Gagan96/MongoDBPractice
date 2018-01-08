package Models;

public class Country {

    private String country_name;

    public Country(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return country_name != null ? country_name.equals(country.country_name) : country.country_name == null;
    }

    @Override
    public int hashCode() {
        return country_name != null ? country_name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Country{" +
                "country_name='" + country_name + '\'' +
                '}';
    }
}
