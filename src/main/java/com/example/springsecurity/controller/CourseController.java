package com.example.springsecurity.controller;

import com.example.springsecurity.model.Course;
import com.example.springsecurity.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @PreAuthorize("hasAuthority('course:write')")
    public void registerCourse(@RequestBody Course course) {
        courseService.registerCourse(course);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('course:read')")
    public List<Course> findAllCourse() {
        return courseService.findAllCourses();
    }

    @GetMapping("{courseId}")
    @PreAuthorize("hasAuthority('course:read')")
    public Course findCourse(@PathVariable("courseId") Long courseId) {
        return courseService.findCourse(courseId);
    }

    @DeleteMapping("{courseId}")
    @PreAuthorize("hasAuthority('course:write')")
    public void deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('course:write')")
    public void updateCourse(@RequestBody Course course) {
        courseService.updateCourse(course);
    }
}
