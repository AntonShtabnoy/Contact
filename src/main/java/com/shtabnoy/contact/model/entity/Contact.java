package com.shtabnoy.contact.model.entity;

public class Contact {
    private String name;
    private String surname;
    private String date;
    private String dateFrom;
    private String toDate;
    private String address;
    private String workPlace;
    private String nationality;
    private String family_status;
    private String sex;
    private String webSite;
    private String email;
    private String image;
    private String country;
    private String city;
    private String street;
    private String home;
    private String index;
    private String status;
    private int id;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getFamily_status() {
        return family_status;
    }

    public void setFamily_status(String family_status) {
        this.family_status = family_status;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Contact() {
    }

    public Contact(String name, String surname, String date, String address, String workPlace, String nationality, String family_status, String sex, String webSite, String email, String image, String country, String city, String street, String home, String index, int id) {
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.address = address;
        this.workPlace = workPlace;
        this.nationality = nationality;
        this.family_status = family_status;
        this.sex = sex;
        this.webSite = webSite;
        this.email = email;
        this.image = image;
        this.country = country;
        this.city = city;
        this.street = street;
        this.home = home;
        this.index = index;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return country+" "+city+" "+street+" "+home;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", date='" + date + '\'' +
                ", address='" + address + '\'' +
                ", workPlace='" + workPlace + '\'' +
                ", nationality='" + nationality + '\'' +
                ", family_status='" + family_status + '\'' +
                ", sex='" + sex + '\'' +
                ", webSite='" + webSite + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", home='" + home + '\'' +
                ", index='" + index + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (name != null ? !name.equals(contact.name) : contact.name != null) return false;
        if (date != null ? !date.equals(contact.date) : contact.date != null) return false;
        if (address != null ? !address.equals(contact.address) : contact.address != null) return false;
        return workPlace != null ? workPlace.equals(contact.workPlace) : contact.workPlace == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (workPlace != null ? workPlace.hashCode() : 0);
        return result;
    }
}