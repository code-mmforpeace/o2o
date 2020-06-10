package com.ouver.o2o.service.impl;

import com.ouver.o2o.dao.AwardDao;
import com.ouver.o2o.domain.Award;
import com.ouver.o2o.domain.ImageHolder;
import com.ouver.o2o.dto.AwardExecution;
import com.ouver.o2o.enums.AwardStateEnum;
import com.ouver.o2o.exceptions.AwardOperationException;
import com.ouver.o2o.service.AwardService;
import com.ouver.o2o.utils.ImageUtil;
import com.ouver.o2o.utils.PageCalculator;
import com.ouver.o2o.utils.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AwardServiceImpl implements AwardService {

    @Autowired
    private AwardDao awardDao;

    @Override
    public AwardExecution queryAwardList(Award awardCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Award> awardList = awardDao.queryAwardList(awardCondition,
                rowIndex, pageSize);
        int count = awardDao.queryAwardCount(awardCondition);
        AwardExecution ae = new AwardExecution();
        ae.setAwardList(awardList);
        ae.setCount(count);
        return ae;
    }

    @Override
    public int queryAwardCount(Award awardCondition) {
        return awardDao.queryAwardCount(awardCondition);
    }

    @Override
    public Award queryAwardByAwardId(long awardId) {
        return awardDao.queryAwardByAwardId(awardId);
    }

    @Override
    @Transactional
    public AwardExecution insertAward(Award award, ImageHolder thumbnail) throws AwardOperationException {
        if(award != null){
            award.setEnableStatus(1);
            award.setCreateTime(new Date());
            award.setLastEditTime(new Date());
            if(thumbnail != null){
                addThumbnail(award, thumbnail);
            }
            try{
            int i = awardDao.insertAward(award);
            if( i <= 0){
                throw new AwardOperationException("奖品创建失败！");
            }else {
                return new AwardExecution(AwardStateEnum.SUCCESS);
            }
            }catch (Exception e){
                throw new AwardOperationException(e.getMessage());
            }
        }else {
            return new AwardExecution(AwardStateEnum.NULL_Award_INFO);
        }
    }

    @Override
    public AwardExecution updateAward(Award award,ImageHolder thumbnail) throws AwardOperationException {
        if(award != null && thumbnail != null){
            award.setLastEditTime(new Date());
            Award award1 = awardDao.queryAwardByAwardId(award.getAwardId());
            if(award1.getAwardImg() != null){
                ImageUtil.deleteFileOrPath(award1.getAwardImg());
            }
            addThumbnail(award,thumbnail);
        }
        try{
            int i = awardDao.updateAward(award);
            if(i <= 0){
                throw new AwardOperationException("奖品更新失败！");
            }else {
                return new AwardExecution(AwardStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new AwardOperationException("奖品更新失败！");
        }
    }

    @Override
    public AwardExecution deleteAward(long awardId,long shopId) throws AwardOperationException{
        try {
            int i = awardDao.deleteAward(awardId,shopId);
            if(i <= 0){
                throw new AwardOperationException("删除失败");
            }else {
                return new AwardExecution(AwardStateEnum.SUCCESS);
            }
        }catch (Exception e){
            throw new AwardOperationException("删除失败"+e.getMessage());
        }
    }
    private void addThumbnail(Award award, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(award.getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        award.setAwardImg(thumbnailAddr);
    }
}
