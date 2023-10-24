package com.hguxgkx.answer_backend.service.serviceImpl;

import com.hguxgkx.answer_backend.common.entity.R;
import com.hguxgkx.answer_backend.mapper.ExamMapper;
import com.hguxgkx.answer_backend.mapper.PaperMapper;
import com.hguxgkx.answer_backend.mapper.PrizeMapper;
import com.hguxgkx.answer_backend.mapper.UserMapper;
import com.hguxgkx.answer_backend.pojo.Prize;
import com.hguxgkx.answer_backend.service.PaperService;
import com.hguxgkx.answer_backend.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PrizeServiceImpl implements PrizeService {
    @Autowired
    PaperMapper paperMapper;
    @Autowired
    ExamMapper examMapper;
    @Autowired
    PrizeMapper prizeMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public R init(int id, List<String> prz) {
        if(prz==null){
            return R.Error("奖品列表为空");
        }
        if(prizeMapper.getPrizeById(id)!=null){
            return R.Error("抽奖已经创建");
        }
        if(paperMapper.getPaperById(id)==null){
            return R.Error("试卷不存在");
        }
        if(paperMapper.getPaperById(id).getPublish()!=2){
            return R.Error("考试未结束");
        }

        List<String> stu = new ArrayList<>(  );
        for (Map<String, Object> map : examMapper.getExamByPaperId(id)) {
            if((int)map.get("score")>=80){
                stu.add((String) map.get("student_id"));
            }
        }
        int length = stu.size();
        List<String> totalPrz = new ArrayList<>(  );
        if(length==prz.size()){
            totalPrz = prz;
        }else if(length>prz.size()){
            totalPrz.addAll(prz);
            for (int i = 0; i < length - prz.size( ); i++) {
                totalPrz.add("谢谢参与");
            }
        }else{
            Collections.shuffle(prz);
            for (int i=0;i<length;i++) {
                totalPrz.add(prz.get(i));
            }
        }
        Collections.shuffle(totalPrz);
        Prize prize = new Prize(id, stu, prz, totalPrz, new ArrayList<>(  ));
        prizeMapper.addPrize(prize);
        System.out.println(prizeMapper.getAllPrize( ));
        return R.OK(prize);
    }

    @Override
    public R extract(int id, String stu_id) {
        if(prizeMapper.getPrizeById(id)==null){
            return R.Error("抽奖还未创建");
        }
        Prize prize = prizeMapper.getPrizeById(id);
        List<String> stu = prize.getStuObject( );
        List<String> totalPrz = prize.getTotalPrzObject( );
        if(!stu.contains(stu_id)){
            return R.Error("用户不具有抽奖资格或已经抽过奖");
        }else{
            stu.remove(stu_id);
            String r = totalPrz.remove(0);
            Collections.shuffle(totalPrz);
            Map<String,String> map = new HashMap<>(  );
            map.put("sid", stu_id);
            map.put("prz", r);
            map.put("name", userMapper.getUserById(stu_id).getName());
            prize.addLog(map);
            prizeMapper.updatePrize(prize);
//            System.out.println(prize.getLogObject() );
//            System.out.println(prizeMapper.getAllPrize() );
            return R.OK(r);
        }
    }

    @Override
    public R getQX(String sid) {
        List<R> list = new ArrayList<>(  );
        R re = new R();
        re.put("list", list);
        re.put("name", userMapper.getUserById(sid).getName());
        for (Prize prize : prizeMapper.getAllPrize( )) {
            if(prize.getStuObject().contains(sid)){
                R r = new R();
                String pName = paperMapper.getPaperById(prize.getId()).getPaperName();
                int id = paperMapper.getPaperById(prize.getId()).getId();
                r.put("name", pName);
                r.put("id", id);
                list.add(r);
            }
        }
        return R.OK(re);
    }
}
