package com.ouver.o2o.dto;

import com.ouver.o2o.domain.Award;
import com.ouver.o2o.enums.AwardStateEnum;

import java.util.List;

/**
 * award 的增强实体类
 */
public class AwardExecution {
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //奖品的数量
    private int count;

    //操作的奖品（增删改查）
    private Award award;

    //奖品列表（查询奖品列表的时候用到）
    private List<Award> awardList;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public List<Award> getAwardList() {
        return awardList;
    }

    public void setAwardList(List<Award> awardList) {
        this.awardList = awardList;
    }

    public AwardExecution() {
    }

    //生成操作成功或失败的构造器
    public AwardExecution(AwardStateEnum stateEnum){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }
    public AwardExecution(AwardStateEnum stateEnum,Award award){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.award = award;
    }
    public AwardExecution(AwardStateEnum stateEnum,List<Award> awardList){
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.awardList = awardList;
    }
}
