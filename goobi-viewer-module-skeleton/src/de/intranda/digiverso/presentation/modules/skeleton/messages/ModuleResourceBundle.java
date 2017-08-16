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
package de.intranda.digiverso.presentation.modules.skeleton.messages;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.intranda.digiverso.presentation.controller.DataManager;
import de.intranda.digiverso.presentation.messages.ViewerResourceBundle;

public class ModuleResourceBundle extends ViewerResourceBundle {

    private static final Logger logger = LoggerFactory.getLogger(ModuleResourceBundle.class);

    private static ResourceBundle bundle = null;
    private static ResourceBundle localBundle = null;

    /**
     * 
     * @param inLocale
     */
    public static synchronized void loadResourceBundle(Locale inLocale) {
        Locale locale;
        if (inLocale != null) {
            locale = inLocale;
        } else if (FacesContext.getCurrentInstance() != null && FacesContext.getCurrentInstance().getViewRoot() != null) {
            locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        } else {
            locale = Locale.ENGLISH;
        }
        if (bundle == null || !bundle.getLocale().equals(locale)) {
            bundle = ResourceBundle.getBundle("de.intranda.digiverso.presentation.module.skeleton.messages.messages", locale);
        }
        if (localBundle == null || !localBundle.getLocale().equals(locale)) {
            logger.trace("Reloading local resource bundle for '{}'...", locale.getLanguage());
            localBundle = loadLocalResourceBundle(locale);
        }
    }

    /**
     * 
     * @param locale
     * @return
     */
    private static ResourceBundle loadLocalResourceBundle(Locale locale) {
        File file = new File(DataManager.getInstance().getConfiguration().getLocalRessourceBundleFile());
        if (file.exists()) {
            try {
                URL resourceURL = file.getParentFile().toURI().toURL();
                // logger.debug("URL: " + file.getParentFile().toURI().toURL());
                URLClassLoader urlLoader = new URLClassLoader(new URL[] { resourceURL });
                return ResourceBundle.getBundle("messages", locale, urlLoader);
            } catch (Exception e) {
                // some error while loading bundle from file system; use
                // default bundle now ...
            }
        }

        return null;
    }

    @Override
    protected Object handleGetObject(String key) {
        return getTranslation(key, FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }

    /**
     * 
     * @param text
     * @param locale
     * @return
     */
    public static String getTranslation(String text, Locale locale) {
        // logger.trace("Translation for: {}", text);
        loadResourceBundle(locale);
        return getTranslation(text, bundle, localBundle);
    }
}
