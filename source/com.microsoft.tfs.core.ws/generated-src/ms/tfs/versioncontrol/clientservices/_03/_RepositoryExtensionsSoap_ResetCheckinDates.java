// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See License.txt in the repository root.

 /*
 * This file was automatically generated by com.microsoft.tfs.core.ws.generator.Generator
 * from the /complexType.vm template.
 */
package ms.tfs.versioncontrol.clientservices._03;

import com.microsoft.tfs.core.ws.runtime.*;
import com.microsoft.tfs.core.ws.runtime.serialization.*;
import com.microsoft.tfs.core.ws.runtime.types.*;
import com.microsoft.tfs.core.ws.runtime.util.*;
import com.microsoft.tfs.core.ws.runtime.xml.*;

import ms.tfs.versioncontrol.clientservices._03._RepositoryExtensionsSoap_ResetCheckinDates;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

/**
 * Automatically generated complex type class.
 */
public class _RepositoryExtensionsSoap_ResetCheckinDates
    implements ElementSerializable
{
    // No attributes    

    // Elements
    protected Calendar lastCheckinDate;

    public _RepositoryExtensionsSoap_ResetCheckinDates()
    {
        super();
    }

    public _RepositoryExtensionsSoap_ResetCheckinDates(final Calendar lastCheckinDate)
    {
        // TODO : Call super() instead of setting all fields directly?
        setLastCheckinDate(lastCheckinDate);
    }

    public Calendar getLastCheckinDate()
    {
        return this.lastCheckinDate;
    }

    public void setLastCheckinDate(Calendar value)
    {
        if (value == null)
        {
            throw new IllegalArgumentException("'lastCheckinDate' is a required element, its value cannot be null");
        }

        this.lastCheckinDate = value;
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
            "lastCheckinDate",
            this.lastCheckinDate,
            true);

        writer.writeEndElement();
    }
}