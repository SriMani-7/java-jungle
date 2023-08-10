package srimani7.javajungle.track;

public class Transaction {
    String date;
    double amount;
    String note;
    String transactionType;

    public static final String DATE_REGEX = "^(0?[1-9]|[12]\\d|3[01])\\/(0?[1-9]|1[0-2])\\/([1-9]\\d{3})$";
}
