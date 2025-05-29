package com.ead.course.service.impl;

import com.ead.course.repositories.ModuleRepository;
import com.ead.course.service.ModuleService;

public class ModuleServiceImpl implements ModuleService {

    private ModuleRepository moduleRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }
}
