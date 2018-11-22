package grey.smarthouse.database;

/**
 * Created by GREY on 21.04.2018.
 */

public class RelayDbSchema {
    public static final class RelayTable {
        public static final String NAME = "relays";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String DESCRIPTION = "description";
            public static final String NUMBER = "number";
            public static final String MODE = "mode";
            public static final String HOT ="hot";
            public static final String TOP_TEMP="top_temp";
            public static final String BOT_TEMP="bot_temp";
            public static final String PERIOD_TIME="period_time";
            public static final String DURATION_TIME="duration_time";
            public static final String SENS_NUM="sens_num";
        }
    }
}
