package org.samaed.aae;

public class Disease {
    protected static final String DEFAULT_NAME = "Disease";
    protected String uniqueName;

    public Disease() {
        this(DEFAULT_NAME);
    }

    public Disease(String name) {
        this.uniqueName = name;
    }

    public void setUniqueName(String name) {
        this.uniqueName = name;
    }

    public String getUniqueName() {
        return this.uniqueName;
    }

    @Override
    public String toString() {
        return String.format("Disease : %s",getUniqueName());
    }
}
