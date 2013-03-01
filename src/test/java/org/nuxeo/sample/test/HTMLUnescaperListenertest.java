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
package org.nuxeo.sample.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.test.CoreFeature;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

import com.google.inject.Inject;


@RunWith(FeaturesRunner.class)
@Features(CoreFeature.class)
@Deploy({ "org.nuxeo.ecm.platform.htmlsanitizer",
        "nuxeo-htmlunescaper-sample" })
public class HTMLUnescaperListenertest {

    public static final String BAD_HTML = "<b>&eacute;foo&agrave;<script>bar</script></b>";

    public static final String SANITIZED_UNESCAPED_HTML = "<b>éfooà</b>";

    @Inject
    CoreSession session;

    @Test
    public void sanitizeNoteHtml() throws Exception {
        DocumentModel doc = session.createDocumentModel("/", "n", "Note");
        doc.setPropertyValue("note", BAD_HTML);
        doc.setPropertyValue("mime_type", "text/html");
        doc = session.createDocument(doc);
        String note = (String) doc.getPropertyValue("note");
        assertEquals(SANITIZED_UNESCAPED_HTML, note);

        session.save();
        doc.setPropertyValue("note", BAD_HTML);
        doc = session.saveDocument(doc);
        note = (String) doc.getPropertyValue("note");
        assertEquals(SANITIZED_UNESCAPED_HTML, note);
    }
}
