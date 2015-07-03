package org.samaed.aae;

public class Symptom  {
    protected static final String DEFAULT_NAME = "Symptom";
    protected static final float DEFAULT_VALUE = 0f;
    protected static final int DEFAULT_COLOR = 0xfff79646;
    protected static final String DEFAULT_IMAGE = "measuring_tape";
    protected String uniqueName, image;
    protected float min, max, value;
    protected int color;

    public Symptom() {
        this(DEFAULT_NAME, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_VALUE, DEFAULT_COLOR, DEFAULT_IMAGE);
    }

    public Symptom(Symptom s) {
        this(s.getUniqueName(), s.getMin(), s.getMax(), s.getValue(), s.getColor(), s.getImage());
    }

    public Symptom(String name, float value) { this(name, value, value, value); }

    public Symptom(String name, float min, float max, float value) {
        this(name, min, max, value, DEFAULT_COLOR, DEFAULT_IMAGE);
    }

    public Symptom(String name, float value, int color, String image) {
        this(name, value, value, value, color, image);
    }

    public Symptom(String name, float min, float max, float value, int color, String image) {
        this.uniqueName = name;
        this.min = min;
        this.max = max;
        this.value = value;
        this.image = image;
        this.color = color;
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

    public float getValue() { return value; }

    public void setValue(float value) {
        this.value = value;
    }

    public void setMax(float value) {
        this.max = value;
    }

    public float getMax() {
        return this.max;
    }
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Symptom) && ((Symptom)object).getUniqueName().equals(getUniqueName()) && ((Symptom)object).getValue() >= getMin() && ((Symptom)object).getValue() <= getMax();
    }

    @Override
    public String toString() {
        return String.format("Symptom : %s:[%f,%f]",getUniqueName(),getMin(),getMax());
    }

    public boolean isValueInRange(){
        return value >= min && value <= max;
    }
}
