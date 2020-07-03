package com.mvtech.mess.constant;


/**
 * @Description: 是否状态枚举,是yes,否no
 * @author:
 * @date: 2020/1/6 10:57
 */
public enum IsEnum {
    Yes(1, "1"), No(0, "0");
    private Integer value;
    private String description;

    private IsEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取指定value对应的description
     *
     * @param targetValue
     * @return
     */
    public static String getDescription(Integer targetValue) {
        if (targetValue == null)
            return "";
        for (IsEnum statusEnum : IsEnum.values()) {
            if (statusEnum.getValue().equals(targetValue))
                return statusEnum.getDescription();
        }
        return "";
    }

    public static Integer getValue(String description) {
        if (description == null)
            return null;
        for (IsEnum statusEnum : IsEnum.values()) {
            if (statusEnum.getDescription().equals(description))
                return statusEnum.getValue();
        }
        return null;
    }

    public static boolean contains(String test) {
        for (IsEnum statusEnum : IsEnum.values()) {
            if (statusEnum.getDescription().equals(test)) {
                return true;
            }
        }
        return false;
    }

}
