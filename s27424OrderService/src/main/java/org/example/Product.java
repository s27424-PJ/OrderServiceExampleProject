package org.example;

public class Product {
    private int id;
    private String name;
    private int ilosc;
    private final double price;

    public Product(int id, String name, int ilosc, double price) {
        if (ilosc <= 0) {
            throw new IllegalArgumentException("Produkt nie moze miec minusowej ilosci");
        }
        this.id = id;
        this.name = name;
        this.ilosc = ilosc;
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public int getIlosc() {
        return ilosc;
    }

    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ilosc='" + ilosc + '\'' +
                ", cena=" + price +
                '}';
    }
}
