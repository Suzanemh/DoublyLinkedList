# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 
Oppgaven er levert av følgende studenter:
* Navn Navnesen, S123456, s123456@oslomet.no
* ...

TEST
# Arbeidsfordeling

I oppgaven har vi hatt følgende arbeidsfordeling:
* Per har hatt hovedansvar for oppgave 1, 3, og 5. 
* Else har hatt hovedansvar for oppgave 2, 4, og 6. 
* Fatima har hatt hovedansvar for oppgave 7 og 8. 
* Vi har i fellesskap løst oppgave 10. 

# Oppgavebeskrivelse

I oppgave 1 så gikk vi frem ved å ...

I oppgave 2 så brukte vi en ... til å ...

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

