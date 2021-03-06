// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See License.txt in the repository root.

 /*
 * This file was automatically generated by com.microsoft.tfs.core.ws.generator.Generator
 * from the /complexType.vm template.
 */
package ms.tfs.build.buildinfo._03;

import com.microsoft.tfs.core.ws.runtime.*;
import com.microsoft.tfs.core.ws.runtime.serialization.*;
import com.microsoft.tfs.core.ws.runtime.types.*;
import com.microsoft.tfs.core.ws.runtime.util.*;
import com.microsoft.tfs.core.ws.runtime.xml.*;

import ms.tfs.build.buildinfo._03._BuildStoreWebServiceSoap_AddProjectDetailsForBuild;
import ms.tfs.build.buildinfo._03._ProjectData;

import java.lang.String;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * Automatically generated complex type class.
 */
public class _BuildStoreWebServiceSoap_AddProjectDetailsForBuild
    implements ElementSerializable
{
    // No attributes    

    // Elements
    protected String buildUri;
    protected _ProjectData project;

    public _BuildStoreWebServiceSoap_AddProjectDetailsForBuild()
    {
        super();
    }

    public _BuildStoreWebServiceSoap_AddProjectDetailsForBuild(
        final String buildUri,
        final _ProjectData project)
    {
        // TODO : Call super() instead of setting all fields directly?
        setBuildUri(buildUri);
        setProject(project);
    }

    public String getBuildUri()
    {
        return this.buildUri;
    }

    public void setBuildUri(String value)
    {
        this.buildUri = value;
    }

    public _ProjectData getProject()
    {
        return this.project;
    }

    public void setProject(_ProjectData value)
    {
        this.project = value;
    }

    public void writeAsElement(
        final XMLStreamWriter writer,
        final String name)
        throws XMLStreamException
    {
        writer.writeStartElement(name);

        // Elements
        XMLStreamWriterHelper.writeElement(
            writer,
            "buildUri",
            this.buildUri);

        if (this.project != null)
        {
            this.project.writeAsElement(
                writer,
                "project");
        }

        writer.writeEndElement();
    }
}
