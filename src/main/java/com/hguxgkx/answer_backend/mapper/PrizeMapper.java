package com.hguxgkx.answer_backend.mapper;

import com.hguxgkx.answer_backend.pojo.Prize;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PrizeMapper {
    /*
    * 增加一个新的抽奖（一般跟试卷对应）
    * */
    int addPrize(Prize prize);

    /*
    * 更新抽奖状态
    * */
    int updatePrize(Prize prize);

    /*
    * 通过试卷id获得抽奖
    * */
    Prize getPrizeById(int id);

    /*
    * 获得所有的抽奖
    * */
    List<Prize> getAllPrize();
}