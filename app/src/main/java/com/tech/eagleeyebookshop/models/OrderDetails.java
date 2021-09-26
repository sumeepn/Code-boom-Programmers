package com.tech.eagleeyebookshop.models;

public class OrderDetails {

    String bookId;
    String bookName;
    String price;
    String qty;
    String userName;
    String nic;
    String Contact;
    String address;

    public OrderDetails() {
    }

    public OrderDetails(String bookId, String bookName, String price, String qty, String userName, String nic, String contact, String address) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.price = price;
        this.qty = qty;
        this.userName = userName;
        this.nic = nic;
        Contact = contact;
        this.address = address;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
