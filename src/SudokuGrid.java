import javafx.util.Pair;
import org.jpl7.Query;
import org.jpl7.Variable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SudokuGrid extends JPanel {

    private static final Font FONT = new Font("Verdana",
            Font.CENTER_BASELINE,
            20);

    private final JTextField[][] grid;
    private final Map<JTextField, Point> mapFieldToCoordinates =
            new HashMap<>();

    private final int dimension;
    private final JPanel gridPanel;
    private final JPanel buttonPanel;
    private final JButton hintButton;
    private final JButton clearButton;
    private final JButton submitButton;
    private final JPanel[][] minisquarePanels;
    private ArrayList<Pair<Integer,Integer>> queryArray;

    SudokuGrid(int dimension) {
        this.grid = new JTextField[dimension][dimension];
        this.dimension = dimension;

        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                JTextField field = new JTextField();
                mapFieldToCoordinates.put(field, new Point(x, y));
                grid[y][x] = field;
            }
        }

        this.gridPanel = new JPanel();
        this.buttonPanel = new JPanel();

        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        Dimension fieldDimension = new Dimension(30, 30);

        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                JTextField field = grid[y][x];
                field.setBorder(border);
                field.setFont(FONT);
                field.setHorizontalAlignment(JTextField.CENTER);
                field.setPreferredSize(fieldDimension);
            }
        }

        int minisquareDimension = (int) Math.sqrt(dimension);
        this.gridPanel.setLayout(new GridLayout(minisquareDimension,
                minisquareDimension));

        this.minisquarePanels = new JPanel[minisquareDimension]
                [minisquareDimension];

        Border minisquareBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        for (int y = 0; y < minisquareDimension; ++y) {
            for (int x = 0; x < minisquareDimension; ++x) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(minisquareDimension,
                        minisquareDimension));
                panel.setBorder(minisquareBorder);
                minisquarePanels[y][x] = panel;
                gridPanel.add(panel);
            }
        }

        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                int minisquareX = x / minisquareDimension;
                int minisquareY = y / minisquareDimension;

                minisquarePanels[minisquareY][minisquareX].add(grid[y][x]);
            }
        }

        this.gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,
                2));
        this.clearButton = new JButton("Clear");
        this.hintButton = new JButton("Hint");
        this.submitButton = new JButton("Submit");

        this.buttonPanel.setLayout(new BorderLayout());
        this.buttonPanel.add(clearButton, BorderLayout.WEST);
        this.buttonPanel.add(hintButton, BorderLayout.CENTER);
        this.buttonPanel.add(submitButton, BorderLayout.EAST);

        this.setLayout(new BorderLayout());
        this.add(gridPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);

        clearButton.addActionListener((ActionEvent e) -> {
            clearAll();
        });

        hintButton.addActionListener((ActionEvent e) -> {
            hint();
        });

        submitButton.addActionListener((ActionEvent e) -> {
            solve();
        });
    }

    void clearAll() {
        for (JTextField[] row : grid) {
            for (JTextField field : row) {
                field.setText("");
            }
        }
    }

    void hint() {
        queryArray = new ArrayList();
        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                String text = grid[y][x].getText();
                int number = -1;

                try {
                    if(!text.trim().equals("")){
                        number = Integer.parseInt(text.trim());
                        if(number == 1 || number == 2 || number == 3 || number == 4 || number == 5 || number == 6 || number ==7 || number == 8 || number == 9){
                            int transitNumber = transitNumber(y,x);
                            Pair<Integer,Integer> pair = new Pair(number,transitNumber);
                            queryArray.add(pair);
                        }else{
                            grid[y][x].setText("");
                            JOptionPane.showMessageDialog(this,
                                    "Value "+(number + "")+" is out of range",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                        grid[y][x].setText("");
                        JOptionPane.showMessageDialog(this,
                                ex.getMessage(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                }
                }
            }

            try {
                // Query q1 = new Query("consult('D:/Github/CPSC312-2019WT1-Project2/cpsc312 project2.pl')");
                Variable S = new Variable("S");
                Query q = new Query("solveOnce("+convertPairArray(queryArray)+", S)");
                String solution;
                if(q.hasSolution()){
                    solution = q.oneSolution().get("S").toString().substring(3);
                    int value = Integer.parseInt(solution.substring(1,2));
                    int position = Integer.parseInt(solution.substring(4,solution.length()-1));
                    grid[transitRow(position)][transitColumn(position)].setText(value+"");
                }else{
                    JOptionPane.showMessageDialog(this,
                            "There must be something wrong in the sudoku",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
    }

    void solve() {
        queryArray = new ArrayList();
        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                String text = grid[y][x].getText();
                int number = -1;

                try {
                    if(!text.trim().equals("")){
                        number = Integer.parseInt(text.trim());
                        if(number == 1 || number == 2 || number == 3 || number == 4 || number == 5 || number == 6 || number ==7 || number == 8 || number == 9){
                            int transitNumber = transitNumber(y,x);
                            Pair<Integer,Integer> pair = new Pair(number,transitNumber);
                            queryArray.add(pair);
                        }else{
                            grid[y][x].setText("");
                            JOptionPane.showMessageDialog(this,
                                    "Value "+(number + "")+" is out of range",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    grid[y][x].setText("");
                    JOptionPane.showMessageDialog(this,
                            ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        try {
            // Query q1 = new Query("consult('D:/Github/CPSC312-2019WT1-Project2/cpsc312 project2.pl')");
            Variable S = new Variable("S");
            Query q = new Query("solveOnce("+convertPairArray(queryArray)+", S)");
            String solution;
            if(q.hasSolution()) {
                while (q.hasMoreSolutions() && !isFull(grid)) {
                    solution = q.nextSolution().get("S").toString().substring(3);
                    int value = Integer.parseInt(solution.substring(1, 2));
                    int position = Integer.parseInt(solution.substring(4, solution.length() - 1));
                    grid[transitRow(position)][transitColumn(position)].setText(value + "");
                }
            }else{
                JOptionPane.showMessageDialog(this,
                        "There must be something wrong in the sudoku",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    int transitNumber(int y, int x){
        return 9*y+x+1;
    }

    int transitRow(int position){
        return (position-1)/9;
    }

    int transitColumn(int position){
        return (position-1)%9;
    }

    String convertPairArray(ArrayList<Pair<Integer,Integer>> queryArray) {
        String returnString = "";
        if (queryArray.size() == 0) {
            returnString = "[]";
        } else {
            returnString = "[";
            for (int i = 0; i < queryArray.size()-1; i++) {
                String temp = "";
                temp = temp + "(";
                temp = temp + (queryArray.get(i).getKey() + "");
                temp = temp + ",";
                temp = temp + (queryArray.get(i).getValue() + "");
                temp = temp + ")";
                temp = temp + ",";
                returnString = returnString + temp;
            }
            String temp = "";
            temp = temp + "(";
            temp = temp + (queryArray.get(queryArray.size()-1).getKey() + "");
            temp = temp + ",";
            temp = temp + (queryArray.get(queryArray.size()-1).getValue() + "");
            temp = temp + ")";
            returnString = returnString + temp;
            returnString = returnString + "]";
        }
        return returnString;
    }

    Boolean isFull(JTextField[][] grid) {
        for (int y = 0; y < dimension; ++y) {
            for (int x = 0; x < dimension; ++x) {
                String text = grid[y][x].getText();
                if (text.equals(""))
                    return false;
            }
        }
        return true;
    }

}