/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services.entities;

/**
 *
 * @author blackphantom
 */
public class Syllabus {
    private String programa;
    private int version,totalCredits;
    private CourseStudent[] courses;

    public Syllabus(String programa, int version, int totalCredits, CourseStudent[] courses) {
        this.programa = programa;
        this.version = version;
        this.totalCredits = totalCredits;
        this.courses = courses;
    }

    public int getTotalCredits() {
        totalCredits=0;
        for (CourseStudent c: courses){
            totalCredits+=c.getCreditos();
        }
        return totalCredits;
    }


    public CourseStudent[] getCourses() {
        return courses;
    }

    public String getPrograma() {
        return programa;
    }

    public int getVersion() {
        return version;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public void setCourses(CourseStudent[] courses) {
        this.courses = courses;
    }

    
}
