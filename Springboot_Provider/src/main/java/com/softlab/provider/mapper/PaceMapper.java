package com.softlab.provider.mapper;

import com.softlab.common.model.Pace;
import com.softlab.common.model.Run;
import com.softlab.common.model.vo.PaceVo;
import com.softlab.common.model.vo.RunVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LiXiwen
 * @date 2019/11/11 12:55
 */
@Mapper
@Repository
public interface PaceMapper {

    /**
     * 添加单个pace
     * @param pace
     * @return
     */
    boolean addPace(Pace pace);

    /**
     * 按照rank降序查询
     * @return
     */
    List<PaceVo> selectPaceDescRank();

    /**
     * 更新pace表
     * @param pace
     * @return
     */
    boolean updatePace(Pace pace);

    /**
     * 查询paceVo，用作返回值的全部信息
     * @param pace
     * @return
     */
    List<PaceVo> selectPaceVo(Pace pace);


    List<PaceVo> selectPaceVo1(PaceVo paceVo);

    List<Pace> selectPace1();

    boolean addRunData(Run run);

    List<RunVo> selectRunData();

    List<PaceVo> init1();

}
