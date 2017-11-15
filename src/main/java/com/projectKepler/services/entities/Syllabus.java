/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services.entities;

/**
 *
 * @author blackphantom
 */
public class Syllabus {
    private String programa;
    private int version,totalCredits, creditsSemester;
    private CourseStudent[] courses;

    public Syllabus(String programa, int version, int totalCredits, int creditsSemester, CourseStudent[] courses) {
        this.programa = programa;
        this.version = version;
        this.totalCredits = totalCredits;
        this.creditsSemester = creditsSemester;
        this.courses = courses;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public int getCreditsSemester() {
        return creditsSemester;
    }

    public CourseStudent[] getCourses() {
        return courses;
    }

    
}
