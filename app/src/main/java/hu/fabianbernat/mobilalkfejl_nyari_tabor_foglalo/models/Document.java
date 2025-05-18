package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.Objects;

public class Document implements Cloneable {

    private String id;
    private String nev;
    private DocumentType tipus;
    private String url;
    private Date feltoltesIdopontja;
    private Date lejaratIdopontja;
    private DocumentStatus statusz;
    private Parent tulajdonos;

    // Alapértelmezett konstruktor (Firestore miatt szükséges)
    public Document() {
    }

    // Egyszerűsített konstruktor
    public Document(String nev, DocumentType tipus, String url, Date feltoltesIdopontja,
                    DocumentStatus statusz, Parent tulajdonos) {
        this(null, nev, tipus, url, feltoltesIdopontja, null, statusz, tulajdonos);
    }

    // Teljes konstruktor
    public Document(String id, String nev, DocumentType tipus, String url, Date feltoltesIdopontja,
                    Date lejaratIdopontja, DocumentStatus statusz, Parent tulajdonos) {
        this.id = id;
        this.nev = nev;
        this.tipus = tipus;
        this.url = url;
        this.feltoltesIdopontja = feltoltesIdopontja;
        this.lejaratIdopontja = lejaratIdopontja;
        this.statusz = statusz;
        this.tulajdonos = tulajdonos;
    }


    // Getterek és setterek
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public DocumentType getTipus() {
        return tipus;
    }

    public void setTipus(DocumentType tipus) {
        this.tipus = tipus;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFeltoltesIdopontja() {
        return feltoltesIdopontja;
    }

    public void setFeltoltesIdopontja(Date feltoltesIdopontja) {
        this.feltoltesIdopontja = feltoltesIdopontja;
    }

    public Date getLejaratIdopontja() {
        return lejaratIdopontja;
    }

    public void setLejaratIdopontja(Date lejaratIdopontja) {
        this.lejaratIdopontja = lejaratIdopontja;
    }

    public DocumentStatus getStatusz() {
        return statusz;
    }

    public void setStatusz(DocumentStatus statusz) {
        this.statusz = statusz;
    }

    public Parent getTulajdonos() {
        return tulajdonos;
    }

    public void setTulajdonos(Parent tulajdonos) {
        this.tulajdonos = tulajdonos;
    }

    // equals és hashCode metódusok
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id) &&
                Objects.equals(nev, document.nev) &&
                tipus == document.tipus &&
                Objects.equals(url, document.url) &&
                Objects.equals(feltoltesIdopontja, document.feltoltesIdopontja) &&
                Objects.equals(lejaratIdopontja, document.lejaratIdopontja) &&
                statusz == document.statusz &&
                Objects.equals(tulajdonos, document.tulajdonos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nev, tipus, url, feltoltesIdopontja, lejaratIdopontja, statusz, tulajdonos);
    }

    // toString metódus a könnyebb hibakereséshez
    @NonNull
    @Override
    public String toString() {
        return "Document{" +
                "id='" + id + '\'' +
                ", nev='" + nev + '\'' +
                ", tipus=" + tipus +
                ", url='" + url + '\'' +
                ", feltoltesIdopontja=" + feltoltesIdopontja +
                ", lejaratIdopontja=" + lejaratIdopontja +
                ", statusz=" + statusz +
                ", tulajdonos=" + tulajdonos +
                '}';
    }

    // Klónozó metódus, mély másolatot készít a dokumentumról
    @NonNull
    @Override
    public Document clone() {
        try {
            Document clone = (Document) super.clone();
            clone.setFeltoltesIdopontja(this.feltoltesIdopontja != null ? (Date) this.feltoltesIdopontja.clone() : null);
            clone.setLejaratIdopontja(this.lejaratIdopontja != null ? (Date) this.lejaratIdopontja.clone() : null);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen - Why?
        }
    }

    public enum DocumentStatus {
        FELDOLGOZAS_ALATT("Feldolgozás alatt"),
        ELFOGADVA("Elfogadva"),
        ELUTASITVA("Elutasítva");

        private final String label;

        DocumentStatus(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public enum DocumentType {
        SZULOI_NYILATKOZAT("Szülői beleegyező nyilatkozat"),
        EGESZSEGUGYI_NYILATKOZAT("Egészségügyi nyilatkozat"),
        ORVOSI_IGAZOLAS("Orvosi igazolás"),
        SZAMLAZASI_ADATOK("Számlázási adatok"),
        EGYEB("Egyéb dokumentum");

        private final String label;

        DocumentType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
}