package com.centennial.healthtab.object;

public class Bottle_Data {
    public int glass;
    public int type;
    public String unit;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bottle_Data{unit='");
        stringBuilder.append(this.unit);
        stringBuilder.append('\'');
        stringBuilder.append(", glass=");
        stringBuilder.append(this.glass);
        stringBuilder.append(", type=");
        stringBuilder.append(this.type);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public int getGlass() {
        return this.glass;
    }

    public void setGlass(int i) {
        this.glass = i;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String str) {
        this.unit = str;
    }
}
