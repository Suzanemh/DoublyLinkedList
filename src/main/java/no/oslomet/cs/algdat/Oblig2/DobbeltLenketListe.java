package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.*;


public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0;
    }


    public DobbeltLenketListe(T[] a) {

        Objects.requireNonNull(a,"Tabellen er tom!"); // Vi bruker denne metoden for å utelukke at tabellen er tom
        int i = 0; for (; i < a.length && a[i] == null; i++);   // Hopper over eventuelle nuller i tabellen.

        if (i < a.length)
        {
            Node<T> p = hode = new Node<T>(a[i], null, null);  // Hodet settes på plass
            antall ++;
            // For løkke for å finne resten
            i++;
            for (; i < a.length; i++)
            {
                if (a[i] != null)
                {
                    p.neste = new Node<T>(a[i]); // Eventuelt resterende noder
                    p = p.neste;
                    hode.neste.forrige = hode;
                    p.forrige = hale;
                    hale = p;    // Når vi går ut av forløkken vil halen være siste p. Som dermed har en neste peker som peker på ingenting.
                    antall++;
                }
            }

        }
    }

    //Oppgave 3B Kildekode hentet fra 1.2.3.a
    private static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }

    //Oppgave 3b
    public Liste<T> subliste(int fra, int til) {

        Liste<T> liste = new DobbeltLenketListe<>();
        fratilKontroll(antall, fra, til);
        int lengde = til - fra;

        if (lengde < 1) {
            return liste;
        }
        Node<T> p = finnNode(fra);

        for (int i = fra; i < til; i++) {
            liste.leggInn(p.verdi);
            p = p.neste;
        }
        return liste;
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    // Koden er tatt fra kompendiet seksjon 3.3.2  Programkode 3.3.2 f)
    // Jeg har bar omgjorde den til dobbel lenke liste
    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        if (antall == 0) {
            hode = new Node<>(verdi, null,null); // tom liste
            hale = hode;
        }
        else { /*hale = hale.neste = new Node<>(verdi,hale, null); */        // legges bakerst
            hale.neste = new Node<>(verdi, hale, null);
            hale = hale.neste;
        }
        antall++;                  // en mer i listen
        return true;
    }

    //Oppgave 3a
    private Node <T> finnNode(int indeks){
        if(indeks < 0){
            throw new IndexOutOfBoundsException("Ugyldig indeks");
        }
        if(indeks < antall / 2) { // Dersom indeksen er på første halvdel
            Node <T> p = hode;
            for(int i = 0; i < indeks; i++){
                p = p.neste;
            }
            return p;
        }
        else { // Dersom indeksen er på andre halvdel
            Node <T> r = hale;
            for (int i = 1; i < antall - indeks; i++) {
                r = r.forrige;
            }
            return r;
        }
    }

    // Oppgave 5
    // Utgangspunktet var kompendiets Programkode 3.3.2 g)
    // Den måtte tilpasses en dobbel lenket liste
    // Største utfordringen var følgelig pekerne
    // Viktig å også skille ut tilfellene der noe settes i 0 indeks på tom tabell kontra en tabell med innhold.

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, true);
        if (indeks == 0) {                                                          // innlegg til 0'te indeks
            if (antall == 0) {                                                      // Dersom listen er tom
                hode = new Node<T> (verdi, null, null);
                hale = hode;
            }
            else {
                hode = new Node<T> (verdi, null, hode);                       // Dersom listen ikke er tom, må nye nodens neste peker, peke på gamle hodet.
                hode.neste.forrige = hode;                                          // Må huske å gi gamle hodet en forrige peker
            }
        }
        else if (indeks == antall) {                                          // innlegg til bakerste indeks
            hale.neste = new Node<T>(verdi, hale, null);                // her må nye nodens forrige peker peke på gamle halen
            hale = hale.neste;                                              // Så oppdaterer man halen
        }
        else {
            Node<T> p = hode;                                               // p flyttes indeks - 1 ganger. Derfor starter i som 1 og ikke 0
            for (int i = 1; i < indeks; i++) {
                p = p.neste;
            }
            p.neste = new Node<T>(verdi, p.neste.forrige, p.neste);  // verdi settes inn i listen, med riktig pekere. Merk forrige pekeren.
            p.neste.neste.forrige = p.neste;                    // MiiiiiindBLOWN - Dette var det som skulle til for å riktig referere neste indeksen
        }
        antall++;
        endringer ++;
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }


    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks,false);
        return finnNode(indeks).verdi;
    }

    /*      Oppgave 4
    Oppgaven er tatt fra løsningsforslag Avsnitt 3.3.3   oppgave 2 i kompendiet
    kaller på metoden indeksTil() og sjekker om verdien finnes
    hvis inteksTil() innenholder ønsket verdien returneres true, hvis ikke da retuneres false*/
    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) return -1;

        // for å finne verdien må vi loope gjennom lenket liste og vi starter fra hode
        Node<T> p = hode;

        for (int indeks = 0; indeks < antall ; indeks++)
        {
            // hvis nodesin verdi er lik ønsket verdi returnerer vi indexsen til verdien
            if (p.verdi.equals(verdi)) return indeks;
            // hvis verdien er ikke fant fortsetter vi å bevege oss måt neste index
            p = p.neste;
        }
        // hvis index ikke finnes returneres -1 som oppgaven ber om
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) { //kildekode inspirert fra kompendium 3.3.3.b

       Objects.requireNonNull(nyverdi, "Ikke tilatt med null verdier!");
       indeksKontroll(indeks, false);

       Node<T> p = finnNode(indeks);
       T gammelVerdi = p.verdi;
       p.verdi = nyverdi;
       return gammelVerdi;
    }

    // Oppgave 6
    @Override
    public boolean fjern(T verdi) { // (Denne skal fjerne verdi og returnere true)

        if (verdi == null) return false;          // ingen nullverdier i listen

        Node<T> q = hode;             // hjelpepekeren

        while (q != null)                         // Løkke for å finne verdien.
        {
            if (q.verdi.equals(verdi)) break;       // verdien funnet
            q = q.neste;
        }

        if (q == null) {
            return false;              // Dersom vi ikke fant verdien i listen
        }
        else if (q == hode) {   // Hvis verdien som skal fjernes er i hodet.

            if (antall == 1) {  // Hvis hode (og dermed hale) er det eneste på listen:
                hode = null;
                hale = null;
            }
            else if (antall > 1) {      // Hvis hode IKKE er det eneste i listen
                hode = hode.neste;
                hode.forrige = null;
            }
        }
        else if (q == hale) {   // Hvis halen er verdien som skal fjernes
            hale = hale.forrige;
            hale.neste = null;
            }
        else {                  // Hvis verdien ikke er i halen
            q.forrige.neste = q.neste;
            q.neste.forrige = q.forrige;
        }

        q.verdi = null;                           // nuller verdien til q
        q.neste = null;                           // nuller nestepeker

        antall--;                                 // en node mindre i listen
        return true;
    }

    @Override
    public T fjern(int indeks) {    //  (Denne skal fjerne og returnere verdien, på indeks)

        indeksKontroll(indeks, false);

        T temp;                          // hjelpevariabel

        if (indeks == 0) {              // Dersom hodet skal fjernes
            temp = hode.verdi;

            if (antall == 1) {          // Dersom HODET ER ENESTE node i listen
                hode = null;
                hale = null;
            }
            else if (antall > 1) {      // Dersom hodet IKKE er eneste node
                temp = hode.verdi;
                hode = hode.neste;     // hode flyttes til neste node
                hode.forrige = null;   // Sørger for at forrige peker til hode, peker på null
            }
        }
        else {
            Node<T> p = finnNode(indeks - 1);  // p er noden foran den som skal fjernes
            Node<T> q = p.neste;               // q skal fjernes
            temp = q.verdi;                    // tar vare på verdien som skal fjernes

            if (q == hale) {        // Dersom q er siste node
                hale = p;
                hale.neste = null;
            }
            else {                      // Dersom q IKKE er siste node
                p.neste = q.neste;
                p.forrige= q.forrige.forrige;
                q.neste.forrige = p;        // Dette var den gjenstående bakover pekeren som nesten ødela kvelden min :P
            }
        }
        antall--;                            // reduserer antallet
        return temp;
    }
    //Oppgave 7
    @Override
    public void nullstill() {
        //1.måte Metoden tok fra Kompendiet Avsnitt 3.3.2
        long startTime = System.nanoTime();
        Node<T> p = hode, q = null;     //start i hode
        while(p != null) {              //while løkke så lenge til node p er null
            q = p.neste;
            p.neste = null;
            p.verdi = null;
            p = q;
        }
        hode = hale = null;     // settes både hode og hale til null
        antall = 0;
        endringer++;

        //sjekker tidsmåling nede
        long stopTime = System.nanoTime();
        System.out.println(stopTime-startTime);
    }


    // Koden er tatt fra kompendie fra Oppgaver til Avsnitt 3.3.2 oppgave 2
    @Override
    public String toString() {

        //throw new UnsupportedOperationException();
        StringBuilder s = new StringBuilder();

        s.append('[');

        if (!tom())
        {
            Node<T> p = hode;
            s.append(p.verdi);

            p = p.neste;

            while (p != null)  // tar med resten hvis det er noe mer
            {
                s.append(',').append(' ').append(p.verdi);
                p = p.neste;
            }
        }

        s.append(']');

        return s.toString();
    }
    // tok utgangspunktet i toString()  og har bare byttet:
    // Node<T> p til hale
    // p = p.neste til p = p.forrige før og etter while løkke
    public String omvendtString() {

        //throw new UnsupportedOperationException();
        StringBuilder s = new StringBuilder();

        s.append('[');

        if (!tom())
        {
            Node<T> p = hale;
            s.append(p.verdi);

            p = p.forrige;

            while (p != null)  // tar med resten hvis det er noe mer
            {
                s.append(',').append(' ').append(p.verdi);
                p = p.forrige;
            }
        }

        s.append(']');

        return s.toString();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;
        }

        @Override
        //8a
        public T next() {  //Kildekode inspirert fra kompendium 3.3.4.c

            if (!hasNext()) throw new NoSuchElementException("Ingen verdier her!");

            if(endringer != iteratorendringer)
                throw new ConcurrentModificationException("Listen er endret!");

            T temp = denne.verdi; //tar vare på verdien i "denne"
            denne = denne.neste; // flytter "denne" til den neste noden

            fjernOK = true; //boolen lik sann

            return temp; //returnerer verdien
        }

        //8b
        public Iterator<T> iterator(){
         return new DobbeltLenketListeIterator();  //instans av iterator klassen
        }
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }

} // class DobbeltLenketListe


