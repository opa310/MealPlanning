import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MealsObjectsIO {

    private static final String filepath="/Users/ogo/JavaProjects/MealPlanning/src/Meals.obj";

    public static void WriteMealListToFile(List<Meal> meals) {

        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(meals);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void AddMealToFile(Meal meal) {

        List<Meal> meals = (List<Meal>) MealsObjectsIO.ReadObjectFromFile();

        try {

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            meals.add(meal);

            objectOut.writeObject(meals);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Object ReadObjectFromFile() {
        List<Meal> meals = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filepath));
            meals = (List<Meal>) objectInputStream.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return meals;
    }

    public static void main(String[] args) {

        MealsObjectsIO objectIO = new MealsObjectsIO();

        Meal m = new Meal("J",0,0,"X","Y");
        objectIO.AddMealToFile(m);

    }


}
