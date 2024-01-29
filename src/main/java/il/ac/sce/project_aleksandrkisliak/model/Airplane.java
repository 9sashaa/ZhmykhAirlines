package il.ac.sce.project_aleksandrkisliak.model;

public class Airplane {
    private int idairplane;
    private String model;
    private int capacity;

    public Airplane(String model, int capacity) {
        this.model = model;
        this.capacity = capacity;
    }

    public Airplane(int idairplane, String model, int capacity) {
        this.idairplane = idairplane;
        this.model = model;
        this.capacity = capacity;
    }

    public int getIdairplane() {
        return idairplane;
    }

    public void setIdairplane(int idairplane) {
        this.idairplane = idairplane;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Airplane{" +
                "idairplane=" + idairplane +
                ", model='" + model + '\'' +
                ", capacity=" + capacity +
                '}';
    }
}
