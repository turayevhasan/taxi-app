package uz.pdp.back.repository;

import lombok.Getter;
import uz.pdp.back.model.Application;
import uz.pdp.back.repository.contracts.ApplicationRepository;

import java.util.List;

public class ApplicationRepositoryImpl implements ApplicationRepository {
    @Getter
    private static final ApplicationRepository instance = new ApplicationRepositoryImpl();

    private ApplicationRepositoryImpl() {
    }

    private static final DataSaverGetter<Application> dataSaverGetter = new DataSaverGetter<>();
    private static final String applicationDataUrl = "database/application_data.txt";

    @Override
    public void save(Application application) {
        List<Application> allApplications = dataSaverGetter.getAllData(applicationDataUrl);
        allApplications.add(application);
        dataSaverGetter.writeDataToFile(allApplications, applicationDataUrl);
    }

    @Override
    public List<Application> getAllApplications() {
        return dataSaverGetter.getAllData(applicationDataUrl);
    }

    @Override
    public void updateApplications(List<Application> applications) {
        dataSaverGetter.writeDataToFile(applications, applicationDataUrl);
    }
}
