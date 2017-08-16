/**
 * This file is part of the Goobi viewer - a content presentation and management application for digitized objects.
 *
 * Visit these websites for more information.
 *          - http://www.intranda.com
 *          - http://digiverso.com
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.intranda.digiverso.presentation.modules.skeleton.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.intranda.digiverso.presentation.controller.DataManager;
import de.intranda.digiverso.presentation.dao.impl.JPADAO;
import de.intranda.digiverso.presentation.exceptions.DAOException;
import de.intranda.digiverso.presentation.modules.skeleton.dao.IModuleDAO;
import de.intranda.digiverso.presentation.modules.skeleton.model.Skeleton;

public class ModuleJPADAO implements IModuleDAO {

    /** Logger for this class. */
    private static final Logger logger = LoggerFactory.getLogger(ModuleJPADAO.class);

    /** Core DAO. */
    private final JPADAO dao;

    public ModuleJPADAO() throws DAOException {
        logger.trace("ModuleJPADAO()");
        dao = (JPADAO) DataManager.getInstance().getDao();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Skeleton> getAllSkeletons() throws DAOException {
        preQuery();
        Query q = dao.getEntityManager().createQuery("SELECT o FROM Skeleton o");
        // q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return q.getResultList();
    }

    @Override
    public boolean addSkeleton(Skeleton skeleton) throws DAOException {
        preQuery();
        EntityManager em = dao.getFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(skeleton);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return true;
    }

    @Override
    public boolean updateSkeleton(Skeleton skeleton) throws DAOException {
        preQuery();
        EntityManager em = dao.getFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(skeleton);
            em.getTransaction().commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            em.close();
        }

        return true;
    }

    @Override
    public boolean deleteSkeleton(Skeleton skeleton) throws DAOException {
        preQuery();
        EntityManager em = dao.getFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Skeleton o = em.getReference(Skeleton.class, skeleton.getId());
            em.remove(o);
            em.getTransaction().commit();
            return true;
        } finally {
            em.close();
        }
    }

    private void preQuery() throws DAOException {
        dao.preQuery();
    }
}
