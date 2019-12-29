package carzanodev.genuniv.microservices.common.util.time;

import java.sql.Timestamp;

public final class TimeUtility {

    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

}
