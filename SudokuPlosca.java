import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;

public class SudokuPlosca extends JPanel {

    private JLabel[][] sudoku;
    private JLabel aktiven = null;
    private int aktI, aktJ; 

    public SudokuPlosca() {
        //this.setBackground(Color.LIGHT_GRAY);
        this.setSize(450, 450);
        this.setBounds(125, 80, 450, 450);
        this.setLayout(null);

        sudoku = new JLabel[9][9];
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                JLabel polje = new JLabel();
                polje.setBounds(j * 50, i * 50, 50, 50);
                //polje.setText(String.format("[%d, %d]", i, j));
                polje.setHorizontalAlignment(SwingConstants.CENTER);
                polje.setFont(new Font("Arial", Font.BOLD, 25));

                int tankiRob = 1, debeliRob = 3;
                int top = 0, left = 0, bottom = tankiRob, right = tankiRob;
                if (i == 0) { top = debeliRob; }
                if (i % 3 == 2) { bottom = debeliRob; }
                if (j == 0) { left = debeliRob; }
                if (j % 3 == 2) { right = debeliRob; }

                Border obroba = BorderFactory.createMatteBorder(top, left, bottom, right, Color.black);
                polje.setBorder(obroba);
                polje.addMouseListener(new KlikMiske(this, i, j));
                polje.setOpaque(true);
                polje.setBackground(Color.WHITE);

                sudoku[i][j] = polje;
                this.add(polje);
            }
        }
    }

    public void premik(int i, int j) {
        i += aktI;
        j += aktJ;
        if (i < 0) { i = sudoku.length - 1; }
        if (i >= sudoku.length) { i = 0; }
        if (j < 0) { j = sudoku[i].length - 1; }
        if (j >= sudoku[i].length) { j = 0; }
        zamenjajAktivnega(i, j);
    }

    public void zamenjajAktivnega(int i, int j) {
        if (aktiven != null) {
            aktiven.setBackground(Color.WHITE);
        }
        aktiven = sudoku[i][j];
        aktI = i;
        aktJ = j;
        aktiven.setBackground(new Color(230, 238, 255));
    }

    public void vpisi(String s) {
        if (aktiven != null) {
            aktiven.setText(s);
        }
    }

}

class KlikMiske extends MouseAdapter {

    private SudokuPlosca plosca;
    private int i, j;

    public KlikMiske(SudokuPlosca plosca, int i, int j) {
        this.plosca = plosca;
        this.i = i;
        this.j = j;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        plosca.zamenjajAktivnega(i, j);
    }

}

