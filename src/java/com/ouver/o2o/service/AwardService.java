package com.ouver.o2o.service;

import com.ouver.o2o.domain.Award;
import com.ouver.o2o.domain.ImageHolder;
import com.ouver.o2o.dto.AwardExecution;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 奖品的业务层接口
 */
public interface AwardService {
    /**
     * 根据传入的查询条件，返回奖品list
     * @param awardCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    AwardExecution queryAwardList(@Param("awardCondition") Award awardCondition,
                               @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 返回奖品总数
     * @param awardCondition
     * @return
     */
    int queryAwardCount(@Param("awardCondition") Award awardCondition);

    /**
     * 根据id查询奖品
     * @param awardId
     * @return
     */
    Award queryAwardByAwardId(long awardId);

    /**
     * 添加
     * @param award
     * @return
     */
    AwardExecution insertAward(Award award, ImageHolder thumbnail);

    /**
     * 修改
     * @param award
     * @return
     */
    AwardExecution updateAward(Award award,ImageHolder thumbnail);

    /**
     * 删除
     * @param awardId
     * @param shopId
     * @return
     */
    AwardExecution deleteAward(long awardId,long shopId);
}
