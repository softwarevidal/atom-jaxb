package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Objects;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;
import static fr.vidal.oss.jaxb.atom.core.validation.FeedValidation.allEntriesContainAuthor;
import static fr.vidal.oss.jaxb.atom.core.validation.FeedValidation.noDuplicatedLinks;
import static java.util.Collections.unmodifiableCollection;

@XmlRootElement(name = "feed")
@XmlType(propOrder = {"title", "subtitle", "links", "id", "authors", "contributors", "updateDate", "additionalElements", "generator", "icon", "logo", "rights", "entries"})
public class Feed {

    @XmlElement(name = "link", required = true)
    private final Collection<Link> links;
    @XmlElement(name = "title", required = true)
    private final String title;
    @XmlElement(name = "subtitle")
    private final String subtitle;
    @XmlElement(name = "id", required = true)
    private final String id;
    @XmlElement(name = "updated", required = true)
    private final Date updateDate;
    @XmlElement(name = "author")
    private final Collection<Author> authors;
    @XmlElement(name = "contributor")
    private final Collection<Contributor> contributors;
    @XmlAnyElement
    private final Collection<SimpleElement> additionalElements;
    @XmlElement(name = "entry")
    private final Collection<Entry> entries;
    @XmlElement(name = "generator")
    private final Generator generator;
    @XmlElement(name = "icon")
    private final Icon icon;
    @XmlElement(name = "logo")
    private final Logo logo;
    @XmlElement(name = "rights")
    private final String rights;

    @SuppressWarnings("unused")
    private Feed() {
        this(builder());
    }

    private Feed(Builder builder) {
        links = builder.links;
        title = builder.title;
        subtitle = builder.subtitle;
        id = builder.id;
        updateDate = builder.updateDate;
        authors = builder.authors;
        contributors = builder.contributors;
        additionalElements = builder.additionalElements;
        entries = builder.entries;
        generator = builder.generator;
        icon = builder.icon;
        logo = builder.logo;
        rights = builder.rights;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getId() {
        return id;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public Collection<Contributor> getContributors() {
        return unmodifiableCollection(contributors);
    }

    public Collection<Link> getLinks() {
        return unmodifiableCollection(links);
    }

    public Collection<Entry> getEntries() {
        return unmodifiableCollection(entries);
    }

    public Collection<SimpleElement> getAdditionalElements() {
        return unmodifiableCollection(additionalElements);
    }

    public Generator getGenerator() {
        return generator;
    }
    public Icon getIcon() {
        return icon;
    }

    public Logo getLogo() {
        return logo;
    }

    public String getRights() {
        return rights;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Feed other = (Feed) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Feed{" +
            "links=" + links +
            ", title='" + title + '\'' +
            ", subtitle='" + subtitle + '\'' +
            ", id='" + id + '\'' +
            ", updateDate=" + updateDate +
            ", authors=" + authors +
            ", contributors=" + contributors +
            ", additionalElements=" + additionalElements +
            ", entries=" + entries +
            ", generator=" + generator +
            ", icon=" + icon +
            ", logo=" + logo +
            ", rights=" + rights +
            '}';
    }

    public static class Builder {

        private String title;
        private String subtitle;
        private String id;
        private Date updateDate;
        private Collection<Author> authors = new LinkedHashSet<>();
        private Collection<Contributor> contributors = new LinkedHashSet<>();
        private Collection<Link> links = new LinkedHashSet<>();
        private Collection<SimpleElement> additionalElements = new LinkedHashSet<>();
        private Collection<Entry> entries = new LinkedHashSet<>();
        private Generator generator;
        private Icon icon;
        private Logo logo;
        public String rights;

        private Builder() {
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder addAuthor(Author author) {
            this.authors.add(author);
            return this;
        }

        public Builder withUpdateDate(Date updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public Builder addContributor(Contributor contributor) {
            this.contributors.add(contributor);
            return this;
        }

        public Builder addLink(Link link) {
            this.links.add(link);
            return this;
        }

        public Builder addSimpleElement(SimpleElement simpleElement) {
            this.additionalElements.add(simpleElement);
            return this;
        }

        public Builder addEntry(Entry entry) {
            this.entries.add(entry);
            return this;
        }

        public Builder withGenerator(Generator generator) {
            this.generator = generator;
            return this;
        }

        public Builder withIcon(Icon icon) {
            this.icon = icon;
            return this;
        }

        public Builder withLogo(Logo logo) {
            this.logo = logo;
            return this;
        }

        public Builder withRights(String rights) {
            this.rights = rights;
            return this;
        }

        public Feed build() {
            checkState(!authors.isEmpty() || allEntriesContainAuthor(entries), "author is mandatory");
            checkState(title != null, "title is mandatory");
            checkState(id != null, "id is mandatory");
            checkState(updateDate != null, "updateDate is mandatory");
            checkState(!links.isEmpty(), "links cannot be empty");
            checkState(noDuplicatedLinks(links), "links must not contain more than one link with the same rel `alternate` and same hreflang");
            return new Feed(this);
        }
    }
}
