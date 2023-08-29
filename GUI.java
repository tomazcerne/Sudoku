import java.awt.*;
import javax.swing.*;

public class GUI extends JFrame{

    private JLabel naslov;
    private JPanel sudokuPlosca;
    private JLabel napaka;
    private JButton gumbResi;

    public GUI() {
        this.setTitle("Sudoku");
        this.setSize(700, 700);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);
        

        String naslovniTekst = "Vnesite poljuben Sudoku in program ga bo rešil";
        naslov = new JLabel(naslovniTekst);
        naslov.setBounds(100, 20, 500, 30);
        naslov.setHorizontalAlignment(SwingConstants.CENTER);
        naslov.setFont(new Font("Arial", Font.BOLD, 20));

        sudokuPlosca = new SudokuPlosca();
        this.addKeyListener(new Tipkovnica((SudokuPlosca)sudokuPlosca));
       
        napaka = new JLabel("napaka!");
        napaka.setBounds(100, 550, 500, 30);
        napaka.setHorizontalAlignment(SwingConstants.CENTER);
        napaka.setForeground(Color.RED);
        napaka.setFont(new Font("Arial", Font.PLAIN, 17));

        gumbResi = new JButton("Reši Sudoku");
        gumbResi.setBounds(250, 600, 200, 30);
        gumbResi.setFont(new Font("Arial", Font.BOLD, 15));
        
        this.add(naslov);
        this.add(sudokuPlosca);
        this.add(napaka);
        this.add(gumbResi);

        this.revalidate();
        this.repaint();
    }

}