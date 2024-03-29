import java.util.Random;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Táto trieda tvorí základ celej hry. 
 * V tejto trede sa spájaju všetky ostatné triedy do celku, riadi sa priebeh hry
 * generovanie kolizií, nastavovanie pozadia a skinu, počíta sa skóre.
 * @author Tobiáš Mitala
 * @version 1.7
 */
public class HraciePole {
    private PozadieObrazok pozadie;
    private Bird bird;
    private ArrayList<Prekazky> prekazky;
    private Random nahoda;
    private StavObrazovky obrazovka;
    private CiselnyDisplay zobrazovcCisel;
    private CiselnyDisplay zobrazovacTop;
    private InformacieRozne infoObr;
    private Sipky sipkaLavaHore;
    private Sipky sipkaPravaHore;
    private Sipky sipkaLavaDole;
    private Sipky sipkaPravaDole;
    private static HraciePole hra = new HraciePole();
    //-------------------------
    private int posun = 0;
    private int skore = 0;
    private int topSkore = 0;
    private int pocitadloTikou = 0;
    
    private Manazer engine = new Manazer();
    /**
     * Konštruktor vytvorí celu hru zo všetkými predefinovanými hodnotami. 
     */
    private HraciePole() {
        this.pozadie = new PozadieObrazok();
        this.nahoda = new Random();
        this.zobrazovcCisel = new CiselnyDisplay();
        this.zobrazovacTop = new CiselnyDisplay();
        this.infoObr = new InformacieRozne();
        this.bird = new Bird();
        this.sipkaLavaHore = new Sipky(80, 195 , false);
        this.sipkaPravaHore = new Sipky(200, 197 , true);
        this.sipkaLavaDole = new Sipky(25, 325 , false);
        this.sipkaPravaDole = new Sipky(250, 327 , true);
        
        this.sipkyZobraz();
        
        this.prekazky = new ArrayList();
        
        this.engine.spravujObjekt(this);
        
        this.startovaciaObrazovka();
    }
    /**
     * Vytvorí inštanciu jedináčika.
     * @return Vráti inštanciu HraciePole.
     */
    public static HraciePole getHraciePole() {
        return HraciePole.hra;
    }
    
    //----------------------Stavy obrazoviek--------------------
    /**
     * V tejto metóde sa nastavujú viditelnosti objektov (napr. šípky, logo, ostatné info) 
     * a generujú sa nové prekážky. Tato metóda slúži na zobrazenie všetkých úvodných informácií. 
     */
    private void startovaciaObrazovka() {
        this.obrazovka = StavObrazovky.STARTOVACIA_OBRAZOVKA;
        this.bird.naStred();
        this.bird.zobraz();
        
        this.infoObr.obrazkyLogoZobraz();
        this.pozadie.zobrazHlinu();
        
        this.generatorPrekazok(0);
        this.generatorPrekazok(180);
    }
    
    /**
     * Táto metóda skrýva všetky objekty. Čistí hraciu plochu pred ďalším pokusom. 
     */
    private void restObrazovky() {
        for (Prekazky prekaza: this.prekazky) {
            prekaza.skry();
        }
        this.prekazky.clear();
        this.obrazovka =  StavObrazovky.PREHRA;
        this.bird.skry();
        
        this.pozadie.skryHlinuPauza();
        
        this.zobrazovacTop.skry();
        this.zobrazovcCisel.skry();
        
        this.infoObr.obrazkyPrehraSkry();
        this.infoObr.obrazkyNarazenieSkry();
        this.infoObr.tlacidloRestartSkry();
        
        this.startovaciaObrazovka();
    }
    
    /**
     * Zobrazí informácie keď sa zastaví hra.  
     */
    private void obrazovkaPauza() {
        this.pozadie.zobrazHlinuPauza();
        
        this.zobrazovcCisel.zobraz();
        
        this.infoObr.obrazkyPauzaZobraz();
        
        this.obrazovka = StavObrazovky.PAUZA;
    }
    
    /**
     * Zobrazí informácie po narazení do objektov. 
     * Zobrazí informácie (skóre, posledné najvyššie skóre, atď.)  
     */
    private void obrazovkaPrehra() {
        this.obrazovka = StavObrazovky.PREHRA;
        this.zobrazovcCisel.skry();
        
        this.pozadie.skryHlinuHraBezi();
        this.pozadie.zobrazHlinu();
        
        this.infoObr.obrazkyNarazenieZobraz();
        this.infoObr.obrazkyPrehraZobraz();
        this.infoObr.tlacidloRestartZobraz();
        
        this.zobrazovcCisel.zobrazCislo(this.skore, 150, 170);
        this.zobrazovacTop.zobrazCislo(this.topSkore, 150, 260);
        
        
        this.resetSkore();
        // Keď sa zmačkne enter / medzerník znovu - stav sa nastavy na štartovaciu obrazovku
        // a zreštartu je sa
    }
    
    //----------------------------------ENGINE----------------------------------
    /**
     * Táto metóda dáva do pohybu všetky ostatné metódy a aj objekty. 
     * Táto metóda riadi posun gravitácie, posun prekážok, overuje kolíziu... 
     */
    public void tik() {
        /**
         * Animácia Birdu sa vykonáva aj napriek tomu že všetky ostane pohybujúce objekty stoja. 
         * Nehýbe sa iba keď je pauza.  
         */
        if (this.obrazovka == StavObrazovky.HRA_BEZI || this.obrazovka == StavObrazovky.STARTOVACIA_OBRAZOVKA) {
            this.bird.posunAnimaciu();
        }
        
        
        if (this.obrazovka !=  StavObrazovky.HRA_BEZI) {
            if (this.obrazovka == StavObrazovky.PREHRA) {
                return;
            }
            /**
             * Tu sa vytvára blikanie textu "space"
             * hodnoty 15 udávajú rýchlosť blikania
             * čím vyššia hodnota, tým pomalšie blikanie
             */
            if (this.pocitadloTikou <= 15) {
                this.infoObr.medzernikZobraz(Bird.POZICIA_X + 23, this.bird.getPoziciaY() + 60);
                this.pocitadloTikou++;
            } else if (this.pocitadloTikou > 15 && this.pocitadloTikou <= 30) {
                this.infoObr.medzernikSkry();
                this.pocitadloTikou++;
            } else {
                this.pocitadloTikou = 0;
            }
            //zobrazí tlačídla šípok
            if (this.obrazovka == StavObrazovky.STARTOVACIA_OBRAZOVKA) {
                this.sipkyZobraz();
            }
            
            return;
        }
        
        //Posúva prekážky ak hra beží.
        for (Prekazky prekaza: this.prekazky) {
            prekaza.posunPrekazky();
        }
        
        this.sipkySkry();
        this.infoObr.obrazkyLogoSkry();
        
        this.pozadie.zobrazHlinuHraBezi();
        this.zobrazovcCisel.zobraz();
        
        //Keď je prekážka už mimo hracej plochy tak sa maže.  
        if (this.prekazky.get(0).getPoziciaX() < -50) {
            this.prekazky.get(0).skry();
            this.prekazky.get(1).skry();            
            this.prekazky.remove(0);
            this.prekazky.remove(0);
            this.generatorPrekazok(0);
        }
        
        this.gravitaciaPosun();
        
        //Overenie či je Bird mimo hracej plochy
        if (this.bird.jeMimoHracejPlochy()) {
            this.obrazovkaPrehra();
        }
        // Overenie kolízie a pripočítavanie skóre .
        // Funkcia koliduje spĺňa dve funkcie kolízie a skóre 
        for (Prekazky prek : this.prekazky) {
            if (prek.koliduje(this.bird.getPoziciaY(), this.bird)) {
                this.obrazovkaPrehra();
                
            }
        }
        //System.out.println("tik \n");
    }
    
    //--------------------------OSTATNE METÓDY--------------------------
    /**
     * Táto metóda generuje prekážky. Hornu aj dolnú na správnych súradniciach a správnou medzerou. 
     * Následne sa pridávajú do arraylistu. 
     * @param sirkaMedziPrkazkami - udáva vzdialenosť medzi dvomi a dvomi prekážkami v x osi 
     */
    private void generatorPrekazok(int sirkaMedziPrkazkami) {
        //Opatrenie aby sa nevigenerovali čísla menšie ako 20
        int spodnaHranaHornejPrekazky = (this.nahoda.nextInt(22) + 3) * 10;
        Prekazky hornaPrekazka = new Prekazky(true, spodnaHranaHornejPrekazky, sirkaMedziPrkazkami, this);
        Prekazky dolnaPrekazka = new Prekazky(false, spodnaHranaHornejPrekazky, sirkaMedziPrkazkami, this);
        
        this.prekazky.add(hornaPrekazka);
        this.prekazky.add(dolnaPrekazka);
    }
    
    /**
     * Posúva Bird smerom dole pri každom tiku. Ak je výskok tak posun je do opačného smeru. 
     */
    private void gravitaciaPosun() {
        this.posun += Bird.POHYB;
        if (this.posun >= Bird.POHYB) {
            this.posun = Bird.POHYB;
        }
        this.bird.posun(this.posun);
    }
    
    /**
     * Metóda zvyšuje skóre a zobrazuje ho. 
     */
    public void skoruje() {
        this.skore++;
        this.zobrazovcCisel.zobrazCislo(this.skore, 150, 525);
        
    }
    
    /**
     * Metóda resetuje skóre. 
     */
    private void resetSkore() {
        if (this.skore > this.topSkore) {
            this.topSkore = this.skore;
        }
        this.skore = 0;
    }
    
    /**
     * Metóda zobrazuje šípky. 
     */
    private void sipkyZobraz() {
        this.sipkaLavaHore.zobraz();
        this.sipkaPravaHore.zobraz();
        this.sipkaLavaDole.zobraz();
        this.sipkaPravaDole.zobraz();
    }
    /**
     * Metóda skrýva šípky.  
     */
    private void sipkySkry() {
        this.sipkaLavaHore.skry();
        this.sipkaPravaHore.skry();
        this.sipkaLavaDole.skry();
        this.sipkaPravaDole.skry();
    }
    
    
    //-------------------------------Ovládanie-----------------------------------
    /**
     * Ovládanie pomocou kláves Enter a medzerník. 
     */
    public void aktivuj() { 
        if (this.obrazovka != StavObrazovky.HRA_BEZI) {
            this.posun = 0;
        }
        
        //Výška bežného skoku. 
        if (this.obrazovka == StavObrazovky.HRA_BEZI) {
            // číslo určuje o koľko poskočí voči jednotke padania
            this.posun -= (5 * Bird.POHYB);
        }
        
        if (this.obrazovka == StavObrazovky.STARTOVACIA_OBRAZOVKA || this.obrazovka == StavObrazovky.PAUZA) { 
            /**
             * Číslo je o jednotku menšie – pri každom výskoku sa odpočítava už aj gravitácia 
             * a pri prvom skoku to tak nie je, preto poskočí vyššie.  
             */ 
            this.posun -= (4 * Bird.POHYB);
            this.obrazovka =  StavObrazovky.HRA_BEZI;
            //zobrazí sa až pri prvom skoku
            this.zobrazovcCisel.zobrazCislo(this.skore, 150, 525);
            this.infoObr.obrazkyPauzaSkry();
            this.infoObr.medzernikSkry();
            
        }
        
        if (this.obrazovka == StavObrazovky.PREHRA) {
            this.obrazovka = StavObrazovky.STARTOVACIA_OBRAZOVKA;
            this.restObrazovky();
            
        }
    }
    
    /**
     * Ovládanie pomocou klávesy esc. Metóda zastaví alebo ukončí hru. 
     */
    public void zrus() {
        if (this.obrazovka == StavObrazovky.HRA_BEZI) {
            this.obrazovkaPauza();
            return;
        } else {
            int ukoncenie = JOptionPane.showConfirmDialog(null, "Naozaj chcete ukončiť FriBird?");
            if (ukoncenie == 0) {
                System.exit(0);
            }
            
        }
    }
    
    /**
     * Ovládanie pomocou myšky. 
     */
    public void vyberSuradnice(int x, int y) {
        //tlacidlo restar
        if (this.obrazovka == StavObrazovky.PREHRA) {
            if (x < (50 + 200) && x > 50 ) {
                if (y < (320 + 60) && y > 320) {
                    this.obrazovka = StavObrazovky.STARTOVACIA_OBRAZOVKA;
                    this.restObrazovky();
                }
            }
            
        } else {
            //šípka 30x50
            //šípka Lavá Hore
            if (x < (80 + 30) && x > 80 ) {
                if (y < (195 + 50) && y > 195) {
                    this.bird.zemnBird(false);
                }
             
                //šípka pravá hore
            } else if (x < (200 + 30) && x > 200 ) {
                if (y < (179 + 50) && y > 179) {
                    this.bird.zemnBird(true);
                }
             
                //šípka lává dole
            } else if (x < (25 + 30) && x > 25 ) {
                if (y < (325 + 50) && y > 325) {
                    this.pozadie.zemnPozadie(false);
                }
            
                //šípka prava dole
            } else if (x < (250 + 30) && x > 250 ) {
                if (y < (327 + 50) && y > 327) {
                    this.pozadie.zemnPozadie(true);
                }
            }
            //this.pozadie.zobrazHlinu();
            this.restObrazovky();
        }
    }
}
