package net.fortuna.ical4j.connector.dav.request;

import net.fortuna.ical4j.connector.dav.property.CSDavPropertyName;
import net.fortuna.ical4j.connector.dav.property.CalDavPropertyName;
import org.apache.jackrabbit.webdav.DavConstants;
import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.xml.DomUtil;
import org.apache.jackrabbit.webdav.xml.Namespace;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Arrays;

public interface XmlSupport {

    /**
     *
     */
    String PROPERTY_COMP_FILTER = "comp-filter";

    /**
     *
     */
    String PROPERTY_TIME_RANGE = "time-range";

    /**
     *
     */
    String ATTRIBUTE_NAME = "name";

    /**
     *
     */
    String ATTRIBUTE_START = "start";

    /**
     *
     */
    String ATTRIBUTE_END = "end";

    default Document newXmlDocument(Node...children) throws ParserConfigurationException {
        Document document = DomUtil.createDocument();
        Arrays.stream(children).forEach(document::appendChild);

        return document;
    }

    default Element newElement(Document document, String elementName, Namespace namespace, Node...children) {
        Element element = DomUtil.createElement(document, elementName, namespace);
        Arrays.stream(children).forEach(element::appendChild);
        return element;
    }

    default Element newElement(Document document, DavPropertyName propertyName, Node...children) {
        Element element = DomUtil.createElement(document, propertyName.getName(), propertyName.getNamespace());
        Arrays.stream(children).forEach(element::appendChild);
        return element;
    }

    /**
     *
     * @param document
     * @param elementName
     * @param children
     * @return
     * @deprecated use {@link XmlSupport#newElement(Document, DavPropertyName, Node...)}
     */
    @Deprecated
    default Element newDavElement(Document document, String elementName, Node...children) {
        return newElement(document, elementName, DavConstants.NAMESPACE, children);
    }

    /**
     *
     * @param document
     * @param elementName
     * @param children
     * @return
     * @deprecated use {@link XmlSupport#newElement(Document, DavPropertyName, Node...)}
     */
    @Deprecated
    default Element newCsElement(Document document, String elementName, Node...children) {
        return newElement(document, elementName, CSDavPropertyName.NAMESPACE, children);
    }

    /**
     *
     * @param document
     * @param elementName
     * @param children
     * @return
     * @deprecated use {@link XmlSupport#newElement(Document, DavPropertyName, Node...)}
     */
    @Deprecated
    default Element newCalDavElement(Document document, String elementName, Node...children) {
        return newElement(document, elementName, CalDavPropertyName.NAMESPACE, children);
    }

    default Element newComponentFilter(Document document, String componentName, Node...children) {
        Element calFilter = newCalDavElement(document, PROPERTY_COMP_FILTER);
        calFilter.setAttribute(ATTRIBUTE_NAME, componentName);
        Arrays.stream(children).forEach(calFilter::appendChild);
        return calFilter;
    }

    default Element newTimeRange(Document document, String startTime, String endTime) {
        Element timeRange = newCalDavElement(document, PROPERTY_TIME_RANGE);
        timeRange.setAttribute(ATTRIBUTE_START, startTime);
        timeRange.setAttribute(ATTRIBUTE_END, endTime);
        return timeRange;
    }
}
