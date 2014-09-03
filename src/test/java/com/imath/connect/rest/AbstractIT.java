package com.imath.connect.rest;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;

import com.imath.connect.data.InstanceDB;
import com.imath.connect.data.MainDB;
import com.imath.connect.data.ProjectDB;
import com.imath.connect.data.StandardConfigurationDB;
import com.imath.connect.data.UserConnectDB;
import com.imath.connect.model.Instance;
import com.imath.connect.model.Project;
import com.imath.connect.model.StandardConfiguration;
import com.imath.connect.model.UserConnect;
import com.imath.connect.service.AbstractController;
import com.imath.connect.service.InstanceController;
import com.imath.connect.service.ProjectController;
import com.imath.connect.service.StandardConfigurationController;
import com.imath.connect.service.UserConnectController;
import com.imath.connect.util.Constants;
import com.imath.connect.util.Encryptor;
import com.imath.connect.util.Resources;
import com.imath.connect.util.Util;

import org.jasypt.hibernate4.type.EncryptedStringType;

public abstract class AbstractIT {
    
    @Deployment
    public static Archive<?> createTestArchive() {
       MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class)
                .loadMetadataFromPom("pom.xml");  
       return ShrinkWrap.create(WebArchive.class, "test.war")
               .addAsLibraries(resolver.artifact("org.mockito:mockito-all:1.8.3").resolveAsFiles())
               .addAsLibraries(resolver.artifact("org.jasypt:jasypt:1.9.0").resolveAsFiles())
               .addAsLibraries(resolver.artifact("org.jasypt:jasypt-hibernate4:1.9.0").resolveAsFiles())
               .addClasses(EncryptedStringType.class, MainDB.class, UserConnect.class , Project.class, StandardConfiguration.class, Instance.class,
                       InstanceDB.class, ProjectDB.class, StandardConfigurationDB.class, UserConnectDB.class,
                       JaxRsActivator.class, ProjectRest.class, InstanceRest.class, com.imath.connect.security.SecurityManager.class, 
                       StandardConfigurationRest.class, UserConnectRest.class,
                       AbstractController.class, InstanceController.class, ProjectController.class, StandardConfigurationController.class, UserConnectController.class,
                       Constants.class, Resources.class, Util.class, Encryptor.class, AbstractIT.class, ProjectRestIT.class, InstanceRestIT.class, 
                       StandardConfigurationRestIT.class, UserConnectRestIT.class)
               .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
               .addAsWebInfResource("arquillian-ds.xml")
               .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
      }
}
