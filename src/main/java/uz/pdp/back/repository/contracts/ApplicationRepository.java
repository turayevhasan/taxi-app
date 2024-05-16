package uz.pdp.back.repository.contracts;

import uz.pdp.back.model.Application;

import java.util.List;

public interface ApplicationRepository {
    void save(Application application);
    List<Application> getAllApplications();

    void updateApplications(List<Application> applications);
}
