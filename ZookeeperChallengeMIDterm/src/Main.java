import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class Main
{
    private String name;
    private int age;
    private String species;
    public Main(String name, int age, String species)
    {
        this.name = name;
        setAge(age); // Validate and set age
        setSpecies(species); // Validate and set species
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        if (age >= 0) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
    }

    public String getSpecies()
    {
        return species;
    }

    public void setSpecies(String species)
    {
        if (species != null && !species.isEmpty())
        {
            this.species = species;
        } else {
            throw new IllegalArgumentException("Species cannot be null or empty.");
        }
    }
}

public class ZookeeperChallengeMIDterm
{
    public static <Animal> void main(String[] args)
    {
        List<Animal> animals = new ArrayList<>();
        Map<String, Integer> speciesCount = new HashMap<>();
        Map<String, List<String>> animalNames = new HashMap<>();

        // Read animal names from animalNames.txt and populate animalNames HashMap
        readAnimalNames("animalNames.txt", animalNames);

        // Read animal details from arrivingAnimals.txt and create Animal instances
        // based on species, update speciesCount HashMap
        readAnimalDetails("arrivingAnimals.txt", animals, speciesCount);

        // Generate report and write to newAnimals.txt
        generateReport("newAnimals.txt", animals, speciesCount);
    }

    private static void readAnimalNames(String filename, Map<String, List<String>> animalNames) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            String currentSpecies = "";
            while ((line = reader.readLine()) != null) {
                if (line.contains("Names:")) {
                    currentSpecies = line.split(" ")[0];
                    animalNames.put(currentSpecies, new ArrayList<>());
                } else {
                    String[] names = line.split(", ");
                    for (String name : names) {
                        animalNames.get(currentSpecies).add(name);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readAnimalDetails(String filename, List<Animal> animals, Map<String, Integer> speciesCount) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int age = Integer.parseInt(parts[0]);
                String species = parts[5].toLowerCase(); // Normalize species name
                Animal animal = new Animal(parts[6], age, species);
                animals.add(animal);
                speciesCount.put(species, speciesCount.getOrDefault(species, 0) + 1);
            }
        } catch (IOException | NumberFormatException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private static void generateReport(String filename, List<Animal> animals, Map<String, Integer> speciesCount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String species : speciesCount.keySet()) {
                writer.write("Species: " + species + ", Count: " + speciesCount.get(species) + "\n");
                for (Animal animal : animals) {
                    if (animal.getSpecies().equalsIgnoreCase(species)) {
                        writer.write("Name: " + animal.getName() + ", Age: " + animal.getAge() + "\n");
                    }
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
