/**
 * Copyright (C) 2023 Bonitasoft S.A.
 * Bonitasoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation
 * version 2.1 of the License.
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth
 * Floor, Boston, MA 02110-1301, USA.
 **/
package org.bonitasoft.engine.bpm.bar;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.bind.DataBindingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

import org.bonitasoft.engine.bpm.flownode.ActivityDefinition;
import org.bonitasoft.engine.bpm.flownode.BoundaryEventDefinition;
import org.bonitasoft.engine.bpm.flownode.EndEventDefinition;
import org.bonitasoft.engine.bpm.flownode.ErrorEventTriggerDefinition;
import org.bonitasoft.engine.bpm.flownode.FlowElementContainerDefinition;
import org.bonitasoft.engine.bpm.flownode.IntermediateCatchEventDefinition;
import org.bonitasoft.engine.bpm.flownode.IntermediateThrowEventDefinition;
import org.bonitasoft.engine.bpm.flownode.MessageEventTriggerDefinition;
import org.bonitasoft.engine.bpm.flownode.SignalEventTriggerDefinition;
import org.bonitasoft.engine.bpm.flownode.StartEventDefinition;
import org.bonitasoft.engine.bpm.flownode.TimerEventTriggerDefinition;
import org.bonitasoft.engine.bpm.flownode.impl.internal.BoundaryEventDefinitionImpl;
import org.bonitasoft.engine.bpm.flownode.impl.internal.EndEventDefinitionImpl;
import org.bonitasoft.engine.bpm.flownode.impl.internal.IntermediateCatchEventDefinitionImpl;
import org.bonitasoft.engine.bpm.flownode.impl.internal.IntermediateThrowEventDefinitionImpl;
import org.bonitasoft.engine.bpm.flownode.impl.internal.StartEventDefinitionImpl;
import org.bonitasoft.engine.bpm.process.DesignProcessDefinition;
import org.bonitasoft.engine.bpm.process.ProcessDefinition;
import org.bonitasoft.engine.bpm.process.impl.internal.DesignProcessDefinitionImpl;
import org.bonitasoft.engine.bpm.process.impl.internal.SubProcessDefinitionImpl;
import org.glassfish.hk2.osgiresourcelocator.ResourceFinder;
import org.xml.sax.SAXException;

/**
 * @author Baptiste Mesta
 * @author Matthieu Chaffotte
 */
public class ProcessDefinitionBARContribution implements BusinessArchiveContribution {

    private static final String PROCESS_DEFINITION_XSD = "/ProcessDefinition.xsd";
    public static final String PROCESS_DEFINITION_XML = "process-design.xml";

    private final JAXBContext jaxbContext;

    public ProcessDefinitionBARContribution() {
        try {
            jaxbContext = JAXBContext.newInstance(DesignProcessDefinitionImpl.class);
        } catch (final Exception e) {
            throw new DataBindingException(e);
        }
    }

    private Marshaller createMarshaller() throws JAXBException {
        var jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name()); // this is the default value, but it is more explicit like this.
        return jaxbMarshaller;
    }

    private Unmarshaller createUnmarshaller() throws JAXBException, SAXException {
        var processDefinitionXsd = Optional.ofNullable(ResourceFinder.findEntry(PROCESS_DEFINITION_XSD))
                .orElseGet(() -> ProcessDefinition.class.getResource(PROCESS_DEFINITION_XSD));
        var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        jaxbUnmarshaller
                .setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                        .newSchema(processDefinitionXsd));
        return jaxbUnmarshaller;
    }

    @Override
    public boolean isMandatory() {
        return true;
    }

    @Override
    public boolean readFromBarFolder(final BusinessArchive businessArchive, final File barFolder)
            throws IOException, InvalidBusinessArchiveFormatException {
        final File file = new File(barFolder, PROCESS_DEFINITION_XML);
        if (!file.exists()) {
            return false;
        }
        final DesignProcessDefinition processDefinition = deserializeProcessDefinition(file);
        businessArchive.setProcessDefinition(processDefinition);
        return true;
    }

    public DesignProcessDefinition deserializeProcessDefinition(final File file)
            throws IOException, InvalidBusinessArchiveFormatException {
        String content = Files.readString(file.toPath());
        try {
            return convertXmlToProcess(content);
        } catch (final IOException e) {
            checkVersion(content);
            throw new InvalidBusinessArchiveFormatException(e);
        }
    }

    void checkVersion(final String content) throws InvalidBusinessArchiveFormatException {
        final Pattern pattern = Pattern.compile("http://www\\.bonitasoft\\.org/ns/process/client/(\\d\\.\\d)");
        final Matcher matcher = pattern.matcher(content);
        final boolean find = matcher.find();
        if (!find) {
            throw new InvalidBusinessArchiveFormatException("There is no bonitasoft process namespace declaration");
        }
        final String group = matcher.group(1);
        if (!group.equals("7.4")) {
            throw new InvalidBusinessArchiveFormatException("Wrong version of your process definition, " + group
                    + " namespace is not compatible with your current version. Use the studio to update it.");
        }
    }

    @Override
    public void saveToBarFolder(final BusinessArchive businessArchive, final File barFolder) throws IOException {
        final DesignProcessDefinition processDefinition = businessArchive.getProcessDefinition();
        serializeProcessDefinition(barFolder, processDefinition);
    }

    public void serializeProcessDefinition(final File barFolder, final DesignProcessDefinition processDefinition)
            throws IOException {
        try {
            Files.writeString(barFolder.toPath().resolve(PROCESS_DEFINITION_XML),
                    convertProcessToXml(processDefinition));
        } catch (final FileNotFoundException e) {
            throw new IOException(e);
        }
    }

    public String convertProcessToXml(DesignProcessDefinition processDefinition) throws IOException {
        final StringWriter writer = new StringWriter();
        try {
            createMarshaller().marshal(processDefinition, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new IOException(e);
        }
    }

    public DesignProcessDefinition convertXmlToProcess(String content) throws IOException {
        try (InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
            DesignProcessDefinition process = (DesignProcessDefinition) createUnmarshaller().unmarshal(stream);
            if (process.getActorInitiator() != null) {
                process.getActorInitiator().setInitiator(true);
            }
            addEventTriggerOnEvents(process.getFlowElementContainer());
            return process;
        } catch (UnsupportedOperationException | JAXBException | SAXException e) {
            throw new IOException("Failed to deserialize the XML string provided", e);
        }
    }

    private void addEventTriggerOnEvents(FlowElementContainerDefinition flowElementContainer) {
        /*
         * Add event trigger manually
         */
        addEventTriggerOnIntermediateCatchEvent(flowElementContainer);
        addEventTriggerOnIntermediateThrowEvent(flowElementContainer);
        addEventTriggerOnEndEvent(flowElementContainer);
        addEventTriggerOnBoundaryEvent(flowElementContainer);
        addEventTriggerOnStartEvent(flowElementContainer);
    }

    private void addEventTriggerOnStartEvent(FlowElementContainerDefinition flowElementContainer) {
        for (StartEventDefinition startEvent : flowElementContainer.getStartEvents()) {
            StartEventDefinitionImpl startEventImpl = (StartEventDefinitionImpl) startEvent;
            for (MessageEventTriggerDefinition messageEventTrigger : startEvent.getMessageEventTriggerDefinitions()) {
                startEventImpl.addEventTrigger(messageEventTrigger);
            }
            for (ErrorEventTriggerDefinition errorEventTrigger : startEvent.getErrorEventTriggerDefinitions()) {
                startEventImpl.addEventTrigger(errorEventTrigger);
            }
            for (SignalEventTriggerDefinition signalEventTrigger : startEvent.getSignalEventTriggerDefinitions()) {
                startEventImpl.addEventTrigger(signalEventTrigger);
            }
            for (TimerEventTriggerDefinition timerEventTrigger : startEvent.getTimerEventTriggerDefinitions()) {
                startEventImpl.addEventTrigger(timerEventTrigger);
            }
        }
    }

    private void addEventTriggerOnBoundaryEvent(FlowElementContainerDefinition flowElementContainer) {
        for (ActivityDefinition activity : flowElementContainer.getActivities()) {
            for (BoundaryEventDefinition boundaryEvent : activity.getBoundaryEventDefinitions()) {
                var boundaryEventImpl = (BoundaryEventDefinitionImpl) boundaryEvent;
                boundaryEvent.getMessageEventTriggerDefinitions().forEach(boundaryEventImpl::addEventTrigger);
                boundaryEvent.getErrorEventTriggerDefinitions().forEach(boundaryEventImpl::addEventTrigger);
                boundaryEvent.getSignalEventTriggerDefinitions().forEach(boundaryEventImpl::addEventTrigger);
                boundaryEvent.getTimerEventTriggerDefinitions().forEach(boundaryEventImpl::addEventTrigger);
            }
            if (activity.getClass() == SubProcessDefinitionImpl.class) {
                addEventTriggerOnEvents(((SubProcessDefinitionImpl) activity).getSubProcessContainer());
            }
        }
    }

    private void addEventTriggerOnEndEvent(FlowElementContainerDefinition flowElementContainer) {
        for (EndEventDefinition endEvent : flowElementContainer.getEndEvents()) {
            EndEventDefinitionImpl endEventImpl = (EndEventDefinitionImpl) endEvent;
            for (ErrorEventTriggerDefinition errorEventTrigger : endEventImpl.getErrorEventTriggerDefinitions()) {
                endEventImpl.addEventTrigger(errorEventTrigger);
            }
            for (SignalEventTriggerDefinition signalEventTrigger : endEventImpl.getSignalEventTriggerDefinitions()) {
                endEventImpl.addEventTrigger(signalEventTrigger);
            }
            for (MessageEventTriggerDefinition messageEventTrigger : endEventImpl.getMessageEventTriggerDefinitions()) {
                endEventImpl.addEventTrigger(messageEventTrigger);
            }
        }
    }

    private void addEventTriggerOnIntermediateThrowEvent(FlowElementContainerDefinition flowElementContainer) {
        for (IntermediateThrowEventDefinition throwEvent : flowElementContainer.getIntermediateThrowEvents()) {
            IntermediateThrowEventDefinitionImpl throwEventImpl = (IntermediateThrowEventDefinitionImpl) throwEvent;
            for (MessageEventTriggerDefinition messageEventTriggerDefinition : throwEventImpl
                    .getMessageEventTriggerDefinitions()) {
                throwEventImpl.addEventTrigger(messageEventTriggerDefinition);
            }
            for (SignalEventTriggerDefinition signalEventTriggerDefinition : throwEventImpl
                    .getSignalEventTriggerDefinitions()) {
                throwEventImpl.addEventTrigger(signalEventTriggerDefinition);
            }
        }
    }

    private void addEventTriggerOnIntermediateCatchEvent(FlowElementContainerDefinition flowElementContainer) {
        for (IntermediateCatchEventDefinition catchEvent : flowElementContainer.getIntermediateCatchEvents()) {
            IntermediateCatchEventDefinitionImpl catchEventImpl = (IntermediateCatchEventDefinitionImpl) catchEvent;
            for (MessageEventTriggerDefinition messageEventTrigger : catchEvent.getMessageEventTriggerDefinitions()) {
                catchEventImpl.addEventTrigger(messageEventTrigger);
            }
            for (ErrorEventTriggerDefinition errorEventTrigger : catchEvent.getErrorEventTriggerDefinitions()) {
                catchEventImpl.addEventTrigger(errorEventTrigger);
            }
            for (SignalEventTriggerDefinition signalEventTrigger : catchEventImpl.getSignalEventTriggerDefinitions()) {
                catchEventImpl.addEventTrigger(signalEventTrigger);
            }
            for (TimerEventTriggerDefinition timerEventTrigger : catchEventImpl.getTimerEventTriggerDefinitions()) {
                catchEventImpl.addEventTrigger(timerEventTrigger);
            }
        }
    }

    @Override
    public String getName() {
        return "Process design";
    }

}
