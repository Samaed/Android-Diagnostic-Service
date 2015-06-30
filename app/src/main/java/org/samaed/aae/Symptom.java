package org.samaed.aae;

public class Symptom  {
    protected static final String DEFAULT_NAME = "Symptom";
    protected static final float DEFAULT_VALUE = 0f;
    protected String uniqueName;
    protected float value;
    protected int color = 0xfff79646;
    protected String image = "measuring_tape";

    public Symptom() {
        this(DEFAULT_NAME, DEFAULT_VALUE);
    }

    public Symptom(String name, float value) {
        this.uniqueName = name;
        this.value = value;
    }

    public Symptom(String name, float value, int color, String image) {
        this.uniqueName = name;
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

    public void setValue(float value) {
        this.value = value;
    }

    public float getValue() {
        return this.value;
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
    public String toString() {
        return String.format("Symptom : %s@%f",getUniqueName(),getValue());
    }
}
