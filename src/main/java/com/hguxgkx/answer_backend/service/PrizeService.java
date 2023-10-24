package com.hguxgkx.answer_backend.service;

import com.hguxgkx.answer_backend.common.entity.R;

import java.util.List;

public interface PrizeService {
    /*
    * 初始化生成抽奖类
    * 根据试卷id，找到80分以上的，再通过奖品列表填充成完整的奖池
    * */
    R init(int id, List<String> prz);

    /*
    * 从中抽奖, 提供抽奖id和抽奖的学生id
    * */
    R extract(int id, String stu_id);

    /*
    * 获取当前用户有无资格
    * */
    R getQX(String sid);
}
