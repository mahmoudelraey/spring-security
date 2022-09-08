package com.example.springsecurity.controller;

import com.example.springsecurity.model.Student;
import com.example.springsecurity.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registerStudent(@RequestBody Student student) {
        studentService.registerStudent(student);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('student:read')")
    public List<Student> findAllStudent() {
        return studentService.findAllStudents();
    }

    @GetMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:read')")
    public Student findStudent(@PathVariable("studentId") Long studentId) {
        return studentService.findStudent(studentId);
    }

    @DeleteMapping("{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void updateStudent(@RequestBody Student student) {
        studentService.updateStudent(student);
    }
}
