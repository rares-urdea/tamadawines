package ro.tamadawines.persistence.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Rares Urdea
 */
@Entity
@Table(name = "images")
@NamedQueries({
        @NamedQuery(
                name = "Image.findAll",
                query = "SELECT i FROM Image i"
        ),
        @NamedQuery(
                name = "Image.findByName",
                query = "Select i FROM Image i WHERE i.name LIKE :name"
        ),
        @NamedQuery(
                name = "User.findByUrl",
                query = "Select i FROM Image i WHERE i.url LIKE :url"
        )
})
public class Image {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String url;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "url", nullable = false, insertable = true, updatable = true, length = 511)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
