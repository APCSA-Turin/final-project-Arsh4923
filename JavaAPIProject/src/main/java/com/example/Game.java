package com.example;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Game {
    private static final Random random = new Random();
    
// This method is going to display the stats of one pokemon given the user entered choice 1 
    public static void displaySinglePokemonStats(Scanner scanner) throws Exception {
        System.out.println("\n=== Single Pokémon Stats ===");
        System.out.println("Enter Pokémon name: ");
        String pokemonName = scanner.nextLine().toLowerCase();
        
        JSONObject pokemon = getPokemonData(pokemonName);
        System.out.println("\n" + pokemonName.toUpperCase() + " STATS:");
        printStats(pokemon);
    }
   // This is code for the guess pokemon feature where given the user is going 
    public static void guessThatPokemon(Scanner scanner) throws Exception {
        System.out.println("\n=== Guess That Pokémon ===");
        // Get a random Pokémon ID (1-898 for main series Pokémon)
        int randomId = random.nextInt(898) + 1;
        JSONObject pokemon = getPokemonData(String.valueOf(randomId));
        
        String name = pokemon.getString("name");
        JSONArray types = pokemon.getJSONArray("types");
        JSONObject species = new JSONObject(API.getData(pokemon.getJSONObject("species").getString("url")));
       String generationUrl = species.getJSONObject("generation").getString("url");
      int generation = getGenerationFromUrl(generationUrl);
        
       // This is just going to get the chain data and set attemots to 4 and intail state of guessed to false and after it get all that information 
       // needed the user is going to guess and as the get it wrong it will give clues.
        String chainUrl = species.getJSONObject("evolution_chain").getString("url");
        JSONObject chainData = new JSONObject(API.getData(chainUrl));
        List<String> evolutions = new ArrayList<>();
        traverseChain(chainData.getJSONObject("chain"), evolutions);
        
        int attempts = 4;
        boolean guessed = false;
        
        System.out.println("\nGuess the Pokémon based on these clues!");
        System.out.println("You have " + attempts + " attempts.");
        
       // This is the first clue where it just gives the stat and it is a bit hard to get it outright

        System.out.println("\nClue 1: Base Stats");
        printStats(pokemon);
        

        // This is the while loop where attempts, which is 4 is minused by one after every guess and 
        while (attempts > 0 && !guessed) {
            System.out.println("\nYour guess: ");
            String guess = scanner.nextLine().toLowerCase();
              if (guess.equals(name)) {
               System.out.println("Correct! It's " + name + "!");
                guessed = true;
            } else {
              attempts--;
             if (attempts > 0) {
             System.out.println("Incorrect! Attempts left: " + attempts);
              if (attempts == 3) {
              System.out.println("\nClue 2: Type");
               for (int i = 0; i < types.length(); i++) {
                 System.out.println(types.getJSONObject(i).getJSONObject("type").getString("name"));
                        }
                    } else if (attempts == 2) {
                        System.out.println("\nClue 3: Generation");
                        System.out.println("Generation " + generation);
                    } else if (attempts == 1) {
                        System.out.println("\nClue 4: Evolution Chain");
                        System.out.println("This Pokémon evolves into or from: ");
                        for (String evo : evolutions) {
                            if (!evo.equals(name)) {
                                System.out.println(evo);
     }
     }
     }
     }
     }
     }
        
        if (!guessed) {
            System.out.println("\nOut of attempts! The Pokémon was: " + name);
        }
    }
    
    // Helper method to extract generation number from URL it does this by getting the last number and splitting the string of the url 
    //and putting each charcter in the 2d string and than gets the last which is the gen 
    private static int getGenerationFromUrl(String url) {
        String[] parts = url.split("/");
        return Integer.parseInt(parts[parts.length - 1]);
    }
    
    // Below are helper methods from App class that are reused


    private static JSONObject getPokemonData(String name) throws Exception {
        return new JSONObject(API.getData("https://pokeapi.co/api/v2/pokemon/" + name));
    }
    
    private static void printStats(JSONObject pokemon) {
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
    

    private static int getTotalStats(JSONObject pokemon) {
        JSONArray stats = pokemon.getJSONArray("stats");
        int total = 0;
        for (int i = 0; i < stats.length(); i++) {
            total += stats.getJSONObject(i).getInt("base_stat");
        }
        return total;
    }
    
   
    private static double getAverageStats(JSONObject pokemon) {
        return Math.round((double) getTotalStats(pokemon) / pokemon.getJSONArray("stats").length());
    }
    
   
    private static void traverseChain(JSONObject stage, List<String> chain) {
        chain.add(stage.getJSONObject("species").getString("name"));
        JSONArray nextStages = stage.getJSONArray("evolves_to");
        for (int i = 0; i < nextStages.length(); i++) {
            traverseChain(nextStages.getJSONObject(i), chain);
        }
    }
}