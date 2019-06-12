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
package io.goobi.viewer.modules.skeleton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.goobi.viewer.controller.AbstractConfiguration;
import io.goobi.viewer.controller.DataManager;

public final class ModuleConfiguration extends AbstractConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ModuleConfiguration.class);

    public ModuleConfiguration(String configFilePath) {
        // Load default config file
        //        try {
        //            config = new XMLConfiguration(configFilePath);
        //            config.setReloadingStrategy(new FileChangedReloadingStrategy());
        //            if (!config.getFile().exists()) {
        //                logger.error("Default configuration file not found: {}", config.getFile().getAbsolutePath());
        //                throw new ConfigurationException();
        //            }
        //            logger.info("Default configuration file loaded.");
        //        } catch (ConfigurationException e) {
        //            logger.error("ConfigurationException", e);
        //            config = new XMLConfiguration();
        //        }

        // Load local config file
        try {
            File fileLocal = new File(configFilePath);
            if (fileLocal.exists()) {
                configLocal = new XMLConfiguration(fileLocal);
                configLocal.setReloadingStrategy(new FileChangedReloadingStrategy());
                logger.info("Local configuration file '{}' loaded.", fileLocal.getAbsolutePath());
            } else {
                configLocal = new XMLConfiguration();
            }
            config = configLocal;
        } catch (ConfigurationException e) {
            logger.error("ConfigurationException", e);
            // If failed loading the local file, use default for both
            configLocal = config;
        }
    }

    public boolean reloadingRequired() {
        boolean ret = false;
        if (configLocal != null) {
            ret = configLocal.getReloadingStrategy().reloadingRequired() || config.getReloadingStrategy().reloadingRequired();
        }
        ret = config.getReloadingStrategy().reloadingRequired();
        return ret;
    }

    /*********************************** direct config results ***************************************/

    public static String getLocalRessourceBundleFile() {
        return DataManager.getInstance().getConfiguration().getConfigLocalPath() + "messages_de.properties";
    }

    /**
     * 
     * @return
     * @should return correct value
     */
    public boolean isModuleEnabled() {
        return getLocalBoolean(("enabled"), false);
    }

    /**
     * 
     * @param type
     * @return
     */
    public List<String> getGuiContributions(String type) {
        return getLocalList("guiContributions." + type + ".url", new ArrayList<>());
    }
}
