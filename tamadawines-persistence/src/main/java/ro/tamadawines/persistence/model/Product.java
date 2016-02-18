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

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String subtitle;
    private String producer;
    private String appellation;
    private String grapeVariety;
    private Integer year;
    private String colour;
    private String taste;
    private int bottleSize;
    private float price;
    private float alcohol;
    private Float oldPrice;
    private int stock;
    private int discount;
    private String prizesWon;
    private String thumb;
    private String description;
    private String tasteNotes;
    private Image image;

    @Id
    @javax.persistence.Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @javax.persistence.Column(name = "name", nullable = false, insertable = true, updatable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @javax.persistence.Column(name = "subtitle", nullable = false, insertable = true, updatable = true, length = 500)
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Basic
    @javax.persistence.Column(name = "producer", nullable = false, insertable = true, updatable = true, length = 50)
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }


    @Basic
    @javax.persistence.Column(name = "appellation", nullable = false, insertable = true, updatable = true, length = 50)
    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    @Basic
    @javax.persistence.Column(name = "grapeVariety", nullable = true, insertable = true, updatable = true, length = 100)
    public String getGrapeVariety() {
        return grapeVariety;
    }

    public void setGrapeVariety(String grapeVariety) {
        this.grapeVariety = grapeVariety;
    }

    @Basic
    @javax.persistence.Column(name = "year", nullable = true, insertable = true, updatable = true)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Basic
    @javax.persistence.Column(name = "colour", nullable = false, insertable = true, updatable = true, length = 50)
    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Basic
    @javax.persistence.Column(name = "taste", nullable = true, insertable = true, updatable = true, length = 255)
    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    @Basic
    @javax.persistence.Column(name = "bottleSize", nullable = false, insertable = true, updatable = true)
    public int getBottleSize() {
        return bottleSize;
    }

    public void setBottleSize(int bottleSize) {
        this.bottleSize = bottleSize;
    }

    @Basic
    @javax.persistence.Column(name = "alcohol", nullable = false, insertable = true, updatable = true, precision = 0)
    public float getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(float alcohol) {
        this.alcohol = alcohol;
    }

    @Basic
    @javax.persistence.Column(name = "price", nullable = false, insertable = true, updatable = true, precision = 0)
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Basic
    @javax.persistence.Column(name = "oldPrice", nullable = true, insertable = true, updatable = true, precision = 0)
    public Float getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(Float oldPrice) {
        this.oldPrice = oldPrice;
    }

    @Basic
    @javax.persistence.Column(name = "stock", nullable = false, insertable = true, updatable = true)
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Basic
    @javax.persistence.Column(name = "discount", nullable = false, insertable = true, updatable = true)
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Basic
    @javax.persistence.Column(name = "prizesWon", nullable = false, insertable = true, updatable = true, length = 500)
    public String getPrizesWon() {
        return prizesWon;
    }

    public void setPrizesWon(String prizesWon) {
        this.prizesWon = prizesWon;
    }

    @Basic
    @javax.persistence.Column(name = "thumb", nullable = false, insertable = true, updatable = true, length = 100)
    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    @Basic
    @javax.persistence.Column(name = "description", nullable = false, insertable = true, updatable = true, length = 1500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @javax.persistence.Column(name = "tasteNotes", nullable = false, insertable = true, updatable = true, length = 700)
    public String getTasteNotes() {
        return tasteNotes;
    }

    public void setTasteNotes(String tasteNotes) {
        this.tasteNotes = tasteNotes;
    }

    @OneToOne(cascade = CascadeType.ALL, targetEntity = Image.class)
    @JoinColumn(name = "imageId", referencedColumnName = "id")
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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
                ", thumb='" + thumb + '\'' +
                ", description='" + description + '\'' +
                ", tasteNotes='" + tasteNotes + '\'' +
                '}';
    }
}
