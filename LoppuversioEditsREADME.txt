-------7.5.2017----
README tiedosto liittyen LoppuversioBlackjack.java
p��ohjelmaan



N�M� OVAT T�RKEIT� EDITTEJ� jotka korjaavat bugeja


riville 97 p��ohjelmassa  t�nne lis�ttiin controller.clearbothhands metodi, joka clearaa k�det
t�m� editti on pakollinen koska muuten kun player saa natural blackjackeja, niin h�nen korttejaan 
ei ikin� poisteta kierroksen j�lkeen

riville 102 p��ohjlemassa lis�tty controller.clearboth hands metodi joka clearaa k�det

riville 115 p��ohjelmassa
t�nne riville 115 piti siirt�� vanhaa koodia else-haaran sulkujen sis��n
jos t�t� editti� ei tee, niin ohjelma crashaa seuraavalla blackjack rundilla index out of bounds erroriin arraylisteille
siirretty koodi oli seuraavat rivit:
System.out.println(" player did NOT have natural blackjack ");
splitWasMade = false;                 //variable splitWasMade siihen sijoitetaan splitOffering() return value, ett� joko SPLITTAUS ONNISTUI tai EP�ONNISTUI
splitWasMade = controller.splitOffering();


riville 53 p��ohjelmassa lis�tty 
boolean splitWasMade=false; 


LOPPU

