package ro.tamadawines.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "subscribers")
@NamedQueries({
        @NamedQuery(
                name = "Subscriber.findAll",
                query = "SELECT s FROM Subscriber s"
        ),
        @NamedQuery(
                name = "Subscriber.findByEmail",
                query = "SELECT s FROM Subscriber s WHERE s.emailAddress LIKE :email"
        )
})
public class Subscriber {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String emailAddress;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "emailAddress", nullable = false, insertable = true, updatable = true, length = 511)
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscriber)) return false;
        Subscriber that = (Subscriber) o;
        return id == that.id && emailAddress.equals(that.emailAddress);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + emailAddress.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                '}';
    }
}
