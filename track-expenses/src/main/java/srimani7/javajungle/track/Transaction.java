package srimani7.javajungle.track;

import java.time.LocalDate;

public class Transaction {
    private LocalDate date;
    private double amount;
    private String note;
    private TransactionType transactionType;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
               "date=" + date +
               ", amount=" + amount +
               ", note='" + note + '\'' +
               ", transactionType=" + transactionType +
               '}';
    }
}
