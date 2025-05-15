Mökkien hallinta 

Järjestelmän käyttöliittymässä käyttäjä voi hallita mökkitietoja kattavasti. Toiminnallisuuksiin kuuluu uusien mökkien lisääminen, olemassa olevien tietojen tarkastelu, muokkaus sekä poistaminen. Jokaisesta mökistä tallennetaan olennaiset tiedot, kuten nimi, pinta-ala, lisätiedot ja sen ajankohtainen varaustilanne. Näin varmistetaan, että varausten tiedot pysyvät ajan tasalla ja muokattavissa reaaliaikaisesti.

---------------------------------------------
Varausten hallinta 

Kun varaus halutaan kirjata, käyttäjä voi syöttää tarvittavat tiedot, kuten varattavan mökin sekä asiakastiedot järjestelmään. Varaus on muokattavissa jälkikäteen, ja käyttäjä voi tarvittaessa muokata tai poistaa varauksen. Kaikki nämä toiminnot päivittyvät SQL-tietokantaan reaaliaikaisesti.

---------------------------------------------
Asiakashallinta 

Järjestelmässä on erillinen asiakasvälilehti, jonka avulla voidaan muokata ja poistaa asiakkaiden tietoja. Asiakastietoja ovat esimerkiksi nimi, puhelinnumero, sähköposti sekä asiakastyyppi (yksityinen tai yritys). Tiedot ovat yhteydessä SQL-tietokantaan.

---------------------------------------------
Laskujen hallinta 

Laskuja pystyy seuraamaan laskut ikkunasta, jonka tiedoissa näkyvät yksilöllinen laskunumero, laskun summa, maksuntila ja lisätiedot. Tiedot ovat yhteydessä SQL-tietokantaan.

---------------------------------------------
Yleinen data

Järjestelmä päivittää reaaliaikaisesti tietoja vapaista ja varatuista mökeistä.

---------------------------------------------
Tekninen toteutus

Järjestelmän graafinen käyttöliittymä on toteutettu JavaFX:llä, joka tarjoaa intuitiivisen ja interaktiivisen käyttöympäristön. Taustalla toimiva SQL-tietokanta huolehtii tietojen tallennuksesta ja mahdollisuuden tietojen hallintaan.

Järjestelmä integroi saumattomasti useita toimintoja, joita ovat yllämainitut mökkien, varausten, laskujen ja yleisen datan hallinta. Reaaliaikaiset päivitykset SQL-tietokantaan takaavat, että kaikki käyttäjän tekemät muutokset näkyvät välittömästi. Tämä modulaarinen rakenne varmistaa, että järjestelmä pystyy vastaamaan Mökkikodit Oy:n tarpeisiin ja tukee tehokkaasti liiketoiminnan keskeisiä prosesseja.
