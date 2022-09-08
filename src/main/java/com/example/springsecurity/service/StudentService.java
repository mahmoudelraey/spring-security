package com.example.springsecurity.service;

import com.example.springsecurity.model.Student;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    List<Student> students = new ArrayList<>();

    public StudentService() {
        students.add(new Student(1l, "Ahmed"));
        students.add(new Student(2l, "ali"));
        students.add(new Student(3l, "mohamed"));
        students.add(new Student(4l, "said"));
    }

    public void registerStudent(Student student) {
        Comparator<Student> studentComparator = Comparator.comparing(Student::getId);
        Optional<Student> maxStudentId = students.stream().max(studentComparator);
        student.setId(maxStudentId.get().getId() + 1);
        students.add(student);
    }

    public Student findStudent(Long studentId) {
        return students.stream()
                .filter(student -> student.getId().equals(studentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("student not found"));
    }

    public List<Student> findAllStudents() {
        return students;
    }

    public void deleteStudent(Long studentId) {
        students.removeIf(student -> student.getId().equals(studentId));
    }

    public void updateStudent(Student updateStudent) {
        students.stream().forEach(student -> {
            if (student.getId().equals(updateStudent.getId())) {
                student.setName(updateStudent.getName());
            }
        });
    }
}
