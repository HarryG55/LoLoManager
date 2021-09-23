package dao;

import POJO.User;
import org.apache.ibatis.annotations.Select;

/**
 * @author YachenGuo
 * @ClassName LoginDao
 * @Description
 * @createTime 2021 09 07 16:56
 */
public interface LoginDao {

    public User getUserByName(String username);

    public User getUserByPassword(String password);


}
