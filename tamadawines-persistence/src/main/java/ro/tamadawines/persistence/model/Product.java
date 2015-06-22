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

    private byte featured;

    @Basic
    @javax.persistence.Column(name = "featured", nullable = false, insertable = true, updatable = true)
    public byte getFeatured() {
        return featured;
    }

    public void setFeatured(byte featured) {
        this.featured = featured;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (Float.compare(product.alcohol, alcohol) != 0) return false;
        if (bottleSize != product.bottleSize) return false;
        if (discount != product.discount) return false;
        if (featured != product.featured) return false;
        if (id != product.id) return false;
        if (Float.compare(product.price, price) != 0) return false;
        if (stock != product.stock) return false;
        if (appellation != null ? !appellation.equals(product.appellation) : product.appellation != null)
            return false;
        if (colour != null ? !colour.equals(product.colour) : product.colour != null) return false;
        if (description != null ? !description.equals(product.description) : product.description != null)
            return false;
        if (grapeVariety != null ? !grapeVariety.equals(product.grapeVariety) : product.grapeVariety != null)
            return false;
        if (image != null ? !image.equals(product.image) : product.image != null) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (prizesWon != null ? !prizesWon.equals(product.prizesWon) : product.prizesWon != null) return false;
        if (producer != null ? !producer.equals(product.producer) : product.producer != null) return false;
        if (subtitle != null ? !subtitle.equals(product.subtitle) : product.subtitle != null) return false;
        if (taste != null ? !taste.equals(product.taste) : product.taste != null) return false;
        if (tasteNotes != null ? !tasteNotes.equals(product.tasteNotes) : product.tasteNotes != null) return false;
        if (thumb != null ? !thumb.equals(product.thumb) : product.thumb != null) return false;
        if (year != null ? !year.equals(product.year) : product.year != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (subtitle != null ? subtitle.hashCode() : 0);
        result = 31 * result + (producer != null ? producer.hashCode() : 0);
        result = 31 * result + (appellation != null ? appellation.hashCode() : 0);
        result = 31 * result + (grapeVariety != null ? grapeVariety.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (colour != null ? colour.hashCode() : 0);
        result = 31 * result + (taste != null ? taste.hashCode() : 0);
        result = 31 * result + bottleSize;
        result = 31 * result + (alcohol != +0.0f ? Float.floatToIntBits(alcohol) : 0);
        result = 31 * result + (price != +0.0f ? Float.floatToIntBits(price) : 0);
        result = 31 * result + stock;
        result = 31 * result + discount;
        result = 31 * result + (int) featured;
        result = 31 * result + (prizesWon != null ? prizesWon.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (thumb != null ? thumb.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (tasteNotes != null ? tasteNotes.hashCode() : 0);
        return result;
    }
}
