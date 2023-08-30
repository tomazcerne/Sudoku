import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class Tipkovnica extends KeyAdapter {

    private SudokuPlosca plosca;

    public Tipkovnica(SudokuPlosca plosca) {
        this.plosca = plosca;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int koda = e.getKeyCode();
        if (koda >= '1' && koda <= '9') {
            plosca.vpisi("" + (koda - '0'));
        }
        else if (koda == 8) { //backspace
            plosca.vpisi("");
        }
        else if (koda == 37) { //levo
            plosca.premik(0, -1);
        }
        else if (koda == 38) { //gor
            plosca.premik(-1, 0);
        }
        else if (koda == 39) { //desno
            plosca.premik(0, 1);
        }
        else if (koda == 40) { //dol
            plosca.premik(1, 0);
        }
    }
}
