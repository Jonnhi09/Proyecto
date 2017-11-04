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
    private int version;
    private Course[] courses;

    public Syllabus(String programa, int version, Course[] courses) {
        this.programa = programa;
        this.version = version;
        this.courses = courses;
    }

    public Course[] getCourses() {
        return courses;
    }

    public String getPrograma() {
        return programa;
    }

    public int getVersion() {
        return version;
    }
    
}
