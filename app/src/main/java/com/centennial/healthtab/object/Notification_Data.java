package com.centennial.healthtab.object;

public class Notification_Data {
    public int ID;
    public String date;
    public boolean onOffNotification;

    public String getDate() {
        return this.date;
    }

    public void setDate(String str) {
        this.date = str;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int i) {
        this.ID = i;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Notification_Data{date='");
        stringBuilder.append(this.date);
        stringBuilder.append('\'');
        stringBuilder.append(", ID=");
        stringBuilder.append(this.ID);
        stringBuilder.append(", onOffNotification=");
        stringBuilder.append(this.onOffNotification);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public boolean isOnOffNotification() {
        return this.onOffNotification;
    }

    public void setOnOffNotification(boolean z) {
        this.onOffNotification = z;
    }

    public boolean equals(Object obj) {
        return obj != null && (obj instanceof Notification_Data) && getID() == ((Notification_Data) obj).getID();
    }
}
