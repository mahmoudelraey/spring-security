package com.example.springsecurity.service;

import com.example.springsecurity.model.Course;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    List<Course> courses = new ArrayList<>();

    public CourseService() {
        courses.add(new Course(1l, "Ahmed"));
        courses.add(new Course(2l, "ali"));
        courses.add(new Course(3l, "mohamed"));
        courses.add(new Course(4l, "said"));
    }

    public void registerCourse(Course course) {
        Comparator<Course> courseComparator = Comparator.comparing(Course::getId);
        Optional<Course> maxCourseId = courses.stream().max(courseComparator);
        course.setId(maxCourseId.get().getId() + 1);
        courses.add(course);
    }

    public Course findCourse(Long courseId) {
        return courses.stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("course not found"));
    }

    public List<Course> findAllCourses() {
        return courses;
    }

    public void deleteCourse(Long courseId) {
        courses.removeIf(course -> course.getId().equals(courseId));
    }

    public void updateCourse(Course updateCourse) {
        courses.stream().forEach(course -> {
            if (course.getId().equals(updateCourse.getId())) {
                course.setName(updateCourse.getName());
            }
        });
    }
}
