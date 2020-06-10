package com.ouver.o2o.enums;

public enum  AwardStateEnum {
    CHECK(0, "审核中"), OFFLINE(-1, "非法"), SUCCESS(1, "操作成功"), PASS(2, "通过认证"), INNER_ERROR(
            -1001, "操作失败"), NULL_AwardID(-1002, "AwardId为空"), NULL_Award_INFO(
            -1003, "传入了空的信息");

    private int state;
    private String stateInfo;

    private AwardStateEnum(int state,String stateInfo){
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static AwardStateEnum stateOf(int state){
        for(AwardStateEnum stateEnum : values()){
            if(stateEnum.getState() == state){
                return stateEnum;
            }
        }
        return null;
    }
}
