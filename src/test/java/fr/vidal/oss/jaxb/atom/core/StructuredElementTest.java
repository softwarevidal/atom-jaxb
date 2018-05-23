package fr.vidal.oss.jaxb.atom.core;

import org.junit.Test;

import java.util.List;

import static fr.vidal.oss.jaxb.atom.core.StructuredElement.builder;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class StructuredElementTest {

    public static final String SHOULD_CONTAIN_CHILD_OR_ATTRIBUTE = "A structured element should contain at least a child element or an attribute.";

    @Test
    public void raise_exception_when_tagName_is_missing() {
        Attribute attribute = null;
        try {
            builder(null, attribute).build();
            fail("Missing tagName");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("TagName is mandatory.");
        }
    }

    @Test
    public void raise_exception_when_attribute_is_null() {
        Attribute attribute = null;
        try {
            builder("rootElement", attribute).build();
            fail("Missing attribute");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo(SHOULD_CONTAIN_CHILD_OR_ATTRIBUTE);
        }
    }

    @Test
    public void raise_exception_when_tagName_is_missing_element_case() {
        ExtensionElement childElement = null;
        try {
            builder(null, childElement).build();
            fail("Missing tagName");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("TagName is mandatory.");
        }
    }

    @Test
    public void raise_exception_when_child_element_is_null() {
        ExtensionElement childElement = null;
        try {
            builder("rootElement", childElement).build();
            fail("Missing child element");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("A structured element should contain at least a child element or an attribute.");
        }
    }

    @Test
    public void construct_a_structured_element_with_multiple_attributes_and_elements() {
        Attribute attribute = anAttribute("type", "text");
        AnyElement childElement = aChildElement("dosages");

        StructuredElement rootElement = builder("rootElement", attribute)
            .addAttributes(attributes(
                anAttribute("attr", "value"),
                anAttribute("attr2", "value2"),
                anAttribute("attr3", "value3")))
            .addChildElements(childElements(
                childElement))
            .build();

        assertThat(rootElement.attributes()).containsExactly(
            attribute,
            anAttribute("attr", "value"),
            anAttribute("attr2", "value2"),
            anAttribute("attr3", "value3"));

        assertThat(rootElement.getExtensionElements()).containsExactly(childElement);
    }

    private List<ExtensionElement> childElements(ExtensionElement... childElements) {
        return asList(childElements);
    }

    private AnyElement aChildElement(String tagName) {
        return AnyElement.builder(tagName)
            .addAnyElement(AnyElement.builder("dose").build())
            .build();
    }

    private List<Attribute> attributes(Attribute... attributes) {
        return asList(attributes);
    }

    private Attribute anAttribute(String name, String value) {
        return Attribute.builder(name, value).build();
    }
}
