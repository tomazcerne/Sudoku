import java.awt.*;
import javax.swing.*;

public class GUI extends JFrame{

    private JLabel naslov;
    private SudokuPlosca sudokuPlosca;
    private JLabel napaka;
    private JButton gumbResi;
    private JButton gumbBrisi;

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

        sudokuPlosca = new SudokuPlosca(this);
        this.addKeyListener(new Tipkovnica(sudokuPlosca));
       
        napaka = new JLabel();
        napaka.setBounds(50, 550, 600, 30);
        napaka.setHorizontalAlignment(SwingConstants.CENTER);
        napaka.setForeground(Color.RED);
        napaka.setFont(new Font("Arial", Font.PLAIN, 17));

        gumbResi = new JButton("Reši Sudoku");
        gumbResi.setBounds(150, 600, 170, 30);
        gumbResi.setFont(new Font("Arial", Font.BOLD, 15));
        gumbResi.addActionListener((e) -> {this.resiSudoku();});

        gumbBrisi = new JButton("Pobriši");
        gumbBrisi.setBounds(380, 600, 170, 30);
        gumbBrisi.setFont(new Font("Arial", Font.BOLD, 15));
        gumbBrisi.addActionListener((e) -> {this.brisi();});
        
        this.add(naslov);
        this.add(sudokuPlosca);
        this.add(napaka);
        this.add(gumbResi);
        this.add(gumbBrisi);

        this.revalidate();
        this.repaint();
    }

    public void prikaziNapako(String sporocilo) {
        napaka.setText(sporocilo);
    }

    public void resiSudoku() {
        sudokuPlosca.resi();
        this.requestFocusInWindow();
    }

    public void brisi() {
        sudokuPlosca.brisi();
        this.requestFocusInWindow();
    }

}