package model;

public abstract class DBObject {
    //
    //
    // class that identifies if object is writable/readable or not
    //
    //

    private boolean active = true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract String getInfo();

}
