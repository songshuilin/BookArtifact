package event;

/**
 * Created by 陈师表 on 2016/11/23.
 */

public class NightMode {
    private boolean isNight;
    public NightMode(boolean isNight){
        this.isNight=isNight;
    }
    public boolean getMode_(){
        return isNight;
    }
}
