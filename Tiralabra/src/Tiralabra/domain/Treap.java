package Tiralabra.domain;

import Tiralabra.util.ALista;

/**
 * Toteuttaa keko/puu-Treap rakenteellisen puun.
 *
 * @author Pia Pakarinen
 */
public class Treap implements Puu {

    /**
     * Juurisolmu.
     */
    private SolmuTreap juuri;

    /**
     * Luo uuden puun, jolle luodaan juurisolmu annetulla arvolla.
     *
     * @param emo juurisolmun arvo
     */
    public Treap(int emo) {
        this.juuri = new SolmuTreap(emo, null);
    }

    /**
     * Luo uuden tyhjän puun.
     */
    public Treap() {
    }

    @Override
    public String tulostaArvot() {
        ALista l = sisaJarjestys(juuri, new ALista());
        return l.toString();
    }

    @Override
    public void insert(int key) {
        boolean vasen = false;
        SolmuTreap i = this.juuri;
        while (i != null) {
            if (i.getArvo() == key) {
                return;
            } else if (key < i.getArvo()) {
                if (i.getVasen() == null) {
                    vasen = true;
                    break;
                } else {
                    i = i.getVasen();
                }
            } else {
                if (i.getOikea() == null) {
                    vasen = false;
                    break;
                } else {
                    i = i.getOikea();
                }
            }
        }
        //puu on tyhjä
        if (i == null) {
            this.juuri = new SolmuTreap(key, null);
            return;
        }
        //puu ei ole tyhjä; lisätään uusi lehti, sitten nostetaan se prioriteettinsa
        //mukaiseen asemaan
        if (vasen) {
            i.setVasen(new SolmuTreap(key, i));
            heapi(i.getVasen());
        } else {
            i.setOikea(new SolmuTreap(key, i));
            heapi(i.getOikea());
        }
    }

    @Override
    public void delete(int key) {
        SolmuTreap pois = searchSolmu(key);
        deleteTreap(pois);
    }

    @Override
    public boolean search(int key) {
        return searchSolmu(key) != null;
    }

    /**
     * Etsii annetun arvon sisältävän solmun puusta, palauttaa null jos ei
     * löydy.
     * Asetettu julkiseksi lähinnä testausta varten.
     * @param arvo haettava arvo
     * @return null-viite tai haetun arvon sisältävä solmu
     */
    public SolmuTreap searchSolmu(int arvo) {
        SolmuTreap i = this.juuri;
        while (i != null) {
            if (arvo == i.getArvo()) {
                break;
            } else if (arvo < i.getArvo()) {
                i = i.getVasen();
            } else {
                i = i.getOikea();
            }
        }
        return i;
    }

    /**
     * Palauttaa puun juuren.
     *
     * @return puun juuri
     */
    public SolmuTreap getJuuri() {
        return juuri;
    }

    /**
     * Kiertää solmua oikealle.
     *
     * @param s kierrettävä solmu
     */
    private void kierrosOikealle(SolmuTreap s) {
        if (s.getVanhempi() == null) {
            return;
        }

        SolmuTreap isovanh = s.getVanhempi().getVanhempi();

        s.getVanhempi().setVasen(s.getOikea());
        s.setOikea(s.getVanhempi());

        puunKiertoPuuhunTakaisin(isovanh, s);
    }

    /**
     * Kiertää solmua oikealle.
     *
     * @param s kierrettävä solmu
     */
    private void kierrosVasemmalle(SolmuTreap s) {
        if (s.getVanhempi() == null) {
            return;
        }
        SolmuTreap isovanh = s.getVanhempi().getVanhempi();

        s.getVanhempi().setOikea(s.getVasen());
        s.setVasen(s.getVanhempi());

        puunKiertoPuuhunTakaisin(isovanh, s);
    }

    /**
     * Kiinnittää puun kierretyn osan takaisin puuhun.
     *
     * @param isovanh solmu, johon uusi alipuu liitty (alkuperäisen kierretyn
     * solmun isovanhempi)
     * @param s kierretty solmu
     */
    private void puunKiertoPuuhunTakaisin(SolmuTreap isovanh, SolmuTreap s) {
        if (isovanh != null) {
            if (isovanh.getOikea() == s.getVanhempi()) {
                isovanh.setOikea(s);
            } else {
                isovanh.setVasen(s);
            }
        } else {
            s.setVanhempi(null);
            juuri = s;
        }
    }

    /**
     * Käy puun alkiot läpi ja palauttaa niiden arvota ALista-esityksenä.
     *
     * @param s käsiteltävissä oleva solmu
     * @param l lista, jolle arvot tallenetaan
     * @return puun arvot listalla
     */
    private ALista sisaJarjestys(SolmuTreap s, ALista l) {
        if (s == null) {
            return l;
        }
        l.lisaa(s.getArvo());
        sisaJarjestys(s.getVasen(), l);
        sisaJarjestys(s.getOikea(), l);
        return l;
    }

    /**
     * Poistaa annetun solmun puusta.
     *
     * @param pois poistettava solmu
     */
    private void deleteTreap(SolmuTreap pois) {
        //poistettavaa solmua ei löydy
        if (pois == null) {
            return;
        } //poistettava solmu on lehtisolmu
        else if (pois.getOikea() == null && pois.getVasen() == null) {
            if (juuri == pois) {
                juuri = null;
                return;
            }
            if (pois == pois.getVanhempi().getOikea()) {
                pois.getVanhempi().setOikea(null);
            } else {
                pois.getVanhempi().setVasen(null);
            }
            heapi(pois.getVanhempi());
            return;
        }
        teelehti(pois);
        deleteTreap(pois);
    }

    /**
     * Korjaa puun keko-ominaisuuden. Nostaa valittua solmua ylemmäs, kunnes sen
     * prioriteetti on suurempi kuin lapsien.
     *
     * @param s korjattava solmu
     */
    private void heapi(SolmuTreap s) {
        if (s.getVanhempi() == null) {
            juuri = s;
        } else if (s.getPrioriteetti() > s.getVanhempi().getPrioriteetti()) {
            if (s == s.getVanhempi().getOikea()) {
                kierrosVasemmalle(s);
            } else {
                kierrosOikealle(s);
            }
            heapi(s);
        }
    }

    /**
     * Tekee annetusta solmusta lehtisolmun.
     * @param s solmu, josta halutaan lehti
     */
    private void teelehti(SolmuTreap s) {
        while (s.getVasen() != null) {
            kierrosOikealle(s.getVasen());
        }
        while (s.getOikea() != null) {
            kierrosVasemmalle(s.getOikea());
        }
    }
}