Arshdeep Singh 
1. Project Overview
I used PokeApi for my project 
My pokemon compares two different pokemon in how well they would perform in different stages of the game 
It solves the problem of entertainment and curiosity. This is because this can help people choose what pokemon to catch and what pokemon to use in the games.

2. Code Breakdown
public static void main(String[] args)
This the start of the program and gives 3 options than reads the user input 
After it reads the user input it picks one of the features 

public  static void compareTwoPokemon(Scanner scanner)
Asks the user for two Pokémon names after the input is 2  meaning they selected they 2nd feature 
Gets their evolution chains
Uses compareEvolutions() to show and compare the results

public  static List<String> getEvolutionChain(String name)
This method calls the selected pokemons species chain and than the evolution chain meaning that it is going to get what the pokemon is evolution is so it can later call it to compare
It returns the list of the evolution chain in order using traverseChain 

 public  static void traverseChain(JSONObject stage, List<String> chain)
Adds the current Pokémon’s name to the chain list
Checks if it evolves to others and repeats
Builds the full evolution order (from start to end)

public  static void compareEvolutions(List<String> evo1, List<String> evo2)
Compares the base forms for early game and depending on who has the higher average that pokemon is better for early game 
It than checks which pokemon is better for the middle game using its middle stage and then compares the average
It then compares the final forms it first checks for mega evolutions and uses the compareFinalForms() 
method for the final comparison and decide which pokemon is better for the late game 
public  static void compareFinalForms
It calculates the average of the two final forms and states which one is better for late game 
It prints the final comparison  of the two pokemon as well

 public static JSONObject getPokemonData(String name)
Calls API.getData() with a Pokémon specific URL based off the name and than returns the data as a JSONObject

public static void printStats(JSONObject pokemon)
Extracts stats array from JSON
Prints each stat (HP, Attack, Defense, etc.)
Shows total and average stats

private static int getTotalStats(JSONObject pokemon)
Sums values from all stat categories and returns total base stat number

private static double getAverageStats(JSONObject pokemon)
Divides total stats by number of stat categories (6) and then returns rounded average

private static double getAverageStats(JSONObject pokemon)
Divides total stats by number of stat categories (6) and then returns rounded average

public static void displaySinglePokemonStats(Scanner scanner)
This method is used when the input of the user is scanned as 1 
It than takes Pokémon name as the input
Than fetches the data and displays stats using printStats()

public static void guessThatPokemon(Scanner scanner)
This method is used when the input of the user is 3 
It then selects a random pokemon and than gives the user 4 chances to guess the pokemon 
After each guess a hint is given to the user (stat,type,generation, evolutions)

private static int getGenerationFromUrl(String url)
Gets the generation from the last of the string 
3. Features Implemented
✔ Base Project (88%)
 - Uses an external API (PokeApi)
 - Uses multiple Java methods and logic
 - Parses JSON response using basic string matching
Statistical computations: This is the method to find the average of the stats to compare the pokemon  and is done by getting the total stats and dividing it by how many stat categories there are and this is done by getting the length.
private static double getAverageStats(JSONObject pokemon) {
       return Math.round((double) getTotalStats(pokemon) / pokemon.getJSONArray("stats").length());
   }
}

 Filter/sort data: My program filters data by filtering out only  the pokemon name in JSON and then sorting it by different evolution stages.

Predictive score = 96 %
	



4. Screenshots or Outputs
When 1 for pokemon stats is clicked 
Project Pokémon
Choose an option:
1. View single Pokémon stats
2. Compare two Pokémon
3. Guess That Pokémon
Enter your choice (1-3): 1

=== Single Pokémon Stats ===
Enter Pokémon name: 
Ditto

DITTO STATS:
hp: 48
attack: 48
defense: 48
special-attack: 48
special-defense: 48
speed: 48
TOTAL: 288
AVERAGE: 48.0


When the comparer is used 
Choose an option:
1. View single Pokémon stats
2. Compare two Pokémon
3. Guess That Pokémon
Enter your choice (1-3): 2

Pokémon Evolution Comparison Tool
Enter first Pokémon: 
Charmander
Enter second Pokémon: 
Bulbasaur

=== Comparison Results ===

=== EARLY GAME ===

CHARMANDER STATS:
hp: 39
attack: 52
defense: 43
special-attack: 60
special-defense: 50
speed: 65
TOTAL: 309
AVERAGE: 52.0

BULBASAUR STATS:
hp: 45
attack: 49
defense: 49
special-attack: 65
special-defense: 65
speed: 45
TOTAL: 318
AVERAGE: 53.0

charmander average: 52.0
bulbasaur average: 53.0
bulbasaur is better for early game!

=== MID GAME ===

CHARMELEON STATS:
hp: 58
attack: 64
defense: 58
special-attack: 80
special-defense: 65
speed: 80
TOTAL: 405
AVERAGE: 68.0

IVYSAUR STATS:
hp: 60
attack: 62
defense: 63
special-attack: 80
special-defense: 80
speed: 60
TOTAL: 405
AVERAGE: 68.0

charmeleon average: 68.0
ivysaur average: 68.0
Both are equal for mid game!

=== LATE GAME ===

CHARIZARD STATS:
hp: 78
attack: 84
defense: 78
special-attack: 109
special-defense: 85
speed: 100
TOTAL: 534
AVERAGE: 89.0

VENUSAUR STATS:
hp: 80
attack: 82
defense: 83
special-attack: 100
special-defense: 100
speed: 80
TOTAL: 525
AVERAGE: 88.0

charizard (final) average: 89.0
venusaur (final) average: 88.0
charizard is better for late game!

When you pick option 3/pokemon guesser
Project Pokémon
Choose an option:
1. View single Pokémon stats
2. Compare two Pokémon
3. Guess That Pokémon
Enter your choice (1-3): 3

=== Guess That Pokémon ===

Guess the Pokémon based on these clues!
You have 4 attempts.

Clue 1: Base Stats
hp: 86
attack: 81
defense: 97
special-attack: 81
special-defense: 107
speed: 43
TOTAL: 495
AVERAGE: 83.0

Your guess: 
Dittp
Incorrect! Attempts left: 3

Clue 2: Type
rock
grass

Your guess: 
SEptitle
Incorrect! Attempts left: 2

Clue 3: Generation
Generation 3

Your guess: 
Charmender
Incorrect! Attempts left: 1

Clue 4: Evolution Chain
This Pokémon evolves into or from: 
lileep

Your guess: 
Cradily
Correct! It's cradily!

5. What I Learned
Learned how an API would work and how to code with it
Learned about JSON arrays and how JSON is needed for APIs
Learned how to How to parse JSON

