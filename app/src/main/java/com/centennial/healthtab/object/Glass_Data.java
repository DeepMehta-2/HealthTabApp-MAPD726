package com.centennial.healthtab.object;

public class Glass_Data {
    public int glass;
    private String hour;
    private String minute;
    public String time;
    public String unit;

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String str) {
        this.unit = str;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String str) {
        this.time = str;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Glass_Data{unit='");
        stringBuilder.append(this.unit);
        stringBuilder.append('\'');
        stringBuilder.append(", time='");
        stringBuilder.append(this.time);
        stringBuilder.append('\'');
        stringBuilder.append(", glass=");
        stringBuilder.append(this.glass);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public int getGlass() {
        return this.glass;
    }

    public void setGlass(int i) {
        this.glass = i;
    }

    public String getHour() {
        return this.hour;
    }

    public void setHour(String str) {
        this.hour = str;
    }

    public String getMinute() {
        return this.minute;
    }

    public void setMinute(String str) {
        this.minute = str;
    }
}
