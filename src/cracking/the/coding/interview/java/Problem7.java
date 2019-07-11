package cracking.the.coding.interview.java;

import java.util.ArrayList;
import java.util.List;

public class Problem7 {

    static int getPopulation(List<Country> countries, String continent) {
        return countries.stream()
                .filter(country -> continent.equals(country.getContinent()))
                .map(Country::getPopulation)
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country("europe", 10000));
        countries.add(new Country("america", 20000));
        countries.add(new Country("europe", 20000));
        System.out.println(getPopulation(countries, "europe"));
    }

}
