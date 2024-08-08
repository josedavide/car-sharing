package carsharing.car;

public class Car {
    private int id;
    private String Name;
    private int companyId;

    public Car(int id, String name, int companyId) {
        this.id = id;
        this.Name = name;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public int getCompanyId() {
        return companyId;
    }
    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
