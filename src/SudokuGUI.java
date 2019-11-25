import javax.swing.*;
import java.awt.*;

public class SudokuGUI {
    private final JFrame frame = new JFrame("Sudoku");
    private SudokuGrid grid;

    public SudokuGUI() {
        frame.getContentPane().add(grid = new SudokuGrid(9));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        centerView();
        frame.setVisible(true);
    }

    private void centerView() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();

        frame.setLocation((screen.width - frameSize.width) >> 1,
                (screen.height - frameSize.height) >> 1);
    }
}
