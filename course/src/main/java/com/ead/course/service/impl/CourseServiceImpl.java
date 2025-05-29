package com.ead.course.service.impl;

import com.ead.course.repositories.CourseRepository;
import com.ead.course.service.CourseService;

public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
}
