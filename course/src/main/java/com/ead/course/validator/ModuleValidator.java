package com.ead.course.validator;

import com.ead.course.domain.Module;
import com.ead.course.domain.dto.request.ModuleRequest;
import com.ead.course.exception.BusinessException;
import com.ead.course.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ModuleValidator {

    @Autowired
    private ModuleRepository repository;

    @Autowired
    private MessageSource messageSource;

    public void validTitleAndDescriptionAlreadyExists(Module module) {
        if(repository.existsByTitle(module.getTitle()) && repository.existsByDescription(module.getDescription())){
            throw new BusinessException(messageSource.getMessage("module-already-exists", null, LocaleContextHolder.getLocale()));
        }
    }

    public void validTitleAndDescriptionAlreadyExists(ModuleRequest moduleRequest) {
        if(repository.existsByTitle(moduleRequest.getTitle()) && repository.existsByDescription(moduleRequest.getDescription())){
            throw new BusinessException(messageSource.getMessage("module-already-exists", null, LocaleContextHolder.getLocale()));
        }
    }

}
