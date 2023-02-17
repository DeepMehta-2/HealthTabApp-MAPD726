package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper;

public class WaterIntake {
    int cup_flag;
    int cup_id;
    int cup_size;
    int new_cup_size;
    boolean nextTmeStatus = false;
    String water_date;
    int water_id;
    String water_time;

    public boolean isNextTmeStatus() {
        return this.nextTmeStatus;
    }

    public void setNextTmeStatus(boolean z) {
        this.nextTmeStatus = z;
    }

    public int getNew_cup_size() {
        return this.new_cup_size;
    }

    public void setNew_cup_size(int i) {
        this.new_cup_size = i;
    }

    public int getCup_flag() {
        return this.cup_flag;
    }

    public void setCup_flag(int i) {
        this.cup_flag = i;
    }

    public WaterIntake(int i, String str, String str2, int i2, int i3, int i4, int i5) {
        this.water_id = i;
        this.water_date = str;
        this.water_time = str2;
        this.cup_id = i2;
        this.cup_size = i3;
        this.cup_flag = i4;
        this.new_cup_size = i5;
    }
    public WaterIntake(){}

    public int getWater_id() {
        return this.water_id;
    }

    public void setWater_id(int i) {
        this.water_id = i;
    }

    public String getWater_date() {
        return this.water_date;
    }

    public void setWater_date(String str) {
        this.water_date = str;
    }

    public String getWater_time() {
        return this.water_time;
    }

    public void setWater_time(String str) {
        this.water_time = str;
    }

    public int getCup_id() {
        return this.cup_id;
    }

    public void setCup_id(int i) {
        this.cup_id = i;
    }

    public int getCup_size() {
        return this.cup_size;
    }

    public void setCup_size(int i) {
        this.cup_size = i;
    }
}
