-------7.5.2017----
README tiedosto liittyen LoppuversioBlackjack.java
pääohjelmaan



NÄMÄ OVAT TÄRKEITÄ EDITTEJÄ jotka korjaavat bugeja


riville 97 pääohjelmassa  tänne lisättiin controller.clearbothhands metodi, joka clearaa kädet
tämä editti on pakollinen koska muuten kun player saa natural blackjackeja, niin hänen korttejaan 
ei ikinä poisteta kierroksen jälkeen

riville 102 pääohjlemassa lisätty controller.clearboth hands metodi joka clearaa kädet

riville 115 pääohjelmassa
tänne riville 115 piti siirtää vanhaa koodia else-haaran sulkujen sisään
jos tätä edittiä ei tee, niin ohjelma crashaa seuraavalla blackjack rundilla index out of bounds erroriin arraylisteille
siirretty koodi oli seuraavat rivit:
System.out.println(" player did NOT have natural blackjack ");
splitWasMade = false;                 //variable splitWasMade siihen sijoitetaan splitOffering() return value, että joko SPLITTAUS ONNISTUI tai EPÄONNISTUI
splitWasMade = controller.splitOffering();


riville 53 pääohjelmassa lisätty 
boolean splitWasMade=false; 


LOPPU

