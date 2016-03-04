// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See License.txt in the repository root.

 /*
 * This file was automatically generated by com.microsoft.tfs.core.ws.generator.Generator
 * from the /complexType.vm template.
 */
package ms.tfs.workitemtracking.clientservices._03;

import com.microsoft.tfs.core.ws.runtime.*;
import com.microsoft.tfs.core.ws.runtime.serialization.*;
import com.microsoft.tfs.core.ws.runtime.types.*;
import com.microsoft.tfs.core.ws.runtime.util.*;
import com.microsoft.tfs.core.ws.runtime.xml.*;

import ms.tfs.workitemtracking.clientservices._03._ClientService2Soap_SyncExternalStructures;

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
public class _ClientService2Soap_SyncExternalStructures
    implements ElementSerializable
{
    // No attributes    

    // Elements
    protected String projectURI;

    public _ClientService2Soap_SyncExternalStructures()
    {
        super();
    }

    public _ClientService2Soap_SyncExternalStructures(final String projectURI)
    {
        // TODO : Call super() instead of setting all fields directly?
        setProjectURI(projectURI);
    }

    public String getProjectURI()
    {
        return this.projectURI;
    }

    public void setProjectURI(String value)
    {
        this.projectURI = value;
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
            "projectURI",
            this.projectURI);

        writer.writeEndElement();
    }
}