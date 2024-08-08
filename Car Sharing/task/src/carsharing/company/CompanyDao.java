package carsharing.company;

import java.util.List;

public interface CompanyDao {
    Company getCompany(int id);
    List<Company> getCompanies();
    int addCompany(String companyName);
}
