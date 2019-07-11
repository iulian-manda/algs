package cracking.the.coding.interview.java;

class Country {
    private String continent;
    private int population;

    Country(String continent, int population) {
        this.continent = continent;
        this.population = population;
    }

    int getPopulation() {
        return population;
    }

    String getContinent() {
        return continent;
    }
}
