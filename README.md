# JSON Logger

[![Maven Central](https://img.shields.io/maven-central/v/cloud.anypoint/json-logger)](https://central.sonatype.com/artifact/cloud.anypoint/json-logger/overview)

Drop-in replacement for default Mule Logger that outputs a JSON structure based on a predefined JSON schema.

## Why?

- Logs are as good as the logging practices applied by developers
- Not all developers think alike on what needs to be logged
- No tools to enforce proper logging standards
- Simple but essential logging best practices (e.g. tie multiple log entries to a single transaction by using a correlation id) are inconsistent and drastically impact troubleshooting capabilities
- The rise of Splunk, ELK and other log aggregation platforms is due to their ability to aggregate, understand and exploit data
- These platforms typically understand key=value pairs or JSON data structures. Only then, the full potential of these tools can be unlocked in the form of advanced dashboards and reports

For these reasons and based on previous customer experiences, I created this generic Java SDK JSON Logger Connector.

## How?

As mentioned above, this is a Java SDK based Mule 4 connector. However, in order to maximize customization to each customer's requirements while avoiding steep Java SDK learning curves, you can easily modify the output JSON data structure as well as connector configuration by editing 2 simple JSON schemas provided under:
>/json-logger/src/main/resources/schema/

In a nutshell, by defining the output JSON schema as well as providing some additional SDK specific details (e.g. default values, default expressions, etc.), we can dynamically generate a module that aligns to those schemas.

## Installation

Add this dependency to your application pom.xml

```
<groupId>cloud.anypoint</groupId>
<artifactId>json-logger</artifactId>
<version>3.0.2</version>
<classifier>mule-plugin</classifier>
```

If you want to send your logs to Anypoint MQ or a JMS queue, you need to add the corresponding dependencies to your implementation. Refer to the pom.xml file for guidance—it specifies these dependencies as optional. This approach allows the resulting application JAR to be approximately 10 MB smaller if queue connectivity is not required.

### Local Exchange Deployment

If you want to deploy JSON Logger to your organization's Exchange, you need to follow these steps:
1. Update the `groupId` in pom.xml to the org id of your target business group (instead of `cloud.anypoint`).
2. Add the Exchange Mule Maven Plugin to the plugins section of your pom.xml (don't forget to add a suitable version number, e.g., `0.0.23` as of the time of writing):
```
<plugin>
    <groupId>org.mule.tools.maven</groupId>
    <artifactId>exchange-mule-maven-plugin</artifactId>
    <version>${exchange.mule.maven.plugin.version}</version>
    <executions>
        <execution>
            <id>validate</id>
            <phase>validate</phase>
            <goals>
                <goal>exchange-pre-deploy</goal>
            </goals>
        </execution>
        <execution>
            <id>deploy</id>
            <phase>deploy</phase>
            <goals>
                <goal>exchange-deploy</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
3. Add a `<distributionManagement>` element to your pom.xml (note the `eu1.` which is required only for the EU management plane):
```
<distributionManagement>
    <repository>
        <id>anypoint-exchange-v3</id>
        <name>Anypoint Exchange V3</name>
        <url>https://maven.[eu1.]anypoint.mulesoft.com/api/v3/organizations/${project.groupId}/maven</url>
        <layout>default</layout>
    </repository>
</distributionManagement>
```
Also make sure, you have your credentials set up in your settings.xml accordingly (matching the repository id in this snippet).
4. Execute `mvn clean deploy`

Please also check these blogposts for more details:

PART 1: https://blogs.mulesoft.com/dev/anypoint-platform-dev/json-logging-in-mule-4-getting-the-most-out-of-your-logs/

PART 2: https://blogs.mulesoft.com/dev/api-dev/json-logging-in-mule-4/

##  Release notes

### 3.0.2 version - Release notes

* Bugfix for using commons-lang3 instead of commons-lang in pojos

### 3.0.1 version - Release notes

* Made queue dependencies optional to reduce size
* Fixed dependencies with security alerts

### 2.2.3 version - Release notes

* Made dw functions more robust
* Updated MUnit
* Started preparation of pipelines

### 2.2.2 version - Release notes

* Replaced joda-time by JDK Date and Time API
* Upgraded dependencies
* Removed unused dependencies
* Fixed AMQP destination

### 2.2.1 version - Release notes

* Reorganized folder structure
* Added Apache 2 license
* Removed deployment script
* Updated README to cover manual exchange deployment

### 2.2.0 version - Release notes

* Updated to support Java 17
* Upgraded dependencies to fix known vulnerabilities

### 2.1.0 version - Release notes

* Minimum supported mule runtime 4.3
* Upgraded dependencies to fix known vulnerabilities

### 2.0.1 version - Release notes

Bug fixes:
* Added support for large payloads

### 2.0.0 version - Release notes

New features:
* External Destinations
* Data masking

Improvements:
* Field ordering

More details in the coming blog post (stay tuned!)

### 1.1.0 version - Release notes

New features:
* Scoped loggers to capture "scope bound elapsed time". Great for performance tracking of specific components (e.g. outbound calls)
* Added "Parse content fields in json output" flag so that content fields can become part of final JSON output rather than a "stringified version" of the content

Improvements:
* Removed Guava and caching in general with a more efficient handling of timers (for elapsed time)
* Optimized generation of JSON output
* Code optimizations
* Minimized dependency footprint (down from ~23MB to ~13MB)
* Optimized parsing of TypedValue content fields

## Authors

* **Andres Ramirez** [Email: andres.ramirez@mulesoft.com] (original version)
* **Matthias Transier** [Email: matthias.transier@gmail.com] (update to Java 17)

## Support disclaimer

This extension is based on code developed by Andres Ramirez. It is a custom connector and it is provided under open source license.
It is not officially supported by MuleSoft/Salesforce or anyone else.
