package org.samaed.aae;

public class Symptom  {
    protected static final String DEFAULT_NAME = "Symptom";
    protected static final float DEFAULT_VALUE = 0f;
    protected String uniqueName;
    protected float min, max;

    public Symptom() {
        this(DEFAULT_NAME, DEFAULT_VALUE, DEFAULT_VALUE);
    }

    public Symptom(String name, float min, float max) {
        this.uniqueName = name;
        this.min = min;
        this.max = max;
    }

    public void setUniqueName(String name) {
        this.uniqueName = name;
    }

    public String getUniqueName() {
        return this.uniqueName;
    }

    public void setMin(float value) {
        this.min = value;
    }

    public float getMin() {
        return this.min;
    }

    public void setMax(float value) {
        this.max = value;
    }

    public float getMax() {
        return this.max;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Symptom) && ((Symptom)object).getMin() >= getMin() && ((Symptom)object).getMax() <= getMax();
    }

    @Override
    public String toString() {
        return String.format("Symptom : %s:[%f,%f]",getUniqueName(),getMin(),getMax());
    }
}
