/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services;

import com.google.inject.Injector;
import static com.google.inject.Guice.createInjector;
import com.projectKepler.persistence.AcudienteDAO;
import com.projectKepler.persistence.ConsejeroAcademicoDAO;
import com.projectKepler.persistence.CoordinadorCancelacionesDAO;
import com.projectKepler.persistence.EstudianteDAO;
import com.projectKepler.persistence.mybatis.AcudienteDAOMyBatis;
import com.projectKepler.persistence.mybatis.ConsejeroAcademicoDAOMyBatis;
import com.projectKepler.persistence.mybatis.CoordinadorCancelacionesDAOMyBatis;
import com.projectKepler.persistence.mybatis.EstudianteDAOMyBatis;
import com.projectKepler.services.impl.ServiciosCancelacionesImpl;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

/**
 *
 * @author danielagonzalez
 */
public class ServicioCancelacionesFactory {
    
    private static ServicioCancelacionesFactory instance = new ServicioCancelacionesFactory();
    private static Injector injector;
    
    public ServicioCancelacionesFactory (){
        injector = createInjector(new XMLMyBatisModule() {
            
            @Override
            protected void initialize(){
                install(JdbcHelper.MySQL);              
                setClassPathResource("mybatis-config.xml");
                bind(ServiciosCancelaciones.class).to(ServiciosCancelacionesImpl.class);
                bind(ConsejeroAcademicoDAO.class).to(ConsejeroAcademicoDAOMyBatis.class);                
                bind(CoordinadorCancelacionesDAO.class).to(CoordinadorCancelacionesDAOMyBatis.class);
                bind(AcudienteDAO.class).to(AcudienteDAOMyBatis.class);
                bind(EstudianteDAO.class).to(EstudianteDAOMyBatis.class);
            }
        });
    }
    
    public ServiciosCancelaciones getServiciosCancelaciones(){
        return injector.getInstance(ServiciosCancelaciones.class);
    }
    
    public static ServicioCancelacionesFactory getInstance(){
        return instance;
    }
}
