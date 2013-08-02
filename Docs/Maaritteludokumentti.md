Määrittelydokumentti

Tässä tietorakenteiden harjoitustyössä toteutetaan erilaisten hakupuiden tehokkuutta vertaileva ohjelma. 

Mahdollisia vertailukohteita ovat punamustat-, B-, treap- ja threaded-puut.

Ohjelmassa on mahdollista vertaillaan perusoperaatioiden (insert, delete, search) lisäksi myös puiden alkioiden tulostusta suuruusjärjestyksessä.

Harjoitustyön tarkoituksena on lähinnä harjoitella eri puiden rakentamista ja jonkun vielä nimettömän järjestämisalgoritmin toteuttamista (vaativuus kuitenkin todennäköisesti O(n log n)). Jokaisella puulla keskimääräiset vaativuusluokat ainakin perusoperaatioiden osalta ovat luokkaa O(log n), joten tehokkuuden vertailussa tuskin esiintyy merkittäviä eroja.

Käyttäjän on mahdollista valita mitä rakententeista vertaillaan kullakin kerralla (ainakin kahta, mahdollisesti myös kaikkia samaan aikaan). Lisäksi käyttäjä valitsee verrattavat operaatiot (yksi, useampi, tai kaikki kerrallaan). Mahdollisesti käyttäjän on myös mahdollista määritellä puiden sisältämien alkioiden määrä vertailuissa.

Tilavaativuus tulee siis olemaan luokkaa O(n), jossa n = puiden alkioiden määrä x puiden määrä. Aikavaativuus on maksimissaan O(n), kun vertaillaan alkoioiden tulostamista, ja minimissä O (log n) kun vertailussa ei tutkita alkioiden tulostamista.