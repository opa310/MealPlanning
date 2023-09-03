import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MealManager{
    private static List<Meal> meals;

    public MealManager() {

        meals = (List<Meal>) MealsObjectsIO.ReadObjectFromFile();
    }

    public static List<Meal> filterMealsByCaloriesProtein(int calorieThreshold, int proteinThreshold) {
        return meals.stream()
                .filter(meal -> meal.getCalories() + meal.getProtein() >= calorieThreshold + proteinThreshold)
                .collect(Collectors.toList());
    }

    public static List<Meal> searchMealsByNameOrKeyword(String query) {
        return meals.stream()
                .filter(meal -> meal.getMealName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Meal> generateRandomMealPlan(int calorieGoal, int proteinGoal) {
        List<Meal> mealPlan = new ArrayList<>();
        int currentCalories = 0;
        int currentProtein = 0;
        Random random = new Random();

        // Shuffle the meals to introduce randomness
        List<Meal> shuffledMeals = new ArrayList<>(meals);
        for (int i = shuffledMeals.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Meal temp = shuffledMeals.get(i);
            shuffledMeals.set(i, shuffledMeals.get(j));
            shuffledMeals.set(j, temp);
        }

        for (Meal meal : shuffledMeals) {
            // Check if adding this meal will exceed the goals
            if (currentCalories + meal.getCalories() <= calorieGoal &&
                    currentProtein + meal.getProtein() <= proteinGoal) {
                mealPlan.add(meal);
                currentCalories += meal.getCalories();
                currentProtein += meal.getProtein();
            }

            // If the goals are met, stop adding meals
            if (currentCalories >= calorieGoal && currentProtein >= proteinGoal) {
                break;
            }
        }

        return mealPlan;
    }

    public Meal getMealByName(String name) {
        for (Meal meal : meals) {
            if (meal.getMealName().equalsIgnoreCase(name)) {
                return meal;
            }
        }
        return null; // Return null if the meal with the specified name is not found
    }


    private List<Meal> loadMealsFromFile() {
        return (List<Meal>) MealsObjectsIO.ReadObjectFromFile();
    }


    public void addMeal(Meal meal) {
        MealsObjectsIO.AddMealToFile(meal);
    }


    public static void main(String[] args) {
        MealManager manager = new MealManager();

        // Filter meals by calorie and protein values
        List<Meal> filteredMeals = manager.filterMealsByCaloriesProtein(100, 5);
        System.out.println("Filtered Meals:");
        for (Meal meal : filteredMeals) {
            System.out.println("Meal Name: " + meal.getMealName());
            System.out.println("Calories: " + meal.getCalories());
            System.out.println("Protein: " + meal.getProtein());
            System.out.println("-----------------------------------------");
        }

        // Search meals by name or keyword
        List<Meal> searchedMeals = manager.searchMealsByNameOrKeyword("Chicken");
        System.out.println("Searched Meals:");
        for (Meal meal : searchedMeals) {
            System.out.println("Meal Name: " + meal.getMealName());
            System.out.println("Calories: " + meal.getCalories());
            System.out.println("Protein: " + meal.getProtein());
            System.out.println("-----------------------------------------");
        }

        List<Meal> randomMealPlan = manager.generateRandomMealPlan(1000, 50);

        System.out.println("Random Meal Plan:");
        for (Meal meal : randomMealPlan) {
            System.out.println("Meal Name: " + meal.getMealName());
            System.out.println("Calories: " + meal.getCalories());
            System.out.println("Protein: " + meal.getProtein());
            System.out.println("-----------------------------------------");
        }
    }
}
