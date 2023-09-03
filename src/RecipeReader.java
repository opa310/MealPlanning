import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeReader {

    public static List<Meal> readRecipeFile(String filePath) {
        List<Meal> meals = new ArrayList<>();
        Meal currentMeal = null;
        boolean readingIngeredients = true;
        boolean readingInstructions = true;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while (!Objects.equals(reader.readLine(), null)) {

                while (!Objects.equals(line = reader.readLine(), "")) {
                    if (currentMeal == null) {
                        // First line of a meal contains name, calories, and protein
                        String[] mealInfo = line.split("-");
                        if (mealInfo.length == 3) {
                            String name = mealInfo[0].trim();
                            System.out.println(name);
                            int calories = Integer.parseInt(mealInfo[1].trim());
                            int protein = Integer.parseInt(mealInfo[2].trim());
                            currentMeal = new Meal(name, calories, protein, "", "");
                        }
                    } else if (Objects.equals(currentMeal.getIngredients(), "")){

                        while (readingIngeredients) {
                            // Read the ingredients line by line
                            if (currentMeal.getIngredients().isEmpty()) {
                                currentMeal.setIngredients(line);
                            } else {
                                currentMeal.setIngredients(currentMeal.getIngredients() + "\n" + line);
                            }
                            if(Objects.equals(line = reader.readLine(), "-")) readingIngeredients = false;
                        }

                        line = reader.readLine();// Skip the "-" that appears after the ingredients

                        while (readingInstructions) {
                            // Read the instructions line by line
                            if (currentMeal.getInstructions().isEmpty()) {
                                currentMeal.setInstructions(line);
                            } else {
                                currentMeal.setInstructions(currentMeal.getInstructions() + "\n" + line);
                            }
                            if(Objects.equals(line = reader.readLine(), "-")) readingInstructions = false;
                        }
                    }
                }

                // Continue searching for recipes if a valid recipe wasn't reached
                if (currentMeal != null) {
                    meals.add(currentMeal);
                    currentMeal = null;
                    readingIngeredients = true;
                    readingInstructions = true;
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return meals;
    }



    public static void main(String[] args) {
        String filePath = "/Users/ogo/JavaProjects/MealPlanning/src/Meals.txt"; // Replace with your file path
        List<Meal> meals = readRecipeFile(filePath);


        System.out.println(meals.size() +"\n");


        /*
        // Print the meals
        for (Meal meal : meals) {
            System.out.println("Meal Name: " + meal.getMealName());
            System.out.println("Calories: " + meal.getCalories());
            System.out.println("Protein: " + meal.getProtein());
            System.out.println("Ingredients:\n" + meal.getIngredients());
            System.out.println("Instructions:\n" + meal.getInstructions());
            System.out.println("-----------------------------------------");
        }

         */




        MealsObjectsIO objectIO = new MealsObjectsIO();
        objectIO.WriteMealListToFile(meals);


        List<Meal> meals2 = (List<Meal>) objectIO.ReadObjectFromFile();

        for (Meal meal : meals2) {
            System.out.println("Meal Name: " + meal.getMealName());
            System.out.println("Calories: " + meal.getCalories());
            System.out.println("Protein: " + meal.getProtein());
            System.out.println("Ingredients:\n" + meal.getIngredients());
            System.out.println("Instructions:\n" + meal.getInstructions());
            System.out.println("-----------------------------------------");
        }





        /*
        System.out.println("\n");

        Meal student = new Meal("J",0,0,"X","Y");

        objectIO.AddMealToFile(student);

        meals2 = (List<Meal>) objectIO.ReadObjectFromFile();

        for (Meal meal : meals2) {
            System.out.println("Meal Name: " + meal.getMealName());
            System.out.println("Calories: " + meal.getCalories());
            System.out.println("Protein: " + meal.getProtein());
            System.out.println("Ingredients:\n" + meal.getIngredients());
            System.out.println("Instructions:\n" + meal.getInstructions());
            System.out.println("-----------------------------------------");
        }

         */
    }
}

