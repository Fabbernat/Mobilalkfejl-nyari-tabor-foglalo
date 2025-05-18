package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.Date;
import java.util.List;

public class Application {

    private String applicationId;
    private Camp camp;
    private Parent applicant;
    private Date applicationDate;
    private ApplicationStatus applicationStatus;
    private PaymentStatus paymentStatus;
    private double amountToPay;
    private List<Child> children;
    private TeacherGroup teacherGroup;
    private String note;
    private List<Document> attachedDocuments;


    // 05.18 hozzaadas
    private boolean medicalInfoProvided;
    private boolean paymentCompleted;


    // Alapértelmezett konstruktor (Firestore miatt szükséges)
    public Application() {
        this.applicationDate = new Date();
        this.medicalInfoProvided = false;
        this.paymentCompleted = false;
    }

    public Application(String applicationId, Camp camp, Parent applicant, Date applicationDate,
                       ApplicationStatus applicationStatus, PaymentStatus paymentStatus, double amountToPay,
                       List<Child> children, TeacherGroup teacherGroup, String note,
                       List<Document> attachedDocuments) {
        this.applicationId = applicationId;
        this.camp = camp;
        this.applicant = applicant;
        this.applicationDate = applicationDate;
        this.applicationStatus = applicationStatus;
        this.paymentStatus = paymentStatus;
        this.amountToPay = amountToPay;
        this.children = children;
        this.teacherGroup = teacherGroup;
        this.note = note;
        this.attachedDocuments = attachedDocuments;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public Camp getCamp() {
        return camp;
    }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }

    public Parent getApplicant() {
        return applicant;
    }

    public void setApplicant(Parent applicant) {
        this.applicant = applicant;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public TeacherGroup getTeacherGroup() {
        return teacherGroup;
    }

    public void setTeacherGroup(TeacherGroup teacherGroup) {
        this.teacherGroup = teacherGroup;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Document> getAttachedDocuments() {
        return attachedDocuments;
    }

    public void setAttachedDocuments(List<Document> attachedDocuments) {
        this.attachedDocuments = attachedDocuments;
    }

    public String getCampName() {
        return camp.getName();
    }

    public String getChildName() {
        return children.get(0).getName();
    }

    /* Jó kérdés hogy ez a fizetési státusz vagy mi*/
    public String getStatus() {
        return applicationStatus.name();
    }

    public Date getStartDate() {
        return camp.getStartDate();
    }

    public Date getEndDate() {
        return camp.getEndDate();
    }

    public boolean isMedicalInfoProvided() {
        return medicalInfoProvided;
    }

    public void setMedicalInfoProvided(boolean medicalInfoProvided) {
        this.medicalInfoProvided = medicalInfoProvided;
    }

    public boolean isPaymentCompleted() {
        return paymentCompleted;
    }

    public void setPaymentCompleted(boolean paymentCompleted) {
        this.paymentCompleted = paymentCompleted;
    }


    // Jelentkezés státusz enum szöveges leírással
    public enum ApplicationStatus {
        FELDOLGOZAS_ALATT("Feldolgozás alatt"),
        ELFOGADVA("Elfogadva"),
        ELUTASITVA("Elutasítva"),
        VAROLISTARA_HELYEZVE("Várólistára helyezve");

        private final String displayName;

        ApplicationStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Fizetés státusz enum szöveges leírással
    public enum PaymentStatus {
        FIZETESRE_VAR("Fizetésre vár"),
        FIZETVE("Fizetve"),
        VISSZATERITES_FOLYAMATBAN("Visszatérítés folyamatban"),
        VISSZATERITVE("Visszatérítve");

        private final String displayName;

        PaymentStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
