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
package de.intranda.digiverso.presentation.modules.skeleton.managedbeans;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.intranda.digiverso.presentation.exceptions.ModuleMissingException;
import de.intranda.digiverso.presentation.modules.skeleton.SkeletonModule;

/**
 * Must be declared in faces-config.xml!
 */
@ManagedBean
@SessionScoped
public class SkeletonBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(SkeletonBean.class);

    /** Empty constructor. */
    public SkeletonBean() {
    }

    @PostConstruct
    public void init() {
    }

    public boolean isModuleEnabled() throws ModuleMissingException {
        return SkeletonModule.getInstance().getConfiguration().isModuleEnabled();
    }
}
