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
package de.intranda.digiverso.presentation.modules.skeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.common.SolrDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.intranda.digiverso.presentation.controller.DataManager;
import de.intranda.digiverso.presentation.exceptions.DAOException;
import de.intranda.digiverso.presentation.exceptions.ModuleMissingException;
import de.intranda.digiverso.presentation.modules.IModule;
import de.intranda.digiverso.presentation.modules.skeleton.dao.IModuleDAO;
import de.intranda.digiverso.presentation.modules.skeleton.dao.impl.ModuleJPADAO;

public class SkeletonModule implements IModule {

    private static final Logger logger = LoggerFactory.getLogger(SkeletonModule.class);

    public static final String ID = "viewer-module-skeleton";
    private static final String NAME = "Goobi Viewer module skeleton";
    private static final String VERSION = "1.0.20170711";

    private ModuleConfiguration configuration;
    private IModuleDAO dao;

    /**
     * Convenience method for retrieving the properly cast instance of this module registered in the core.
     * 
     * @return
     * @throws ModuleMissingException
     */
    public static SkeletonModule getInstance() throws ModuleMissingException {
        return (SkeletonModule) DataManager.getInstance().getModule(ID);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    @Override
    public boolean isLoaded() {
        return true;
    }

    @Override
    public ModuleConfiguration getConfiguration() {
        if (configuration == null) {
            configuration = new ModuleConfiguration(DataManager.getInstance().getConfiguration().getConfigLocalPath() + "config_" + ID + ".xml");
        }
        return configuration;
    }

    public void injectConfiguration(ModuleConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * @return the dao
     * @throws DAOException
     * @throws ModuleMissingException
     */
    public IModuleDAO getDao() throws DAOException {
        if (dao == null) {
            dao = new ModuleJPADAO();
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(IModuleDAO dao) {
        this.dao = dao;
    }

    @Override
    public Map<String, String> getCmsMenuContributions() {
        Map<String, String> map = new HashMap<>();

        // TODO Add CMS menu contributions here
        map.put("skeleton_overview", "skeleton/");

        return map;
    }

    @Override
    public List<String> getSidebarContributions() {
        List<String> ret = new ArrayList<>();

        // TODO Add paths to your sidebar widgets here
        ret.add("resources/components/widgets/widget_skeleton.xhtml");

        return ret;
    }

    @Override
    public List<String> getAdminContributions() {
        List<String> ret = new ArrayList<>();

        // TODO Add paths to your admin backend contributions here
        ret.add("resources/components/widgets/widget_admin_skeleton.xhtml");

        return ret;
    }

    /**
     * Widget contributions for regular pages.
     * 
     * @param type <code>PageType</code> name (e.g. viewImage)
     * @return
     */
    @Override
    public List<String> getWidgets(String type) {
        return getConfiguration().getGuiContributions(type);
    }

    @Override
    public void augmentReIndexRecord(String pi, String dataRepository, String namingScheme) throws DAOException {
        // TODO Do something in the module context when exporting record for re-indexing an entire record
    }

    @Override
    public boolean augmentReIndexPage(String pi, int page, SolrDocument doc, String dataRepository, String namingScheme) throws Exception {
        // TODO Do something in the module context when exporting record for re-indexing an single page of a record
        return true;
    }

    @Override
    public boolean augmentReIndexPage(String pi, int page, SolrDocument doc, String recordType, String dataRepository, String namingScheme)
            throws Exception {
        // TODO Do something in the module context when exporting record for re-indexing an single page of a record
        return true;
    }

    @Override
    public boolean augmentResetRecord() {
        // TODO Do something in the module context when unloading the current record in <code>NavigationHelper</code>
        return true;
    }
}
