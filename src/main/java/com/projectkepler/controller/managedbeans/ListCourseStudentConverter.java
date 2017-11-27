/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.controller.managedbeans;

import com.projectkepler.services.entities.CourseStudent;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author dxmortem
 */
@FacesConverter(value="CourseStudentConverter")
public class ListCourseStudentConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        List<CourseStudent> cursos = (List<CourseStudent>) fc.getApplication().evaluateExpressionGet(fc, "#{solCancelBean.materias}", List.class);
        
        for (CourseStudent c : cursos) {
            if (c.getNemonico().equals(string)) {
                return c;
            }
        }
        
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return ((CourseStudent) o).getNemonico();
    }

    

    
    
}
