import java.util.*;

public class Resevanje {

    //private GUI glavnaPlosca;
    private int[][] sudoku;
    private static final String NEMOGOCE =
    "Tak sudoku je nemogoče rešiti v celoti";

    public Resevanje (int[][] sudoku, GUI glavnaPlosca) {
        this.sudoku = sudoku;
        if( !this.resiKlasicno()) {
            glavnaPlosca.prikaziNapako(NEMOGOCE);
            return;
        }
        if ( !this.resen()) {
            if( !this.ugibanje()) {
                glavnaPlosca.prikaziNapako(NEMOGOCE);
            }
        }
    }

    private boolean ugibanje() {
        if (this.resen()) {
            return true;
        }
        int[] min = this.minimalnoMoznosti();
        int iMin = min[0];
        int jMin = min[1];
        ArrayList<Integer> moznosti = this.kandidati(iMin, jMin);
        for (int moznost : moznosti) {
            int[][] kopija = this.kopiraj();
            sudoku[iMin][jMin] = moznost;
            if(this.resiKlasicno()) {
                return this.ugibanje();
            }
            sudoku = kopija;
        }
        return false;
    }

    private boolean resiKlasicno() {

        int stSprememb = 0;
        do {
            stSprememb = 0;
            for (int i = 0; i < sudoku.length; i++) {
                for (int j = 0; j < sudoku[i].length; j++) {
                    if (sudoku[i][j] != 0) {
                        continue;
                    }
                    ArrayList<Integer> seznam = kandidati(i, j);
                    int dolzina = seznam.size();
                    if (dolzina == 0) {
                        return false;
                    }
                    if (dolzina == 1) {
                        sudoku[i][j] = seznam.get(0);
                        stSprememb++;
                    }

                }
            }
        } while (stSprememb > 0);
        return true;
    }

    private ArrayList<Integer> kandidati(int i, int j) {

        ArrayList<Integer> seznam = new ArrayList<>();
        boolean[] vsebovana = new boolean[10];

        int iKvadrat = (i / 3) * 3;
        int jKvadrat = (j / 3) * 3;

        for (int x = 0; x < 9; x++) {
            vsebovana[sudoku[i][x]] = true; //vrstica
            vsebovana[sudoku[x][j]] = true; //stolpec
            
            int iPremik = x / 3;
            int jPremik = x % 3;

            vsebovana[sudoku[iKvadrat + iPremik][jKvadrat + jPremik]] = true; //kvadrat
        }

        for (int kandidat = 1; kandidat <= 9; kandidat++) {
            if ( !vsebovana[kandidat]) {
                seznam.add(kandidat);
            }
        }
        return seznam;
    }

    private boolean resen() {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                if (sudoku[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int[] minimalnoMoznosti() {
        int iMin = 0;
        int jMin = 0;
        int stMin = 9;
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                if (sudoku[i][j] != 0) {
                    continue;
                }
                int st = kandidati(i, j).size();
                if (st < stMin) {
                    stMin = st;
                    iMin = i;
                    jMin = j;
                }
            }
        }
        int[] tab = {iMin, jMin};
        return tab;
    }

    private int[][] kopiraj() {
        int[][] tab = new int[sudoku.length][sudoku[0].length];
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku[i].length; j++) {
                tab[i][j] = sudoku[i][j];
            }
        }
        return tab;
    }
}
