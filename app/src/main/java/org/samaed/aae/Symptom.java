package org.samaed.aae;

public class Symptom  {
    protected static final String DEFAULT_NAME = "Symptom";
    protected static final float DEFAULT_VALUE = 0f;
    protected String uniqueName;
    protected float value;

    public Symptom() {
        this(DEFAULT_NAME, DEFAULT_VALUE);
    }

    public Symptom(String name, float value) {
        this.uniqueName = name;
        this.value = value;
    }

    public void setUniqueName(String name) {
        this.uniqueName = name;
    }

    public String getUniqueName() {
        return this.uniqueName;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format("Symptom : %s@%f",getUniqueName(),getValue());
    }
}
