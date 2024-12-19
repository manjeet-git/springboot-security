package com.springboot.security.spring_security.entity;

import java.io.Serializable;

public class Student implements Serializable {

    private final Integer studentId;
   private final String studentName;
    private  final String course;
    private  final Integer age;

    public Student(Integer studentId, String studentName, String course, Integer age) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.course = course;
        this.age = age;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourse() {
        return course;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", course='" + course + '\'' +
                ", age=" + age +
                '}';
    }
}
