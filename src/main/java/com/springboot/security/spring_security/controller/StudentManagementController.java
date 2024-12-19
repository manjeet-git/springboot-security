package com.springboot.security.spring_security.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.security.spring_security.entity.Student;

@RestController
@RequestMapping("management/api/v1/student-center")
public class StudentManagementController {

	static List<Student> classRoom = Arrays.asList(new Student(101, "Kunal", "science", 19),
			new Student(102, "Mohit", "Arts", 20), new Student(103, "Kamal", "Arts", 21),
			new Student(104, "Mangesh", "commerce", 19));
	
	
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ADMINTRAINEE')")
	public List<Student> getAllStudent(){
		System.out.println("getAllStudent : ");
		return classRoom;
	}
	
	@PostMapping()
	@PreAuthorize("hasAuthority('student:write')")
	public void registerStudent(@RequestBody Student student){
		System.out.println("registerStudent : ");
		System.out.println(student);
	}
	
	@DeleteMapping("{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public void deleteStudent(@PathVariable Integer studentId) {
		System.out.println("deleteStudent : ");
		System.out.println("Deleted the student having Id : "+studentId);
	}
	
	@PutMapping("{studentId}")
	@PreAuthorize("hasAuthority('student:write')")
	public Student updateStudent(Integer studentId, @RequestBody Student student) {
		System.out.println("updateStudent : ");
		System.out.println("update the student having id "+studentId);
		return student;
	}

}
