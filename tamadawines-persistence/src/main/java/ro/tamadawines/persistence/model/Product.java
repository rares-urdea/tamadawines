package ro.tamadawines.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(
                name = "Product.findAll",
                query = "SELECT p FROM Product p"
        ),
        @NamedQuery(
                name = "Product.findByName",
                query = "Select p FROM Product p WHERE p.name LIKE :nm"
        ),
        @NamedQuery(
                name = "Product.delete",
                query = "DELETE FROM Product p WHERE p.id = :id"
        )
})
public class Product {
    private int id;

    @Id
    @javax.persistence.Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    @Basic
    @javax.persistence.Column(name = "name", nullable = false, insertable = true, updatable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String subtitle;

    @Basic
    @javax.persistence.Column(name = "subtitle", nullable = false, insertable = true, updatable = true, length = 500)
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    private String producer;

    @Basic
    @javax.persistence.Column(name = "producer", nullable = false, insertable = true, updatable = true, length = 50)
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    private String appellation;

    @Basic
    @javax.persistence.Column(name = "appellation", nullable = false, insertable = true, updatable = true, length = 50)
    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    private String grapeVariety;

    @Basic
    @javax.persistence.Column(name = "grapeVariety", nullable = true, insertable = true, updatable = true, length = 100)
    public String getGrapeVariety() {
        return grapeVariety;
    }

    public void setGrapeVariety(String grapeVariety) {
        this.grapeVariety = grapeVariety;
    }

    private Integer year;

    @Basic
    @javax.persistence.Column(name = "year", nullable = true, insertable = true, updatable = true)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    private String colour;

    @Basic
    @javax.persistence.Column(name = "colour", nullable = false, insertable = true, updatable = true, length = 50)
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    private String taste;

    @Basic
    @javax.persistence.Column(name = "taste", nullable = true, insertable = true, updatable = true, length = 255)
    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    private int bottleSize;

    @Basic
    @javax.persistence.Column(name = "bottleSize", nullable = false, insertable = true, updatable = true)
    public int getBottleSize() {
        return bottleSize;
    }

    public void setBottleSize(int bottleSize) {
        this.bottleSize = bottleSize;
    }

    private float alcohol;

    @Basic
    @javax.persistence.Column(name = "alcohol", nullable = false, insertable = true, updatable = true, precision = 0)
    public float getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(float alcohol) {
        this.alcohol = alcohol;
    }

    private float price;

    @Basic
    @javax.persistence.Column(name = "price", nullable = false, insertable = true, updatable = true, precision = 0)
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    private Float oldPrice;

    @Basic
    @javax.persistence.Column(name = "oldPrice", nullable = true, insertable = true, updatable = true, precision = 0)
    public Float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Float oldPrice) {
        this.oldPrice = oldPrice;
    }

    private int stock;

    @Basic
    @javax.persistence.Column(name = "stock", nullable = false, insertable = true, updatable = true)
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    private int discount;

    @Basic
    @javax.persistence.Column(name = "discount", nullable = false, insertable = true, updatable = true)
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    private String prizesWon;

    @Basic
    @javax.persistence.Column(name = "prizesWon", nullable = false, insertable = true, updatable = true, length = 500)
    public String getPrizesWon() {
        return prizesWon;
    }

    public void setPrizesWon(String prizesWon) {
        this.prizesWon = prizesWon;
    }

    private String image;

    @Basic
    @javax.persistence.Column(name = "image", nullable = false, insertable = true, updatable = true, length = 100)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String thumb;

    @Basic
    @javax.persistence.Column(name = "thumb", nullable = false, insertable = true, updatable = true, length = 100)
    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    private String description;

    @Basic
    @javax.persistence.Column(name = "description", nullable = false, insertable = true, updatable = true, length = 1500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String tasteNotes;

    @Basic
    @javax.persistence.Column(name = "tasteNotes", nullable = false, insertable = true, updatable = true, length = 700)
    public String getTasteNotes() {
        return tasteNotes;
    }

    public void setTasteNotes(String tasteNotes) {
        this.tasteNotes = tasteNotes;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", producer='" + producer + '\'' +
                ", appellation='" + appellation + '\'' +
                ", grapeVariety='" + grapeVariety + '\'' +
                ", year=" + year +
                ", colour='" + colour + '\'' +
                ", taste='" + taste + '\'' +
                ", bottleSize=" + bottleSize +
                ", alcohol=" + alcohol +
                ", price=" + price +
                ", oldPrice=" + oldPrice +
                ", stock=" + stock +
                ", discount=" + discount +
                ", prizesWon='" + prizesWon + '\'' +
                ", image='" + image + '\'' +
                ", thumb='" + thumb + '\'' +
                ", description='" + description + '\'' +
                ", tasteNotes='" + tasteNotes + '\'' +
                '}';
    }
}
