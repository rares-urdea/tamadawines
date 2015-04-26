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
        )
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String taste;
    private String color;
    private String grapeVariety;
    private String region;
    private Short year;
    private Short quantity;
    private Short stock;
    private Float price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "taste", nullable = false, insertable = true, updatable = true, length = 50)
    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    @Basic
    @Column(name = "color", nullable = false, insertable = true, updatable = true, length = 50)
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Basic
    @Column(name = "grapeVariety", nullable = true, insertable = true, updatable = true, length = 50)
    public String getGrapeVariety() {
        return grapeVariety;
    }

    public void setGrapeVariety(String grapeVariety) {
        this.grapeVariety = grapeVariety;
    }

    @Basic
    @Column(name = "region", nullable = true, insertable = true, updatable = true, length = 50)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Basic
    @Column(name = "year", nullable = true, insertable = true, updatable = true)
    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    @Basic
    @Column(name = "quantity", nullable = true, insertable = true, updatable = true)
    public Short getQuantity() {
        return quantity;
    }

    public void setQuantity(Short quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "stock", nullable = true, insertable = true, updatable = true)
    public Short getStock() {
        return stock;
    }

    public void setStock(Short stock) {
        this.stock = stock;
    }

    @Basic
    @Column(name = "price", nullable = true, insertable = true, updatable = true, precision = 0)
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (color != null ? !color.equals(product.color) : product.color != null) return false;
        if (grapeVariety != null ? !grapeVariety.equals(product.grapeVariety) : product.grapeVariety != null)
            return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        if (price != null ? !price.equals(product.price) : product.price != null) return false;
        if (quantity != null ? !quantity.equals(product.quantity) : product.quantity != null) return false;
        if (region != null ? !region.equals(product.region) : product.region != null) return false;
        if (stock != null ? !stock.equals(product.stock) : product.stock != null) return false;
        if (taste != null ? !taste.equals(product.taste) : product.taste != null) return false;
        if (year != null ? !year.equals(product.year) : product.year != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (taste != null ? taste.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (grapeVariety != null ? grapeVariety.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (stock != null ? stock.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
