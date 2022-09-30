package no.oslomet.cs.algdat.Oblig2;


////////////////// class DobbeltLenketListe //////////////////////////////


import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;


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
                    p = p.neste = new Node<T>(a[i]); // Eventuelt resterende noder
                    hode.neste.forrige = hode;
                    p.forrige = hale;
                    hale = p;    // Når vi går ut av forløkken vil halen være siste p. Som dermed har en neste peker som peker på ingenting.
                    antall++;
                }
            }

        }
    }

    //Oppgave 3B Kildekode hentet fra 1.2.3.a
    public static void fratilKontroll(int antall, int fra, int til)
    {
        if (fra < 0)                                  // fra er negativ
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");

        if (til > antall)                          // til er utenfor tabellen
            throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + antall + ")");

        if (fra > til)                                // fra er større enn til
            throw new IllegalArgumentException
                    ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
    }


    public Liste<T> subliste(int fra, int til) {

        throw new UnsupportedOperationException();
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

        //throw new UnsupportedOperationException();
        //throw new UnsupportedOperationException();
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        if (antall == 0)  hode = hale = new Node<>(verdi, null,null);  // tom liste
        else hale = hale.neste = new Node<>(verdi,hale, null);         // legges bakerst

        antall++;                  // en mer i listen
        return true;
    }

    //Oppgave 3a
    private Node <T> finnNode(int indeks){
        Node <T> p = hode;

        //opretter variabel midten
        int midten = antall / 2;

        if(indeks <= midten){ //indeksen mindre enn midten
            for(int i = 0; i < midten; i++){
                p = p.neste;
            }
            return p;
        }
         else{ //hvis indeksen større enn midnten
             for(int i = midten + 1; i < antall; i++){
                 p = p.neste;
             }
             return p;
        }
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks,false);
        return finnNode(indeks).verdi; // denne metoden henter inn og returneren veriden ved finnNode
    }


    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException();
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

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
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
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException();
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


