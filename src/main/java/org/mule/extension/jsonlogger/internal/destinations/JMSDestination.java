package org.mule.extension.jsonlogger.internal.destinations;

import static org.mule.runtime.api.metadata.DataType.JSON_STRING;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.mule.extensions.jms.api.message.JmsxProperties;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.extension.api.annotation.param.NullSafe;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.extension.api.annotation.param.reference.ConfigReference;
import org.mule.runtime.extension.api.client.ExtensionsClient;
import org.mule.runtime.extension.api.client.OperationParameterizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSDestination implements Destination {

    private static final Logger LOGGER = LoggerFactory.getLogger(JMSDestination.class);

    @Inject
    ExtensionsClient extensionsClient;

    @Parameter
    @Optional
    @ConfigReference(namespace = "JMS", name = "CONFIG")
    @DisplayName("Configuration Ref")
    private String jmsConfigurationRef;

    @Parameter
    @Optional
    @Summary("Name of the target queue destination (e.g. logger-queue)")
    @DisplayName("Queue Destination")
    private String queueDestination;

    @Parameter
    @Optional(defaultValue = "true")
    @Summary("Whether or not the body outboundEncoding should be sent as a Message property")
    @DisplayName("Send Encoding")
    private boolean sendEncoding;

    @Parameter
    @Optional(defaultValue = "true")
    @Summary("Whether or not the body content type should be sent as a property")
    @DisplayName("Send Content-Type")
    private boolean sendContentType;

    @Parameter
    @Optional
    @NullSafe
    @Summary("Indicate which log categories should be send (e.g. [\"my.category\",\"another.category\"]). If empty, all will be send.")
    @DisplayName("Log Categories")
    private ArrayList<String> logCategories;

    @Parameter
    @Optional(defaultValue = "25")
    @Summary("Indicate max quantity of logs entries to be send to the external destination")
    @DisplayName("Max Batch Size")
    private int maxBatchSize;

    @Override
    public int getMaxBatchSize() {
        return this.maxBatchSize;
    }

    @Override
    public String getSelectedDestinationType() {
        return "JMS";
    }

    @Override
    public ArrayList<String> getSupportedCategories() {
        return logCategories;
    }

    @Override
    public void sendToExternalDestination(String finalLog) {
        try {
            Consumer<OperationParameterizer> parameters = operationParameterizer -> operationParameterizer
                    .withConfigRef(this.jmsConfigurationRef)
                    .withParameter("destination", this.queueDestination)
                    .withParameter("body", new TypedValue<>(finalLog, JSON_STRING))
                    .withParameter("sendEncoding", sendEncoding)
                    .withParameter("sendContentType", sendContentType)
                    .withParameter("jmsxProperties", new JmsxProperties())
                    .withParameter("properties", new HashMap<String, Object>());
            extensionsClient.execute("JMS", "publish", parameters);
        } catch (Exception e) {
            LOGGER.error("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void initialise() {

    }

    @Override
    public void dispose() {

    }
}
