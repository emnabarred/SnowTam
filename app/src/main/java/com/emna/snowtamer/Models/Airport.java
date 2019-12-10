package com.emna.snowtamer.Models;

public class Airport {
    private String icao;
    private String name;
    private String country;
    private String lat;
    private String lon;
    private String urlimage;

    //constru vide
    public Airport() {
    }

    //constru name
    public Airport(String name) {
        this.name = name;
    }

    //constru param
    public Airport(String icao, String name, String country, String lat, String lon, String urlimage) {
        this.icao = icao;
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.urlimage = urlimage;
    }

    //getters
    public String getIcao() {
        return icao;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getUrlimage() {


        return urlimage;
    }

    //setters
    public void setIcao(String icao) {
        this.icao = icao;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setUrlimage(String urlimage) {
        this.urlimage = urlimage;
    }
}
