package carzanodev.genuniv.microservices.common.util.time;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimestampUtility {

    public static Timestamp now() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp create(long millis) {
        return new Timestamp(millis);
    }

}
