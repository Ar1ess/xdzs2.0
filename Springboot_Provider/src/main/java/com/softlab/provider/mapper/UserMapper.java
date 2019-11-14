package com.softlab.provider.mapper;

import com.softlab.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LiXiwen
 * @date 2019/11/8 20:28
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 按照pace降序方式查询
     * @return
     */
    List<User> selectUserOrderByPace();

    /**
     * 根据user_pace获取用户数量
     * @return
     */
    int getUserSize();

    /**
     * 查询用户
     * @param user
     * @return
     */
    List<User> selectUser(User user);

    /**
     * 查询用户所处排名
     * @param user
     * @return
     */
    int selectUserRank(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);
}
