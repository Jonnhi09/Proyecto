/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectKepler.services;

import com.google.inject.Injector;
import static com.google.inject.Guice.createInjector;
import com.projectKepler.persistence.EstudianteDAO;
import com.projectKepler.persistence.mybatis.EstudianteDAOMyBatis;
import com.projectKepler.services.algorithm.Algorithm;
import com.projectKepler.services.algorithm.impl.SimpleAlgorithm;
import com.projectKepler.services.graphRectificator.GraphRectificator;
import com.projectKepler.services.graphRectificator.impl.GraphRectificatorImpl;
import com.projectKepler.services.impl.ServiciosCancelacionesImpl;
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
                bind(Algorithm.class).to(SimpleAlgorithm.class);
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
                bind(Algorithm.class).to(SimpleAlgorithm.class);
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
