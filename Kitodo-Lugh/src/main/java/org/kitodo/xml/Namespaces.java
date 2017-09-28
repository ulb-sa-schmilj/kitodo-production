/*
 * (c) Kitodo. Key to digital objects e. V. <contact@kitodo.org>
 *
 * This file is part of the Kitodo project.
 *
 * It is licensed under GNU General private License version 3 or later.
 *
 * For the full copyright and license information, please read the
 * GPL3-License.txt file that was distributed with this source code.
 */

package org.kitodo.xml;

import java.util.*;

import org.kitodo.lugh.vocabulary.*;

/**
 * Helper class to create the xmlns: namespace prefixes.
 *
 * @author Matthias Ronge
 */
public class Namespaces extends HashMap<String, String> {
    /**
     * Maps the constant namespaces "xml:" and "xmlns:" to their reserved
     * prefixes. The map is populated in a static block below.
     */
    private static final Map<String, String> CONSTANTS = new HashMap<>(3);

    /**
     * The default "rdf:" prefix, which may however be overridden. In this
     * class, all prefix Strings are without the colon.
     */
    private static final String DEFAULT_PREFIX_RDF = "rdf";

    /**
     * The default "rdfs:" prefix, which may however be overridden. In this
     * class, all prefix Strings are without the colon.
     */
    private static final String DEFAULT_PREFIX_RDFS = "rdfs";

    /**
     * The "xml:" prefix which is reserved and must neither be used otherwise
     * nor be named differently. In this class, all prefix Strings are without
     * the colon.
     */
    private static final String PREFIX_XML = "xml";

    /**
     * The "xmlns:" prefix which is reserved and must neither be used otherwise
     * nor be named differently. In this class, all prefix Strings are without
     * the colon.
     */
    private static final String PREFIX_XMLNS = "xmlns";
    /**
     * The XMLNS namespace URL.
     */
    public static final String XMLNS_NAMESPACE = "http://www.w3.org/2000/xmlns/";

    /**
     * Populates the map with the constant namespaces "xml:" and "xmlns:".
     */
    static {
        CONSTANTS.put(XMLNS_NAMESPACE, PREFIX_XMLNS);
        CONSTANTS.put(XML.NAMESPACE, PREFIX_XML);
    }

    /**
     * Returns a sequence of letters from a positive whole number.
     *
     * @param value
     *            number to convert
     * @return a, b, c, …, x, y, z, aa, ab, ac, …
     */
    private static String asLetters(long value) {
        int codePoint = (int) ('a' + (--value % 26));
        long higher = value / 26;
        String letter = new String(Character.toChars(codePoint));
        return higher == 0 ? letter : asLetters(higher).concat(letter);
    }

    /**
     * Recomposes an URL from a namespace and a local name, adding a number sign
     * in between if the URL does not end in a slash.
     *
     * @param namespace
     *            namespace for URL
     * @param localName
     *            local name for URL
     * @return combined URL
     */
    public static String concat(String namespace, String localName) {
        boolean addNumberSign = !namespace.endsWith("/") && !namespace.endsWith("#");
        StringBuilder result = new StringBuilder(namespace.length() + (addNumberSign ? 1 : 0) + localName.length());
        result.append(namespace);
        if (addNumberSign) {
            result.append('#');
        }
        result.append(localName);
        return result.toString();
    }

    public static String expand(String abbreviatedUrl, Map<String, String> prefixes) {
        String namespace = prefixes.get(getPrefix(abbreviatedUrl));
        if (namespace == null) {
            return abbreviatedUrl;
        }
        return concat(namespace, abbreviatedUrl.substring(abbreviatedUrl.indexOf(':') + 1));
    }

    public static String getPrefix(String url) {
        return url.substring(0, url.indexOf(':'));
    }

    /**
     * Returns the local name part of an URL. If the URL contains an anchor
     * symbol the local part is considered the sequence after it, otherwise the
     * local name is considered the sequence after the last slash.
     *
     * @param url
     *            URL to return the local name from
     * @return the local name
     */
    public static String localNameOf(String url) {
        int numberSign = url.indexOf('#');
        if (numberSign > -1) {
            return url.substring(numberSign + 1);
        }
        return url.substring(url.lastIndexOf('/') + 1);
    }

    /**
     * Returns the namespace part of an URL. If the URL contains an anchor
     * symbol the namespace is considered the sequence before it, omitting the
     * anchor symbol, otherwise the namespace is considered the sequence up to
     * and including the last slash.
     *
     * @param url
     *            url to return the namespace from
     * @return the namespace
     */
    public static String namespaceOf(String url) {
        int numberSign = url.indexOf('#');
        if (numberSign > -1) {
            return url.substring(0, numberSign);
        }
        return url.substring(0, url.lastIndexOf('/') + 1);
    }

    /**
     * Counter to create new namespace prefixes.
     */
    private long next;

    /**
     * The map of used namespaces in serializing an XML document. Mapping
     * direction is namespace to prefix, {@code #} namespaces are stored without
     * the {@code #} at the end, {@code /} namespaces are stored with a
     * {@code /} as last character.
     */
    private final Map<String, String> used;

    /**
     * Creates a new NamespaceHandler for a given Node which is considered the
     * root node of the XML document.
     */
    public Namespaces() {
        this(null);
    }

    /**
     * Creates a new NamespaceHandler for a given Node which is considered the
     * root node of the XML document.
     *
     * @param presets
     *            Presets of namespace shortcuts, mapped namespace to shortcut.
     *            May be null or empty.
     */
    public Namespaces(Map<String, String> presets) {
        super((int) Math.ceil(((presets != null ? presets.size() : 0) + 2) / 0.75));
        super.put(RDF.NAMESPACE, DEFAULT_PREFIX_RDF);
        super.put(RDFS.NAMESPACE, DEFAULT_PREFIX_RDFS);
        if (presets != null) {
            super.putAll(presets);
        }
        used = new HashMap<>();
    }

    /**
     * Returns the abbreviated attribute. If the attribute is in the same
     * namespace as the element that will hold it, the prefix is omitted.
     *
     * @param element
     *            node the attribute is on
     * @param attribute
     *            attribute key to convert
     * @return abbreviated or basic attribute
     */
    String abbreviateAttribute(String element, String attribute) {
        String ns = namespaceOf(attribute);
        if (ns.isEmpty()) {
            return attribute;
        }
        available(ns);
        String tag = attribute.substring(ns.endsWith("/") ? ns.length() : ns.length() + 1);
        return namespaceOf(element).equals(ns) ? tag : used.get(ns) + ':' + tag;
    }

    /**
     * Returns the abbreviated element name.
     *
     * @param element
     *            URL to abbreviate for an element name
     * @return the abbreviated node name
     */
    String abbreviateElement(String element) {
        String ns = namespaceOf(element);
        if (ns.isEmpty()) {
            return element;
        }
        available(ns);
        return used.get(ns) + ':' + element.substring(ns.endsWith("/") ? ns.length() : ns.length() + 1);
    }

    /**
     * Grants that a prefix is available for a namespace. Missing prefixes are
     * either fetched from the constants or the presets or else are created
     * alphabetically ascending.
     *
     * @param namespace
     *            the namespace
     */
    private void available(String namespace) {
        if (!used.containsKey(namespace)) {
            if (CONSTANTS.containsKey(namespace)) {
                used.put(namespace, CONSTANTS.get(namespace));
            } else if (super.containsKey(namespace)) {
                used.put(namespace, super.get(namespace));
            } else {
                String prefix;
                do {
                    prefix = asLetters(++next);
                } while (super.containsValue(prefix) || CONSTANTS.containsValue(prefix));
                used.put(namespace, prefix);
            }
        }
    }

    public String expand(String abbreviatedUrl) {
        return expand(abbreviatedUrl, this);
    }

    /**
     * Returns all namespaces to be added to the document head. Mapping
     * direction is namespace to prefix, {@code #} namespaces are stored without
     * the {@code #} at the end, {@code /} namespaces are stored with a
     * {@code /} as last character. The method must be called after the document
     * has been converted.
     *
     * @return all namespaces
     */
    Set<Entry<String, String>> namespaceSetForXMLFile() {
        Map<String, String> result = new HashMap<>((int) Math.ceil(used.size() / 0.75));
        for (Entry<String, String> entry : used.entrySet()) {
            String prefix = entry.getKey();
            result.put(PREFIX_XMLNS + ':' + entry.getValue(), prefix);
        }
        return result.entrySet();
    }
}
