package ro.tamadawines.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "counters")
@NamedQueries({
        @NamedQuery(
                name = "Counter.findAll",
                query = "SELECT c FROM Counter c"
        ),
        @NamedQuery(
                name = "Counter.findByName",
                query = "SELECT c FROM Counter c WHERE c.name LIKE :nm"
        ),
        @NamedQuery(
                name = "Counter.increment",
                query = "UPDATE Counter c SET c.value = c.value + 1 WHERE c.name LIKE :nm"
        )
})
public class Counter {

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

    private Long value;

    @javax.persistence.Column(name = "value", nullable = false, insertable = true, updatable = true)
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public enum COUNTER_NAME {
        LISTINGS("list_calls"),
        SELL_SUCCESS("sell_calls_success"),
        SELL_FAILURE("sell_calls_failed");

        private String name;

        public String string() {
            return name;
        }

        COUNTER_NAME(String name) {
            this.name = name;
        }
    }
}
