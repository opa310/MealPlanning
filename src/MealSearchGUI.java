import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MealSearchGUI extends JFrame {
    private MealManager mealManager;
    private DefaultListModel<Meal> resultListModel;
    private JList<Meal> resultList;
    private JTextArea detailsTextArea;
    private JScrollPane detailsScrollPane;

    public MealSearchGUI() {
        mealManager = new MealManager(); // Replace with the correct file path
        resultListModel = new DefaultListModel<>();
        resultList = new JList<>(resultListModel);
        detailsTextArea = new JTextArea(10, 30);
        detailsScrollPane = new JScrollPane(detailsTextArea);

        setTitle("Meal Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        JLabel calorieLabel = new JLabel("Calories:");
        JTextField calorieTextField = new JTextField();
        JLabel proteinLabel = new JLabel("Protein:");
        JTextField proteinTextField = new JTextField();
        JLabel keywordLabel = new JLabel("Keyword:");
        JTextField keywordTextField = new JTextField();
        JButton searchButton = new JButton("Search");
        JButton generatePlanButton = new JButton("Generate Meal Plan");

        inputPanel.add(calorieLabel);
        inputPanel.add(calorieTextField);
        inputPanel.add(proteinLabel);
        inputPanel.add(proteinTextField);
        inputPanel.add(keywordLabel);
        inputPanel.add(keywordTextField);
        inputPanel.add(searchButton);
        inputPanel.add(generatePlanButton);

        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BorderLayout());
        resultsPanel.add(new JScrollPane(resultList), BorderLayout.WEST);
        resultsPanel.add(detailsScrollPane, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultListModel.clear();
                int calorieGoal = Integer.parseInt(calorieTextField.getText());
                int proteinGoal = Integer.parseInt(proteinTextField.getText());
                String keyword = keywordTextField.getText();

                List<Meal> searchResults = mealManager.filterMealsByCaloriesProtein(calorieGoal, proteinGoal);
                searchResults.addAll(mealManager.searchMealsByNameOrKeyword(keyword));

                for (Meal meal : searchResults) {
                    resultListModel.addElement(meal);
                }
            }
        });

        resultList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // Check if the value is a Meal object
                if (value.getClass().equals(Meal.class)) {
                    Meal meal = (Meal) value;
                    // Set the text for the list item using meal name, calories, and protein
                    setText(meal.getMealName() + " - Calories: " + meal.getCalories() + " - Protein: " + meal.getProtein());
                }

                return this;
            }
        });

        generatePlanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultListModel.clear();
                int calorieGoal = Integer.parseInt(calorieTextField.getText());
                int proteinGoal = Integer.parseInt(proteinTextField.getText());

                List<Meal> mealPlan = mealManager.generateRandomMealPlan(calorieGoal, proteinGoal);

                for (Meal meal : mealPlan) {
                    resultListModel.addElement(meal);
                }
            }
        });

        resultList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = resultList.getSelectedIndex();
                if (selectedIndex >= 0) {
                    Meal selectedMeal = resultListModel.getElementAt(selectedIndex);
                    displayMealDetails(selectedMeal);
                }
            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(resultsPanel, BorderLayout.CENTER);
    }

    private void displayMealDetails(Meal meal) {
        if (meal != null) {
            detailsTextArea.setText("Ingredients:\n" + meal.getIngredients() + "\n\nInstructions:\n" + meal.getInstructions());
            detailsTextArea.setCaretPosition(0); // Scroll to the top
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MealSearchGUI gui = new MealSearchGUI();
                gui.setVisible(true);
            }
        });
    }
}
