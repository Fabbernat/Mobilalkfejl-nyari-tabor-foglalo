package com.example.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.Date;
import java.util.List;

public class User {

    private String id;
    private String email;
    private String nev;
    private UserType userType;
    private UserRole userRole;
    private OrganizerType organizerType;
    private String telefonszam;
    private String lakcim;
    private Date szuletesiDatum;
    private Date regisztracioIdopontja;
    private boolean aktivitas;
    private List<Document> dokumentumok;
    private VolunteerType onkentesTipus;
    private String intezmenyNev;
    private String intezmenyAzonosito;
    private List<Child> gyermekek;
    private List<String> registeredCamps;
    private List<String> createdCamps;
    private String consentForm;

    // Alapértelmezett konstruktor (Firestore miatt szükséges)
    public User() {
    }

    // Getterek és setterek...

    // Enumok

    public enum UserType {
        SZULO("szülő"),
        PEDAGOGUS("pedagógus"),
        KISKORU("kiskorú"),
        TABORI_SZERVEZO("tábori szervező/animátor"),
        ADMIN("adminisztrátor");

        private final String displayName;

        UserType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum UserRole {
        SZULO("szulo"),
        PEDAGOGUS("pedagogus"),
        ONKENTES("onkentes"),
        ADMIN("admin");

        private final String displayName;

        UserRole(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum OrganizerType {
        ALTABORVEZETO("Altáborvezető"),
        ANIMATOR("Tábori animátor"),
        PROGRAM_LEBONYOLITO("Program-lebonyolító"),
        KONYHAI_KISEGITO("Konyhai kisegítő"),
        LOGISZTIKUS("Tábori logisztikus");

        private final String displayName;

        OrganizerType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public enum VolunteerType {
        ALTABORVEZETO("Altáborvezető"),
        ANIMATOR("Tábori animátor"),
        PROGRAM("Program-lebonyolító"),
        KONYHA("Konyhai kisegítő"),
        LOGISZTIKA("Tábori ellátmányfelelős");

        private final String displayName;

        VolunteerType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Beágyazott modellek

    public static class Child {
        private String id;
        private String nev;
        private Date szuletesiDatum;
        private String taj;
        private String allergia;
        private String specialisIgeny;

        public Child() {
        }

        // Getterek és setterek...
    }

    public static class TeacherGroup {
        private int gyerekekSzama;
        private String korosztaly;
        private int kiserokSzama;

        public TeacherGroup() {
        }

        // Getterek és setterek...
    }
}
