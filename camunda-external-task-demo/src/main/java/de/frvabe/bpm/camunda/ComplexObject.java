package de.frvabe.bpm.camunda;

import java.io.Serializable;

public class ComplexObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String textValue;
    private int integerValue;
    private boolean booleanValue;

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public int getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

}
