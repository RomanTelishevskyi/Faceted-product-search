package amazonlike.search.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categories", schema = "public")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    int id;

    String name;

    public Categories() {
    }

    public Categories(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
