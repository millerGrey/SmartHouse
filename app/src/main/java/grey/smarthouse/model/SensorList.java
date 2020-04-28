package grey.smarthouse.model;

import java.util.List;

public class SensorList {
    static List<String> list;
    public static  void setList(List<String> lst){
        list =lst;
    }
    public static List<String> getList(){
        return list;
    }
}
