package uz.pdp.back.controller.contracts;

import uz.pdp.back.payload.ApplicationDTO;

public interface ApplicationController {
    void sendApplication(ApplicationDTO applicationDTO);

    void acceptApplication();
}
