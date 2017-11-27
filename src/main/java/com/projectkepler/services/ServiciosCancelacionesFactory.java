/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectkepler.services;

import com.google.inject.Injector;
import static com.google.inject.Guice.createInjector;
import com.projectkepler.persistence.EstudianteDAO;
import com.projectkepler.persistence.SolicitudDAO;
import com.projectkepler.persistence.UniversidadDAO;
import com.projectkepler.persistence.mybatis.SolicitudDAOMyBatis;
import com.projectkepler.persistence.mybatis.UniversidadDAOMyBatis;
import com.projectkepler.persistence.mybatis.EstudianteDAOMyBatis;
import com.projectkepler.services.algorithm.Algorithm;
import com.projectkepler.services.algorithm.impl.AdavancedAlgorithm;
import com.projectkepler.services.graphRectificator.GraphRectificator;
import com.projectkepler.services.graphRectificator.impl.GraphRectificatorImpl;
import com.projectkepler.services.impl.ServiciosCancelacionesImpl;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;

/**
 *
 * @author danielagonzalez
 */
public class ServiciosCancelacionesFactory {
    
    private static ServiciosCancelacionesFactory instance = new ServiciosCancelacionesFactory();
    private static Injector injector;
    private static Injector testInjector;
    
    public ServiciosCancelacionesFactory (){
        injector = createInjector(new XMLMyBatisModule() {
            
            @Override
            protected void initialize(){
                install(JdbcHelper.PostgreSQL);              
                setClassPathResource("mybatis-config.xml");
                bind(ServiciosCancelaciones.class).to(ServiciosCancelacionesImpl.class);
                bind(EstudianteDAO.class).to(EstudianteDAOMyBatis.class);
                bind(SolicitudDAO.class).to(SolicitudDAOMyBatis.class);
                bind(UniversidadDAO.class).to(UniversidadDAOMyBatis.class);
                bind(Algorithm.class).to(AdavancedAlgorithm.class);
                bind(GraphRectificator.class).to(GraphRectificatorImpl.class);
            }
        });
        
        testInjector = createInjector(new XMLMyBatisModule() {
            
            @Override
            protected void initialize() {
                install(JdbcHelper.PostgreSQL);             
                setClassPathResource("mybatis-config-h2.xml");
                bind(ServiciosCancelaciones.class).to(ServiciosCancelacionesImpl.class);
                bind(EstudianteDAO.class).to(EstudianteDAOMyBatis.class);
                bind(SolicitudDAO.class).to(SolicitudDAOMyBatis.class);
                bind(UniversidadDAO.class).to(UniversidadDAOMyBatis.class);
                bind(Algorithm.class).to(AdavancedAlgorithm.class);
                bind(GraphRectificator.class).to(GraphRectificatorImpl.class);
            }
        });
    }
    
    public ServiciosCancelaciones getServiciosCancelaciones(){
        return injector.getInstance(ServiciosCancelaciones.class);
    }
    
    public static ServiciosCancelacionesFactory getInstance(){
        return instance;
    }
    
    public ServiciosCancelaciones getTestingServiciosCancelaciones() {
        return testInjector.getInstance(ServiciosCancelaciones.class);
    }
}
