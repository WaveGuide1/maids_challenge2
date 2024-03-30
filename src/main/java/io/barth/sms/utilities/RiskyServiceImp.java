package io.barth.sms.utilities;

import io.barth.sms.exception.CustomException;
import org.springframework.stereotype.Service;

@Service
public class RiskyServiceImp implements RiskyService {

    @Override
    public void forbidden() {
        throw new CustomException("Something went wrong");
    }
}
