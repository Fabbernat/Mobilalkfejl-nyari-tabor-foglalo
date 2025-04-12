package com.example.mobilalkfejl_nyari_tabor_foglalo.models;

import com.google.firebase.firestore.auth.User;

import java.util.Date;

public class DocumentModel {

    private String id;
    private String nev;
    private DocumentType tipus;
    private String url;
    private Date feltoltesIdopontja;
    private Date lejaratIdopontja;
    private DocumentStatus statusz;
    private User tulajdonos;

    public DocumentModel() {
    }

    public DocumentModel(String id, String nev, DocumentType tipus, String url, Date feltoltesIdopontja,
                         Date lejaratIdopontja, DocumentStatus statusz, User tulajdonos) {
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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public DocumentType getTipus() { return tipus; }
    public void setTipus(DocumentType tipus) { this.tipus = tipus; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public Date getFeltoltesIdopontja() { return feltoltesIdopontja; }
    public void setFeltoltesIdopontja(Date feltoltesIdopontja) { this.feltoltesIdopontja = feltoltesIdopontja; }

    public Date getLejaratIdopontja() { return lejaratIdopontja; }
    public void setLejaratIdopontja(Date lejaratIdopontja) { this.lejaratIdopontja = lejaratIdopontja; }

    public DocumentStatus getStatusz() { return statusz; }
    public void setStatusz(DocumentStatus statusz) { this.statusz = statusz; }

    public User getTulajdonos() { return tulajdonos; }
    public void setTulajdonos(User tulajdonos) { this.tulajdonos = tulajdonos; }

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
