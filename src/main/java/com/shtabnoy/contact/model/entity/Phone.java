package com.shtabnoy.contact.model.entity;

public class Phone {
    private int id;
    private int contactId;
    private String number;
    private String type;
    private String comment;


    public Phone(int id, String number, String type, String comment) {
        this.id = id;
        this.number = number;
        this.type = type;
        this.comment = comment;
    }

    public Phone() {
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone phone = (Phone) o;

        if (id != phone.id) return false;
        if (contactId != phone.contactId) return false;
        if (number != null ? !number.equals(phone.number) : phone.number != null) return false;
        if (type != null ? !type.equals(phone.type) : phone.type != null) return false;
        return comment != null ? comment.equals(phone.comment) : phone.comment == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + contactId;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", contactId=" + contactId +
                ", number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
