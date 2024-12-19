package com.springboot.security.spring_security.controller;

import com.springboot.security.spring_security.entity.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/student-center")
public class StudentController {

    static List<Student> classRoom= Arrays.asList(new Student(101,"Kunal","science",19),
                                               new Student(102,"Mohit","Arts",20),
                                               new Student(103,"Kamal","Arts",21),
                                               new Student(104,"Mangesh","commerce",19));
   
    @GetMapping(path = "/search-by-id/{studId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudentById(@PathVariable("studId") Integer studentId){
             return new ResponseEntity<Student>(classRoom.stream().filter(student->
                 studentId.equals(student.getStudentId())).
                     findFirst().
                     orElseThrow(()-> new IllegalArgumentException("Student not Found with id : "+studentId)) , HttpStatus.FOUND);
    }
}
