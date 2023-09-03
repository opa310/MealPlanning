import java.io.Serializable;
public class Meal implements Serializable {
    String mealName;

    int Calories;

    int Protein;

    String Ingredients = "";

    String Instructions = "";

    Meal(String name){
        this.mealName = name;
    }

    Meal(String name, int cal, int prot, String ingred, String instr){
        this.mealName = name;
        this.Calories = cal;
        this.Protein = prot;
        this.Ingredients = ingred;
        this.Instructions = instr;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    public int getProtein() {
        return Protein;
    }

    public void setProtein(int protein) {
        Protein = protein;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }

    public String getInstructions() {
        return Instructions;
    }

    public void setInstructions(String instructions) {
        Instructions = instructions;
    }


}
