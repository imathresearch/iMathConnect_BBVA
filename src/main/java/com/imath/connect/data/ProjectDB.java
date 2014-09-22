package com.imath.connect.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.imath.connect.model.Project;
import com.imath.connect.model.UserConnect;

@RequestScoped
public class ProjectDB {
    /**
     * The UserConnect DB repository
     * @author imath
     *
     */
    @Inject
    private EntityManager em;
    
    /**
     * Returns a {@link Project} from the given UUID
     * @param UUID The UUID of the {@link Project}
     * @author imath
     */
    public Project findById(String UUID) {
        em.flush();
        try {
            return em.find(Project.class, UUID);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Return the list of {@link Project} that belong to a given user
     * @param UUID The UUID of the {@link UserConnect}
     */
    
    public List<Project> findByOwner(String UUID) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Project> criteria = cb.createQuery(Project.class);
        Root<Project> project = criteria.from(Project.class);
        Predicate p1 = cb.equal(project.get("owner").get("UUID"), UUID);      
        criteria.select(project).where(p1);
        List<Project> out = em.createQuery(criteria).getResultList();
        return out;
    }
    
    /**
     * Return the list of {@link Project} that a given user is collaborating with
     * @param UUID The UUID of the {@link UserConnect}
     */
    
    public List<Project> findByCollaborators(String UUID) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Project> criteria = cb.createQuery(Project.class);
        Root<Project> project = criteria.from(Project.class);
        Join<Project,UserConnect> projectJoin = project.join("collaborators");
        Predicate p1 = cb.equal(projectJoin.get("UUID"), UUID);      
        criteria.select(project).where(p1);
        List<Project> out = em.createQuery(criteria).getResultList();
        return out;
    }
    
    /**
     * Returns the number of projects
     * @return
     */
    public Long countProjects() {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(Project.class)));
        return em.createQuery(cq).getSingleResult();
    }
    
    
}
