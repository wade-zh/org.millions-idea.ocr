/***
 * @pName j_read_any_xml_config_demo
 * @name ConfigLoader
 * @user HongWei
 * @date 2018/6/18
 * @desc
 */
package com.example.demo;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.net.URL;

public class ConfigLoader {
    public static void configure(String path) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            URL url = new URL(path);
            InputStream stream = url.openStream();
            parser.parse(stream, new XmlHandler());
        } catch (Exception e) {
            System.out.println("Failed to load the configuration file, Because is :" + e.toString());
        }
    }
    static class XmlHandler extends DefaultHandler {
        /**
         * Receive notification of the start of an element.
         * <p>
         * <p>By default, do nothing.  Application writers may override this
         * method in a subclass to take specific actions at the start of
         * each element (such as allocating a new tree node or writing
         * output to a file).</p>
         *
         * @param uri        The Namespace URI, or the empty string if the
         *                   element has no Namespace URI or if Namespace
         *                   processing is not being performed.
         * @param localName  The local name (without prefix), or the
         *                   empty string if Namespace processing is not being
         *                   performed.
         * @param qName      The qualified name (with prefix), or the
         *                   empty string if qualified names are not available.
         * @param attributes The attributes attached to the element.  If
         *                   there are no attributes, it shall be an empty
         *                   Attributes object.
         * @throws SAXException Any SAX exception, possibly
         *                      wrapping another exception.
         * @see ContentHandler#startElement
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equalsIgnoreCase("rabbitmq")) {
                GlobalConfig.setRabbitMQ(new GlobalConfig.RabbitMQ(
                        attributes.getValue("hostName"),
                        Integer.valueOf(attributes.getValue("port")),
                        attributes.getValue("queueName"),
                        attributes.getValue("virtualHost"),
                        attributes.getValue("username"),
                        attributes.getValue("password")));
            }

            if (qName.equalsIgnoreCase("redis")) {
                GlobalConfig.setRedis(new GlobalConfig.Redis(
                        attributes.getValue("hostName"),
                        Integer.valueOf(attributes.getValue("port")),
                        attributes.getValue("password")));
            }
            super.startElement(uri, localName, qName, attributes);
        }

    }
}
