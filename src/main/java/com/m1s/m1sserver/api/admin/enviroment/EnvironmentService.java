package com.m1s.m1sserver.api.admin.enviroment;


import com.m1s.m1sserver.utils.CustomException;
import com.m1s.m1sserver.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentService {
    @Autowired
    private EnvironmentRepository environmentRepository;

    public Environment getEnvironment(String name){
        if(!environmentRepository.existsByName(name))throw new CustomException(ErrorCode.ENVIRONMENT_NOT_FOUND);
        return environmentRepository.findByName(name);
    }
}
