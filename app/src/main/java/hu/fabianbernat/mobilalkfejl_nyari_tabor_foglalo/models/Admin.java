package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import android.location.Location;

public class Admin extends User {

    /**
     * Új tábor létrehozása.
     *
     * @param camp a létrehozandó tábor
     */
    public void createCamp(Camp camp) {
        // itt majd a Firestore adatbázisba mentjük a tábort
        // FirebaseCampService.getInstance().addCamp(camp);
    }

    /**
     * Meglévő tábor frissítése.
     *
     * @param camp a frissített tábor adatokkal
     */
    public void updateCamp(Camp camp) {
        // Firestore adatbázisban frissíteni a tábor adatait
        // FirebaseCampService.getInstance().updateCamp(camp);
    }

    /**
     * Tábor törlése azonosító alapján.
     *
     * @param campId a törlendő tábor azonosítója
     */
    public void deleteCamp(String campId) {
        // Firestore adatbázisból törölni a tábort
        // FirebaseCampService.getInstance().deleteCamp(campId);
    }

    /**
     * Felhasználó létrehozása (pl. szülő, pedagógus, önkéntes).
     *
     * @param user az új felhasználó adatai
     */
    public void createUser(User user) {
        // FirebaseUserService.getInstance().addUser(user);
    }

    /**
     * Felhasználó adatainak frissítése.
     *
     * @param user a frissített felhasználó adatokkal
     */
    public void updateUser(User user) {
        // FirebaseUserService.getInstance().updateUser(user);
    }

    /**
     * Felhasználó törlése azonosító alapján.
     *
     * @param userId a törlendő felhasználó azonosítója
     */
    public void deleteUser(String userId) {
        // FirebaseUserService.getInstance().deleteUser(userId);
    }

    /**
     * Térkép vagy helyszín hozzáadása a táborokhoz.
     *
     * @param location az új helyszín adatai
     */
    public void addLocation(Location location) {
        // FirebaseLocationService.getInstance().addLocation(location);
    }

    /**
     * Helyszín frissítése.
     *
     * @param location a frissített helyszín adatokkal
     */
    public void updateLocation(Location location) {
        // FirebaseLocationService.getInstance().updateLocation(location);
    }

    /**
     * Helyszín törlése.
     *
     * @param locationId a törlendő helyszín azonosítója
     */
    public void deleteLocation(String locationId) {
        // FirebaseLocationService.getInstance().deleteLocation(locationId);
    }

    /**
     * Programháló (napi lebontású programok) létrehozása egy táborhoz.
     *
     * @param campId          a tábor azonosítója
     * @param programSchedule a létrehozandó programháló
     */
    public void createProgramSchedule(String campId, ProgramSchedule programSchedule) {
        // Példa: FirebaseProgramService.getInstance().addProgramToCamp(campId, programSchedule);
    }

    /**
     * Meglévő programháló frissítése.
     *
     * @param campId          a tábor azonosítója
     * @param programSchedule a frissített programháló adatokkal
     */
    public void updateProgramSchedule(String campId, ProgramSchedule programSchedule) {
        // Példa: FirebaseProgramService.getInstance().updateProgramInCamp(campId, programSchedule);
    }

    /**
     * Programháló törlése egy táborból.
     *
     * @param campId       a tábor azonosítója
     * @param programDayId a törlendő napi program azonosítója
     */
    public void deleteProgramSchedule(String campId, String programDayId) {
        // Példa: FirebaseProgramService.getInstance().deleteProgramFromCamp(campId, programDayId);
    }
}
