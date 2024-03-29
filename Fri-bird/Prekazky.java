/**
 * Trieda generuje jednu prekážku na základne vstupných parametrov 
 * @version 1.0
 * @author Tobiáš Mitala
 */
public class Prekazky {
    private Obdlznik prekazka;
    
    //-------Premenne---------
    //Obdlzniky sa generujú na X = 60 (lavý horny)
    //                         Y = 50 (lavy horny)
    private int poziciaY = -(240 + 50);
    private int poziciaX = 240 + 60;
    private HraciePole hraciePole;
    private int spodnaHranaHornejPrekazky;
    private boolean hornaPrekazka;
    
    private int poziciaYDolny;
    
    //------Konštanty----------------------------
    private static final int SIRKA_PREKAZKY = 50;
    private static final int DLZKA_PREKAZKY = 240;
    
    //private static final int POHYB_PREKAZKY = 10;
    private static final int POHYB_PREKAZKY = 2;
    /**
     * Určuje šírku medzi prekážkami v x osi 
     */
    public static final int SIRKA_MEDZI_PREKAZKAMI = 300;
    /**
     * Určuje šírku medzi prekážkami v y osi 
     */
    public static final int VYSKA_MEDZI_PREKAZKAMI = 140; //140
    
    /*Poznámky:
     * Hracie pole má veľkosť 
     *          410x300
     *      (vyska) (sirka)
     * 
     * Sirka prekážky je 50
     * Maximálna dlžka prekážky môže byť 240
     * Minimálna dlžka prekážky môže byť 20
     * Medzera medzi prekážkami je 140
     * 
     * Štartovna pozicia je x 300
     */
    
    /**
     * Konštruktor vytvorí prekážku na základe vstupných údajov. 
     * @param hornaPrekazka true – horná, false – dolná 
     * @param spodnaHranaHornejPrekazky Určuje číslo spodnej hrany hornej prekážky  
     * @param Určuje šírku medzi prekážkami v x osi 
     */
    public Prekazky(boolean hornaPrekazka, int spodnaHranaHornejPrekazky, int sirkaMedziPrkazkami, HraciePole hraciePole) {
        this.prekazka = new Obdlznik();
        this.prekazka.zmenFarbu("yellow");
        this.prekazka.zmenStrany(Prekazky.SIRKA_PREKAZKY, Prekazky.DLZKA_PREKAZKY);
        
        if (hornaPrekazka) {
            this.poziciaX += sirkaMedziPrkazkami;
            // this.poziciaX - 60 je tam preto aby som mal synchronne údaje o x súradnici
            this.prekazka.posunVodorovne(this.poziciaX - 60);
            this.poziciaY += spodnaHranaHornejPrekazky;
            this.prekazka.posunZvisle(this.poziciaY);
            this.poziciaY += 50;
            
            this.poziciaYDolny = this.poziciaY + Prekazky.DLZKA_PREKAZKY;
        } else {
            this.poziciaX += sirkaMedziPrkazkami;
            this.prekazka.posunVodorovne(this.poziciaX - 60);
            //90 + 50
            this.poziciaY += (spodnaHranaHornejPrekazky + Prekazky.VYSKA_MEDZI_PREKAZKAMI + Prekazky.DLZKA_PREKAZKY);
            this.prekazka.posunZvisle(this.poziciaY);
            
            this.poziciaY += 50;
        }
        this.prekazka.zobraz();
        
        this.hraciePole = hraciePole;
        
        this.spodnaHranaHornejPrekazky = spodnaHranaHornejPrekazky;
        this.hornaPrekazka = hornaPrekazka;
        
    }
    
    /**
     * (Prekážku) Zobrazí.  
     */
    public void zobraz() {
        this.prekazka.zobraz();
    }
    
    /**
     * (Prekážku) Skryje.  
     */
    public void skry() {
        this.prekazka.skry();
    }
    
    
    /**
     * (Prekážku) Skryje.  
     */
    public void posunPrekazky() {
        this.prekazka.posunVodorovne(-Prekazky.POHYB_PREKAZKY);
        this.poziciaX -= Prekazky.POHYB_PREKAZKY;
    }
    
    
    /**
     * (Prekážku) Skryje.  
     */
    public int getPoziciaX() {
        return this.poziciaX;
    }
    
    /**
     * Táto funkcia overuje či sa Bird a prekážka zrazila .
     * Druhov úlohou  tejto funkcie je vyhodnocovať skóre.
     */
    public boolean koliduje(int poziciaBird, Bird bird) {
        /*
         * druhý sposob overovania kolízie s rectangle.class (prispôsobené) - intersects
        //Prekážka -t
        //Bird r
        
        int tw = this.SIRKA_PREKAZKY;
        int th = this.DLZKA_PREKAZKY;
        int rw = Bird.SIRKA_BIRD;
        int rh = Bird.DLZKA_BIRD;
        
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        
        int tx = this.poziciaX;
        int ty = this.poziciaY;
        int rx = Bird.POZICIA_X;
        int ry = poziciaBird;
        
        return rw > 0 && rh > 0 && th > 0 && tw > 0 && rx < tx + tw && rx + rw > tx && ry < ty + th && ry + rh > ty;
        
        */
        //Prekažka
        int xPrekazkaLavy = this.poziciaX;
        int yPrekazkaLavy = this.poziciaY;
        int xPrekazkaPravy = this.poziciaX + Prekazky.SIRKA_PREKAZKY;
        int yPrekazkaPravy = this.poziciaY + Prekazky.DLZKA_PREKAZKY;
        
        //Bird
        int xBirdLavy = Bird.POZICIA_X;
        int yBirdLavy = poziciaBird;
        int xBirdPravy = Bird.POZICIA_X + Bird.SIRKA_BIRD;
        int yBirdPravy = poziciaBird + Bird.DLZKA_BIRD;
        
                
        if ( ((xPrekazkaLavy <= xBirdPravy) && (xBirdPravy <= xPrekazkaPravy))
                || ((xPrekazkaLavy <= xBirdLavy) && (xBirdLavy <= xPrekazkaPravy)) ) {
            //Overenie skóre
            if ( (this.hornaPrekazka 
                && xPrekazkaPravy - 1 <= xBirdLavy
                && xPrekazkaPravy + 10 > xBirdLavy
                && !bird.koliduje(this.poziciaY, this.DLZKA_PREKAZKY)) ) {
                this.hraciePole.skoruje();
            }
            //Overenie kolízie sa vykonáva až po overení skóre kvôli returnu 
            //Druhé overenie prebieha v Triede Bird. 
            if (this.hornaPrekazka) {
                return bird.koliduje(this.poziciaY, this.DLZKA_PREKAZKY);
            }
            //System.out.println("");
        }
        
        return false;
    }
}