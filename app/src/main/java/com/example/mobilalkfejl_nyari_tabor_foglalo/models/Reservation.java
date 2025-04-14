package com.example.mobilalkfejl_nyari_tabor_foglalo.models;

import java.util.Date;
import java.util.List;

public class Reservation {
    private String reservationId;
    private Camp camp;
    private User applicant;
    private Date reservationDate;
    private Application.PaymentStatus paymentStatus;
    private double amountToPay;
    private List<User.Child> children;
    private User.TeacherGroup teacherGroup;
    private String note;
    private List<Document> attachedDocuments;
    // Alapértelmezett konstruktor (Firestore miatt szükséges)
    public Reservation() {
    }

    public Reservation(String reservationId, Camp camp, User applicant) {
        this.reservationId = reservationId;
        this.camp = camp;
        this.applicant = applicant;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public Camp getCamp() {
        return camp;
    }

    public void setCamp(Camp camp) {
        this.camp = camp;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Application.PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Application.PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public List<User.Child> getChildren() {
        return children;
    }

    public void setChildren(List<User.Child> children) {
        this.children = children;
    }

    public User.TeacherGroup getTeacherGroup() {
        return teacherGroup;
    }

    public void setTeacherGroup(User.TeacherGroup teacherGroup) {
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
}
