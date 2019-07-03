# Presentation

Starten mit Vagrant(Branch: presentation-testdata):
```bash
java -jar target/team52-0.4.1-exec.jar --spring.config.location=/Users/henryspanka/Development/gpse-ss-2019-team5-2/application_mailhog.properties
```

0. Roomed und Mailhog offen haben im Responsive (Handy Modus)
1. Login: **mgaffke** / **password**
2. **Neues Meeting Erstellen**
3. Daten ausfüllen für bevorzugten Termin
    * Name: **Preisübergabe Uni-Bielefeld**
    * Start Date: **27.09.2019 10:00**
    * End Date: **27.09.2019 12:00**
    * Locations:
        * **Ratingen**:
            * Participants: **100**
            * Equipment:
                * **Beamer**
                * **Phone System**
                * **Speakers**
        * **Mumbai**:
            * Participants: **4**
            * Equipment:
                * **Phone System**
4. Bevorzugter Raum **nicht verfügbar** (Ratingen3)
5. Zeitvorsprung mit **+1h Button** bis Ratingen3 und Ratingen4 verfügbar
6. Zurückgehen und **Zeit ändern**
    * Start Date: **26.09.2019 13:00**
    * End Date: **26.09.2019 15:00**
6. Ratingen3 und Mumbai2 **anklicken und buchen** (WICHTIG: Beide müssen angeklickt sein)
7. **Interne Personen hinzufügen**:
    * **Marie-Sophie B.**
    * **Markus C.**
8. **Externe Personen hinzufügen**:
    * **Nils N. <nils.n@unibi.de>**
    * **Sven W. <sven.w@unibi.de>**
9. **Email Notifications aktivieren für**:
    * **Marie-Sophie B.**
    * **Markus C.**
    * **Nils N.**
11. Logout

---

12. Login: **markusc** / **password**
13. **Neues Meeting Erstellen**
14. Daten ausfüllen
    * Name: **Scrum Meeting**
    * Start Date: **26.09.2019 14:00**
    * End Date: **26.09.2019 15:00**
    * Locations:
        * **Mumbai**:
            * Participants: **13**
            * Equipment:
                * **Phone System**
15. Mumbai2 **anklicken und buchen**
16. Logout

---
17. Login: **mgaffke** / **password**
18. Umgebuchtes **Meeting "Preisübergabe Uni-Bielefeld" öffnen**
19. Raum in Mumbai auf **Mumbai ändern**
20. **Neues Meeting Erstellen**
21. Daten ausfüllen
    * Name: **Test Room**
    * Start Date: **NOW**
    * End Date: **NOW+30m**
    * Locations:
        * **Mumbai**:
            * Participants: **1**
            * Equipment:
                * **Phone System**
22. Mumbai **anklicken und buchen**
23. **Meeting bestätigen**

### Szenario
* Für die Preisübergabe eines Entwicklungsprojektes von Capgemini mit der Universität Bielefeld soll ein Event am Standort in Ratingen (Deutschland) organisiert werden. Hierfür wird ein Präsentationsraum mit Projektor und Audio-Anlage für 100 Personen benötigt. Ein Team aus Mumbai (Indien) mit 4 Personen soll per Videokonferenz hinzugeschaltet werden. Als Koordinator des Projektes möchte Markus G. hierfür die passenden Räume buchen. Es soll in der letzten Septemberwoche stattfinden. Der bevorzugte Termin ist Fr 27.9. von 10 Uhr bis 12 Uhr. Als Alternativtermin wurde bereits Do 26.9. von 13 Uhr bis 15 Uhr im Vorfeld abgestimmt. Einladungen sollen an alle Personen in Mumbai (mit Zeitverschiedung von 3h 30min), aber nur 5 Personen (Markus G., Markus U., Marie B. von Capgemini, sowie Nils N. und Sven W. als externe Gäste der Uni Bielefeld) für den Teil in Ratingen verschickt werden. Während der Buchung mit der RoomBroker-App stellt Markus G. fest, dass der gewünschte Präsentationsraum zum bevorzugten Termin bereits besetzt ist. Obwohl der Raum am Freitag Nachmittag frei wäre, entscheidet sich Markus G. für den vorabgestimmten Alternativtermin am Donnerstag, der ebenfalls ausgewählt werden kann.
* Nach der erfolgreichen Buchung, wird der Raum in Mumbai vom System wegen einer anderen Buchung automatisch in einen anderen Raum verschoben. Aus Erfahrung weiß Markus G., dass der neue Raum für Videokonferenzen störanfällig ist. Als er die Verschiebung mitbekommt, bucht er den Raum in Mumbai noch einmal um. Spontan schaut er ob der neue Raum in Mumbai frei ist und bucht diesen, um die Verbindung zur Videokonferenzanlage mit seinem Laptop zu testen.


## Additional
* Registrierung nur mit @capgeminitest.com möglich.
