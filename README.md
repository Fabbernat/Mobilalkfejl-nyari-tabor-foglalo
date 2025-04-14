# Mobilalkfejl-nyari-tabor-foglalo  
**Mobil alkalmazás fejlesztés projektmunka**  
Nyári tábor foglaló alkalmazás  

---

## **1. Mérföldkő: Fejlesztett Funkciók**
- **Firebase Integration**
  - Teljes Firebase autentikáció (bejelentkezés/regisztráció)
  - `RegisterActivity.java` - Regisztrációs folyamat
- Cloud Firestore CRUD műveletek
    - `Camp.java` - Tábor modell
    - `BrowseCampsActivity.java` - Tábor foglalási logika
 
  ### **UI Komponensek**  
| Komponens | Layout típus | Fájl |  
|-----------|--------------|------|  
| MainActivity | ConstraintLayout | `activity_main.xml` |  
| Login | LinearLayout | `activity_login.xml` |  
| CampList | GridLayout | `activity_list_camps.xml` |  
| Gallery | GridLayout + CardView | `item_gallery.xml` |  

### **Követelményteljesítés**  
| Elem | Megvalósítás helye |  
|-------|---------------------|  
| Fordítási hiba nincs |  `build.gradle.kts` konfiguráció |  
| Futtatási hiba nincs |  Tesztelve egy Google Pixel 8a eszközön |  
| Firebase auth | `BrowseCampsActivity.java`, `ListCampsActivity.java`, `MainActivity.java`, `RegisterActivity.java` fájlokban |  
| Bevíteli mezők | `activity_main.xml`, `activity_register.xml` fájlokban |  
| Layout variety | 3 különböző layout típus |  
| Reszponzivitás | megfelelő dp erőforrások |
- ** Saját táboros ikon **
- ** 12 Kép **
- **Animációk**  
  - 4 animáció
- **Adatmodell létrehozva és tesztelve***

---

## **2. Mérföldkő Tervezett Bővített Funkciók**  
- Reszponzív alkalmazás, álló és fekvő módban is megfelelően jelenik meg minden GUI elem, az oldalak legalább 90%-án
- UI Animációk a főoldalon
- Intentek használata: navigáció meg van valósítva az activityk között (minden activity elérhető)
