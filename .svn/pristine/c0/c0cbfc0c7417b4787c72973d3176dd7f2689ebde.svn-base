package com.ouver.o2o.dao;

import com.ouver.o2o.domain.Award;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 奖品
 */
public interface AwardDao {
    /**
     * 根据传入的查询条件，返回奖品list
     * @param awardCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Award> queryAwardList(@Param("awardCondition") Award awardCondition,
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
    int insertAward(Award award);

    /**
     * 修改
     * @param award
     * @return
     */
    int updateAward(Award award);

    /**
     * 删除
     * @param awardId
     * @param shopId
     * @return
     */
    int deleteAward(long awardId,long shopId);
}
