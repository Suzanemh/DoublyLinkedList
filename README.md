# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Paula Sielawa s364584
* Suzane Mahmoud Hamze s364759
* Majda Ivic s362095
* George Alexander Saavedra s360536


# Arbeidsfordeling

Vi har hatt følgende arbeidsfordeling:
* Suzane, George og Paula har hatt hovedansvar for oppgave 1 og 3. 
* Majda har hatt hovedansvar for oppgave 2 og 4. 
* George har hatt hovedansvar for oppgave 5. 
* Suzane har hatt hovedansvar for oppgave 8.
* Paula har hatt hovedansvar for oppgave 7 og 9.
* George og Majda har hatt hovedansvar for oppgave 6 og 10.

# Oppgavebeskrivelse

* Oppgave 3:
3a:
finnNode() Er løst med 2 if setninger, for å avgjøre hvor indeksen er ift midten.
Den første if setningen flytter seg indeks antall ganger til riktig node, mens den andre som
starter fra halen beveger seg (antall-indeks) antall ganger.

* Oppgave 7:

I oppgave 7 ble vi bedt om å tømme listen på 2 måter og velge den måten som er mest effektivt.
For å registrere tid vi har brukt:
long startTime = System.nanoTime();
long stopTime = System.nanoTime();
System.out.println(stopTime-startTime);

1.måte i utgangspunktet ble tatt fra Kompendiet. Vi har registrert tidsmåling på 6333.

2.måte som går ut på å lage en løkke som inneholder fjern() metode og går inntil listen er tom.
for(int i = antall -1; i >= 0; i--) {
    fjern(i);
}
Vi har registrert tidsmåling på: 23958.
Utifra tidsmåling er det første måte som er mest effektiv og derfor kun første metode ble beholdt i klassen.

