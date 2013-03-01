/*
 * (C) Copyright 2013 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     ldoguin
 */
package org.nuxeo.sample;

import org.apache.commons.lang.StringEscapeUtils;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventContext;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.ecm.core.event.impl.DocumentEventContext;
import org.nuxeo.ecm.core.schema.FacetNames;


/**
 * This listener will unescape only chosen ISO-8859-1 characters.
 * 
 * @author ldoguin
 */
public class HTMLUnescaperListener implements EventListener {

    public void handleEvent(Event event) throws ClientException {

        EventContext context = event.getContext();
        if (!(context instanceof DocumentEventContext)) {
            return;
        }
        DocumentModel doc = ((DocumentEventContext) context).getSourceDocument();
        if (doc.hasFacet(FacetNames.IMMUTABLE)) {
            return;
        }

        String html = doc.getProperty("note:note").getValue(String.class);
        String unescapedHtml = StringEscapeUtils.unescapeHtml(html);
        doc.setPropertyValue("note:note", unescapedHtml);
    }
    
}
