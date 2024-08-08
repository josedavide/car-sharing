package carsharing.car;

import java.util.List;

public interface CarDao {
    Car getCar(int id);
    int addCar(int companyId, String carName);
    List<Car> getCarsByCompanyId(int companyId);
    List<Car> getAvailableCarsByCompanyId(int companyId);
}
