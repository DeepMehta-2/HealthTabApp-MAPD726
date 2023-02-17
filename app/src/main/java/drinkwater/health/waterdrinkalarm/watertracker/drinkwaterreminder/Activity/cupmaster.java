package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

public class cupmaster {
    int cup_id;
    int cup_size;
    int image_id;
    int is_current;

    public int getIs_current() {
        return this.is_current;
    }

    public void setIs_current(int i) {
        this.is_current = i;
    }

    cupmaster(int i, int i2, int i3, int i4) {
        this.cup_id = i;
        this.cup_size = i2;
        this.image_id = i3;
        this.is_current = i4;
    }

    cupmaster() {
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

    public int getImage_id() {
        return this.image_id;
    }

    public void setImage_id(int i) {
        this.image_id = i;
    }
}
