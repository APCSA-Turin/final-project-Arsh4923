package com.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App { // the user is going to enter the names of 2 Pokémon they would like to compare to know which one is better for which part of the game
    // this is done by calling the information from the API and then using a get method to get the chain an finally it compares them and
    // I learnt how to handle exception and tries to access my data from https://www.w3schools.com/java/java_try_catch.asp
    // and I these videos also helped with the creation of my program https://www.youtube.com/watch?reload=9&v=V_cMYN3jAjg ,
    // https://www.youtube.com/watch?v=dVtnFH4m_fE,https://pokemondb.net/, and also used these to help understand JSON objects and arrays
    // https://www.w3schools.com/js/js_json_intro.asp and https://www.w3schools.com/js/js_json_arrays.asp

  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
        System.out.println(" Project Pokémon");
        System.out.println("Choose an option:");
        System.out.println("1. View single Pokémon stats");
        System.out.println("2. Compare two Pokémon");
        System.out.println("3. Guess That Pokémon");
        System.out.print("Enter your choice (1-3): ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        if (choice == 1) {
            Game.displaySinglePokemonStats(scanner);
        } else if (choice == 2) {
            compareTwoPokemon(scanner);
        } else if (choice == 3) {
            Game.guessThatPokemon(scanner);
        } else {
            System.out.println("Invalid choice!");
        }
    } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
    }
}
    
    /* This method handles the comparison of two Pokémon */
    public  static void compareTwoPokemon(Scanner scanner) throws Exception {
        System.out.println("\nPokémon Evolution Comparison Tool");
        System.out.println("Enter first Pokémon: ");
        String pokemon1 = scanner.nextLine().toLowerCase();
        System.out.println("Enter second Pokémon: ");
        String pokemon2 = scanner.nextLine().toLowerCase();

        List<String> evo1 = getEvolutionChain(pokemon1);
        List<String> evo2 = getEvolutionChain(pokemon2);

        compareEvolutions(evo1, evo2);
    }
    
    // This method is used to as a getter method to access the evolution chain so they could be compared we do this by storing the information a list
    public  static List<String> getEvolutionChain(String name) throws Exception {
        List<String> chain = new ArrayList<>();
        JSONObject pokemon = new JSONObject(API.getData("https://pokeapi.co/api/v2/pokemon/" + name));
        String speciesUrl = pokemon.getJSONObject("species").getString("url");
        JSONObject species = new JSONObject(API.getData(speciesUrl));
        String chainUrl = species.getJSONObject("evolution_chain").getString("url");
        JSONObject chainData = new JSONObject(API.getData(chainUrl));

        traverseChain(chainData.getJSONObject("chain"), chain);
        return chain;
    }
    
    // this method goes through the chain of each pokemon to find what it evolves into
    public static void traverseChain(JSONObject stage, List<String> chain) {
        chain.add(stage.getJSONObject("species").getString("name"));
        JSONArray nextStages = stage.getJSONArray("evolves_to");
        for (int i = 0; i < nextStages.length(); i++) {
            traverseChain(nextStages.getJSONObject(i), chain);
        }
    }
    
    // This method is doing the comparing after all the information is gotten and traversable and it gets the first evolution first
    // and sees what pokemon average stats is
    // better meaning they would be better for that part of the game
    // and this comparing is done for all the forms of that pokemon and also for Mega evolution
    public  static void compareEvolutions(List<String> evo1, List<String> evo2) throws Exception {
        System.out.println("\n=== Comparison Results ===");

        if (!evo1.isEmpty() && !evo2.isEmpty()) {
            System.out.println("\n=== EARLY GAME ===");
            JSONObject p1 = getPokemonData(evo1.get(0));
            JSONObject p2 = getPokemonData(evo2.get(0));

            System.out.println("\n" + evo1.get(0).toUpperCase() + " STATS:");
            printStats(p1);

            System.out.println("\n" + evo2.get(0).toUpperCase() + " STATS:");
            printStats(p2);

            double avg1 = getAverageStats(p1);
            double avg2 = getAverageStats(p2);

            System.out.println("\n" + evo1.get(0) + " average: " + avg1);
            System.out.println(evo2.get(0) + " average: " + avg2);

            if (avg1 > avg2) {
                System.out.println(evo1.get(0) + " is better for early game!");
            } else if (avg2 > avg1) {
                System.out.println(evo2.get(0) + " is better for early game!");
            } else {
                System.out.println("Both are equal for early game!");
            }
        }

        if (evo1.size() > 1 && evo2.size() > 1) {
            System.out.println("\n=== MID GAME ===");
            JSONObject p1 = getPokemonData(evo1.get(1));
            JSONObject p2 = getPokemonData(evo2.get(1));

            System.out.println("\n" + evo1.get(1).toUpperCase() + " STATS:");
            printStats(p1);

            System.out.println("\n" + evo2.get(1).toUpperCase() + " STATS:");
            printStats(p2);

            double avg1 = getAverageStats(p1);
            double avg2 = getAverageStats(p2);

            System.out.println("\n" + evo1.get(1) + " average: " + avg1);
            System.out.println(evo2.get(1) + " average: " + avg2);

            if (avg1 > avg2) {
                System.out.println(evo1.get(1) + " is better for mid game!");
            } else if (avg2 > avg1) {
                System.out.println(evo2.get(1) + " is better for mid game!");
            } else {
                System.out.println("Both are equal for mid game!");
            }
        }

        // This part of the program is used only when the pokemon has a mega evolution and gets the last element in evolution list and sees if it is a mega
        int last1 = evo1.size() - 1;
        int last2 = evo2.size() - 1;
        boolean hasMega1 = evo1.get(last1).contains("-mega");
        boolean hasMega2 = evo2.get(last2).contains("-mega");

        System.out.println("\n=== LATE GAME ===");

        if (hasMega1 && hasMega2) {
            JSONObject mega1 = getPokemonData(evo1.get(last1));
            JSONObject mega2 = getPokemonData(evo2.get(last2));

            System.out.println("\n" + evo1.get(last1).toUpperCase() + " STATS:");
            printStats(mega1);

            System.out.println("\n" + evo2.get(last2).toUpperCase() + " STATS:");
            printStats(mega2);

            compareFinalForms(evo1.get(last1), mega1, evo2.get(last2), mega2, "mega");
        }
        else if (hasMega1) {
            JSONObject mega1 = getPokemonData(evo1.get(last1));
            JSONObject final2 = getPokemonData(evo2.get(last2));

            System.out.println("\n" + evo1.get(last1).toUpperCase() + " STATS:");
            printStats(mega1);

            System.out.println("\n" + evo2.get(last2).toUpperCase() + " STATS:");
            printStats(final2);

            compareFinalForms(evo1.get(last1), mega1, evo2.get(last2), final2, "mega vs final");
        }
        else if (hasMega2) {
            JSONObject final1 = getPokemonData(evo1.get(last1));
            JSONObject mega2 = getPokemonData(evo2.get(last2));

            System.out.println("\n" + evo1.get(last1).toUpperCase() + " STATS:");
            printStats(final1);

            System.out.println("\n" + evo2.get(last2).toUpperCase() + " STATS:");
            printStats(mega2);

            compareFinalForms(evo1.get(last1), final1, evo2.get(last2), mega2, "final vs mega");
        }
        else {
            JSONObject final1 = getPokemonData(evo1.get(last1));
            JSONObject final2 = getPokemonData(evo2.get(last2));

            System.out.println("\n" + evo1.get(last1).toUpperCase() + " STATS:");
            printStats(final1);

            System.out.println("\n" + evo2.get(last2).toUpperCase() + " STATS:");
            printStats(final2);

            compareFinalForms(evo1.get(last1), final1, evo2.get(last2), final2, "final");
        }
    }
    
    // This method is used for the final forms of the pokemon and to see which one is the best at the end of both their chains
    public  static void compareFinalForms(String name1, JSONObject p1, String name2, JSONObject p2, String type) {
        double avg1 = getAverageStats(p1);
        double avg2 = getAverageStats(p2);

        System.out.println("\n" + name1 + " (" + type + ") average: " + avg1);
        System.out.println(name2 + " (" + type + ") average: " + avg2);

        if (avg1 > avg2) {
            System.out.println(name1 + " is better for late game!");
        } else if (avg2 > avg1) {
            System.out.println(name2 + " is better for late game!");
        } else {
            System.out.println("Both are equal for late game!");
        }
    }
    
    // This is where the data of the pokemon is accessed and used for the program
    public static JSONObject getPokemonData(String name) throws Exception {
        return new JSONObject(API.getData("https://pokeapi.co/api/v2/pokemon/" + name));
    }
    
    // This method is used to print the information of the pokemon during each evolution/stage
    public static void printStats(JSONObject pokemon) {
        JSONArray stats = pokemon.getJSONArray("stats");
        for (int i = 0; i < stats.length(); i++) {
            JSONObject stat = stats.getJSONObject(i);
            String statName = stat.getJSONObject("stat").getString("name");
            int baseStat = stat.getInt("base_stat");
            System.out.println(statName + ": " + baseStat);
        }
        System.out.println("TOTAL: " + getTotalStats(pokemon));
        System.out.println("AVERAGE: " + getAverageStats(pokemon));
    }
    
    // This method is used to get the stats of the pokemon
    private static int getTotalStats(JSONObject pokemon) {
        JSONArray stats = pokemon.getJSONArray("stats");
        int total = 0;
        for (int i = 0; i < stats.length(); i++) {
            total += stats.getJSONObject(i).getInt("base_stat");
        }
        return total;
    }
    
    // This method is important since it finds which pokemon has the higher average base stats which compares them using this.
   
    {
        return Math.round((double) getTotalStats(pokemon) / pokemon.getJSONArray("stats").length());
    }
}