package com.centennial.healthtab.object;

public class Chart_Data {
    public String Date;
    public String DrinkWater;
    public String TotalWater;
    public String chartDate;

    public String getDrinkWater() {
        return this.DrinkWater;
    }

    public void setDrinkWater(String str) {
        this.DrinkWater = str;
    }

    public String getTotalWater() {
        return this.TotalWater;
    }

    public void setTotalWater(String str) {
        this.TotalWater = str;
    }

    public String getDate() {
        return this.Date;
    }

    public void setDate(String str) {
        this.Date = str;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Chart_Data{DrinkWater='");
        stringBuilder.append(this.DrinkWater);
        stringBuilder.append('\'');
        stringBuilder.append(", TotalWater='");
        stringBuilder.append(this.TotalWater);
        stringBuilder.append('\'');
        stringBuilder.append(", Date='");
        stringBuilder.append(this.Date);
        stringBuilder.append('\'');
        stringBuilder.append(", chartDate='");
        stringBuilder.append(this.chartDate);
        stringBuilder.append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public String getChartDate() {
        return this.chartDate;
    }

    public void setChartDate(String str) {
        this.chartDate = str;
    }
}
