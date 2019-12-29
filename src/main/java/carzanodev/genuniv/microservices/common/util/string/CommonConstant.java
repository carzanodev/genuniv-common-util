package carzanodev.genuniv.microservices.common.util.string;

import lombok.Getter;

@Getter
public enum CommonConstant {

    DB_MULTIDATA_DELIMITER(",");

    private String str;

    CommonConstant(String str) {
        this.str = str;
    }

}
