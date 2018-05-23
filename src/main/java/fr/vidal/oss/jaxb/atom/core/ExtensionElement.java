package fr.vidal.oss.jaxb.atom.core;

import java.util.Collection;

public interface ExtensionElement {
    Namespace namespace();

    String tagName();

    Collection<Attribute> attributes();
}
