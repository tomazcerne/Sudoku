import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.Border;

public class SudokuPlosca extends JPanel {

    private GUI glavnaPlosca;
    private JLabel[][] sudoku;
    private JLabel aktiven = null;
    private int aktI, aktJ;
    private static final String NAPACEN_VPIS = "Vpisana števila se ne smejo "+
     "ponavljati v isti vrstici, stolpcu ali kvadratu";

    public SudokuPlosca(GUI glavnaPlosca) {
        this.glavnaPlosca = glavnaPlosca;

        this.setSize(450, 450);
        this.setBounds(125, 80, 450, 450);
        this.setLayout(null);

        sudoku = new JLabel[9][9];
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {

                JLabel polje = new JLabel();
                polje.setBounds(j * 50, i * 50, 50, 50);
                polje.setHorizontalAlignment(SwingConstants.CENTER);
                polje.setFont(new Font("Arial", Font.BOLD, 25));

                int tankiRob = 1, debeliRob = 3;
                int top = 0, left = 0, bottom = tankiRob, right = tankiRob;
                if (i == 0) { top = debeliRob; }
                if (i % 3 == 2) { bottom = debeliRob; }
                if (j == 0) { left = debeliRob; }
                if (j % 3 == 2) { right = debeliRob; }

                Border obroba = BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK);
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
            aktiven.setFont(new Font("Arial", Font.BOLD, 25));
            aktiven.setForeground(Color.BLACK);
            aktiven.setText(s);
            if(preveri()) {
                glavnaPlosca.prikaziNapako("");
            }
            else {
                glavnaPlosca.prikaziNapako(NAPACEN_VPIS);
            }
        }
    }

    private int stevilka(int i, int j) {
        String s = sudoku[i][j].getText();
        if(s.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(s);
    }

    private boolean preveri() {
        boolean pravilen = true;

        int[][] stevecPoVrsticah = new int [9][10];
        int[][] stevecPoStolpcih = new int [9][10];
        int[][] stevecPoKvadratih = new int [9][10];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int st = stevilka(i, j);
                stevecPoVrsticah[i][st]++;
                stevecPoStolpcih[j][st]++;
                int kvadrat = 3 * (i / 3) + j / 3;
                stevecPoKvadratih[kvadrat][st]++;
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int st = stevilka(i, j);
                int kvadrat = 3 * (i / 3) + j / 3;
                if (st != 0 && (stevecPoVrsticah[i][st] > 1 ||
                    stevecPoStolpcih[j][st] > 1 || 
                    stevecPoKvadratih[kvadrat][st] > 1)
                    ) {
                    sudoku[i][j].setForeground(Color.RED);
                    pravilen = false;
                }
                else {
                    sudoku[i][j].setForeground(Color.BLACK);
                }
            }
        }
        return pravilen;
    }

    private int[][] tabeliraj() {
        int[][]tab = new int[9][9];
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                tab[i][j] = this.stevilka(i, j);
            }
        }
        return tab;
    }

    public void resi() {
        if(preveri()) {
            glavnaPlosca.prikaziNapako("");
            int[][] tab = this.tabeliraj();
            new Resevanje(tab, glavnaPlosca);
            this.prikaziResitev(tab);
        }
        else {
            glavnaPlosca.prikaziNapako(NAPACEN_VPIS);
        }
    }

    private void prikaziResitev(int[][] tab) {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                if (stevilka(i, j) == 0 && tab[i][j] != 0) {
                    sudoku[i][j].setFont(new Font("Arial", Font.PLAIN, 25));
                    sudoku[i][j].setForeground(new Color(52, 171, 2));
                    sudoku[i][j].setText(""+tab[i][j]);
                }
            }
        }
    }

    public void brisi() {
        for (JLabel[] vrsta : sudoku) {
            for (JLabel kvadratek : vrsta) {
                kvadratek.setText("");
            }
        }
        glavnaPlosca.prikaziNapako("");
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

