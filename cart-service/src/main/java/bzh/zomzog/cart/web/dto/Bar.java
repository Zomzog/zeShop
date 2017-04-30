package bzh.zomzog.cart.web.dto;

public class Bar {
    private long   id;
    private String name;

    public Bar() {
        super();
    }

    public Bar(final long id, final String name) {
        super();

        this.id = id;
        this.name = name;
    }

    //

    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}