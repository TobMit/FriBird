/**
 * Trieda vytvára Birda. Načítava jeho rôzne skiny. Trieda je naprogramovaná tak aby bola 
 * schopná načítavať neobmedzený počet skinov. 
 * Aby načítavala nové skiny musí sa zmeniť POCET_BIRD na požadovaný počet.  <br />
 * Pomenovanie skinu vždy musí byť: poradove_cislo_druhu-poradie_animacie.png 
 * @author Tobiáš Mitala
 * @version 2.0
 */
public class Bird {
    private String[][] adresa;
    private Obrazok bird;
    //----------------------------
    
    //Obdlzniky sa generujú na X = 60 (lavý horny)
    //                         Y = 50 (lavy horny)
    private int poziciaX = 60 + 65;
    private int poziciaY = 50 + 155;
    private int druh = 0;
    private int aktualnyBird = 0;
    
    private int pocitacTik = 0;
    
    
    public static final int POHYB = 10;
    public static final int SIRKA_BIRD = 51;
    public static final int DLZKA_BIRD = 30;
    public static final int POZICIA_X = 60 + 65;
    public static final int POCET_BIRD = 2;
    
    
    /**
     * Konštruktor načítava skiny a vytvára Birda.  
     */
    public Bird() {
        this.adresa = new String[2][4];
        
        
        for (int i = 1; i <= this.POCET_BIRD; i++) {
            int velkost = 1;
            while (velkost <= 4) {
                String str = "obr/Bird/" + i + "-" + velkost + ".png";
                this.adresa[i - 1][velkost - 1] = str;
                velkost++;
            }
        }
        this.bird = new Obrazok(this.adresa[this.druh][this.aktualnyBird]);
        this.bird.posunVodorovne(this.POZICIA_X - 100);
        this.bird.posunZvisle(this.poziciaY - 100);
        this.zobraz();
    }
    
    /**
     * Posúva animáciu birda alebo  v prípade loga FRI (logo číslo 2) otáča logo.   
     */
    public void posunAnimaciu() {
        if (this.druh != 1) {
            this.bird.zmenUhol(0);
            if (this.pocitacTik > 2) {
                this.pocitacTik = 0;
                
                this.skry();
                this.aktualnyBird++;
                if (this.aktualnyBird > 3) {
                    this.aktualnyBird = 0;
                }
                this.bird.zmenObrazok(this.adresa[this.druh][this.aktualnyBird]);
                this.zobraz();
            }
            this.pocitacTik++;
        } else {
            //otáča logo
            if (this.pocitacTik > 364) {
                this.pocitacTik = 0;
            }
            this.bird.zmenUhol(this.pocitacTik);
            this.pocitacTik += 2;
        }
        
        
    }
    
    /**
     * Posúva birda.    
     */
    public void posun(int posun) {
        this.bird.posunZvisle(posun);
        this.poziciaY += posun;
    }
    
    /**
     * Overenie, či bird nevyletel z hracej plochy.     
     */
    public boolean jeMimoHracejPlochy() {
        if (this.poziciaY >= (PozadieObrazok.HRACIA_PLOCHA_L_DOLNY_X + 30)) {
            return true;
        }
        return false;
    }
    
    /**
     * Táto metóda overuje kolízie na Y osi ( je pokračovaním overenia kolízie v triede prekážky).
     * @return true/false - koliduje  /nekoliduje
     */
    public boolean koliduje(int poziciaY, int dlzkaPrekazky) {
        if (this.poziciaY > (poziciaY + dlzkaPrekazky) && this.poziciaY < (poziciaY + dlzkaPrekazky + Prekazky.VYSKA_MEDZI_PREKAZKAMI - this.DLZKA_BIRD)) {
            return false;
            
        }
        
        return true;
    }
    /**
     * Metóda mení skin birda. Tak isto aj overuje či je poradové číslo v rozsahu. 
     * @param smer zmení smer podla hodnoty true - další, false - predchádzajúci
     */
    public void zemnBird(boolean smer) {
        this.skry();
        if (smer) {
            this.druh++;
        } else {
            this.druh--;
        }
        if (this.druh > this.POCET_BIRD - 1) {
            this.druh = 0;
        } else if (this.druh < 0) {
            this.druh = this.POCET_BIRD - 1;
        }
        this.bird.zmenObrazok(this.adresa[this.druh][this.aktualnyBird]);
        this.zobraz();
    }
    
    /**
     * Vráti Birda na začiatočnú pozíciu (stred) 
     */
    public void naStred() {
        this.bird.posunZvisle(205 - this.poziciaY);
        this.poziciaY = 205;
    }
    
    /**
     * Vráti súradnicu y 
     */
    public int getPoziciaY() {
        return this.poziciaY;
    }
    
    /**
     * Skryje 
     */
    public void skry() {
        this.bird.skry();
    }
    
    /**
     * Zobrazí 
     */
    public void zobraz() {
        this.bird.zobraz();
    }
}
