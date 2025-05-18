package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.Date;
import java.util.List;

public class Camp {

    // Autoinkrementáló azonosító kezelése
    private static int idCounter = 0;
    private int id;

    // Tábor alapadatok
    private String name;
    private CampType campType;
    private CampFormat campFormat;
    private String location; // helyszín ID
    private Date startDate;
    private Date endDate;
    private String description;
    private int price;
    private int capacity;
    private int registeredParticipants;
    private int minAge;
    private int maxAge;
    private String organizerId;
    private List<DailyProgram> dailyProgram;
    private Coordinates koordinatak;
    private List<String> imageUrls;
    private List<DailyHeadCount> napokLebontasa;
    private Date jelentkezesiHatarido;
    private List<String> szuksegesEszkozok;
    private List<String> csoportok;
    private Integer kiserokSzama;
    private int starredCount;
    private int currentParticipants = 0;
    private String organizer = "Fábián Bernát";
    private String website = "fabbernat.github.io";

    // Alapértelmezett konstruktor (Firestore miatt szükséges)
    public Camp() {
        this.id = generateId();
    }

    // Egyszerű konstruktor
    public Camp(String name, String contactInfo) {
        this.id = generateId();
        this.name = name;
        this.location = contactInfo;
    }

    // Könnyű konstruktor
    public Camp(String name, Date startDate, Date endDate, String description,
                String location, int price) {
        this.id = generateId();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.location = location;
        this.price = price;
    }

    // Teljes konstruktor


    public Camp(int id, String name, CampType campType, CampFormat campFormat, String location, Date startDate, Date endDate, String description, int price, int capacity, int registeredParticipants, int minAge, int maxAge, String organizerId, List<DailyProgram> dailyProgram, Coordinates koordinatak, List<String> imageUrls, List<DailyHeadCount> napokLebontasa, Date jelentkezesiHatarido, List<String> szuksegesEszkozok, List<String> csoportok, Integer kiserokSzama, int starredCount) {
        this.id = id;
        this.name = name;
        this.campType = campType;
        this.campFormat = campFormat;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.price = price;
        this.capacity = capacity;
        this.registeredParticipants = registeredParticipants;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.organizerId = organizerId;
        this.dailyProgram = dailyProgram;
        this.koordinatak = koordinatak;
        this.imageUrls = imageUrls;
        this.napokLebontasa = napokLebontasa;
        this.jelentkezesiHatarido = jelentkezesiHatarido;
        this.szuksegesEszkozok = szuksegesEszkozok;
        this.csoportok = csoportok;
        this.kiserokSzama = kiserokSzama;
        this.starredCount = starredCount;
    }

    public Camp(String s, String s1, String s2, float aFloat, int resourceId, String s3, String s4) {
        this.id = generateId();
    }

    private static synchronized int generateId() {
        return ++idCounter;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CampType getCampType() {
        return campType;
    }

    public void setCampType(CampType campType) {
        this.campType = campType;
    }

    public CampFormat getCampFormat() {
        return campFormat;
    }

    public void setCampFormat(CampFormat campFormat) {
        this.campFormat = campFormat;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRegisteredParticipants() {
        return registeredParticipants;
    }

    public void setRegisteredParticipants(int registeredParticipants) {
        this.registeredParticipants = registeredParticipants;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public List<DailyProgram> getDailyProgram() {
        return dailyProgram;
    }

    public void setDailyProgram(List<DailyProgram> dailyProgram) {
        this.dailyProgram = dailyProgram;
    }

    public Coordinates getKoordinatak() {
        return koordinatak;
    }

    public void setKoordinatak(Coordinates koordinatak) {
        this.koordinatak = koordinatak;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<DailyHeadCount> getNapokLebontasa() {
        return napokLebontasa;
    }

    public void setNapokLebontasa(List<DailyHeadCount> napokLebontasa) {
        this.napokLebontasa = napokLebontasa;
    }

    public Date getJelentkezesiHatarido() {
        return jelentkezesiHatarido;
    }

    public void setJelentkezesiHatarido(Date jelentkezesiHatarido) {
        this.jelentkezesiHatarido = jelentkezesiHatarido;
    }

    public List<String> getSzuksegesEszkozok() {
        return szuksegesEszkozok;
    }

    public void setSzuksegesEszkozok(List<String> szuksegesEszkozok) {
        this.szuksegesEszkozok = szuksegesEszkozok;
    }

    public List<String> getCsoportok() {
        return csoportok;
    }

    public void setCsoportok(List<String> csoportok) {
        this.csoportok = csoportok;
    }

    public Integer getKiserokSzama() {
        return kiserokSzama;
    }

    public void setKiserokSzama(Integer kiserokSzama) {
        this.kiserokSzama = kiserokSzama;
    }

    public int getStarredCount() {
        return starredCount;
    }

    public void setStarredCount(int starredCount) {
        this.starredCount = starredCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addParticipants(int newJoiningParticipants) {
        this.currentParticipants += newJoiningParticipants;

    }

    public int getCurrentParticipants() {
        return currentParticipants;
    }

    public void setCurrentParticipants(int currentParticipants) {
        this.currentParticipants = Math.max(0, currentParticipants);
    }

    public void setType(String campType) {
        this.campType = CampType.valueOf(campType);
    }


    // Belső osztályok (napi program, koordináták, létszám stb.)
    public static class DailyProgram {
        private Date date;
        private String lead;
        private int estimatedChildren;
        private int estimatedAdults;
        private List<String> programs;
        private List<String> meals;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getLead() {
            return lead;
        }

        public void setLead(String lead) {
            this.lead = lead;
        }

        public int getEstimatedChildren() {
            return estimatedChildren;
        }

        public void setEstimatedChildren(int estimatedChildren) {
            this.estimatedChildren = estimatedChildren;
        }

        public int getEstimatedAdults() {
            return estimatedAdults;
        }

        public void setEstimatedAdults(int estimatedAdults) {
            this.estimatedAdults = estimatedAdults;
        }

        public List<String> getPrograms() {
            return programs;
        }

        public void setPrograms(List<String> programs) {
            this.programs = programs;
        }

        public List<String> getMeals() {
            return meals;
        }

        public void setMeals(List<String> meals) {
            this.meals = meals;
        }
    }

    public static class Coordinates {
        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public static class DailyHeadCount {
        private Date datum;
        private int felnottLetszam;
        private int gyerekLetszam;

        public Date getDatum() {
            return datum;
        }

        public void setDatum(Date datum) {
            this.datum = datum;
        }

        public int getFelnottLetszam() {
            return felnottLetszam;
        }

        public void setFelnottLetszam(int felnottLetszam) {
            this.felnottLetszam = felnottLetszam;
        }

        public int getGyerekLetszam() {
            return gyerekLetszam;
        }

        public void setGyerekLetszam(int gyerekLetszam) {
            this.gyerekLetszam = gyerekLetszam;
        }
    }

    // Enumok a tábor típusához és formátumához
    public enum CampType {
        CSERKESZTABOR, ERDEI_VANDORTABOR, VITORLAS_TABOR, TANCTABOR, ZENETABOR,
        ANGOL_TABOR, NEMET_TABOR, SZINJATSZO_TABOR, HITTAN_TABOR,
        PROGRAMOZO_TABOR, MATEMATIKA_TABOR, ROBOTIKA_TABOR, ERZSEBETTABOR
    }

    public enum CampFormat {
        DAY_CAMP, OVERNIGHT
    }
}
