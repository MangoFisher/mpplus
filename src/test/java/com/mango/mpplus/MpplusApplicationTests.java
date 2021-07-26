package com.mango.mpplus;

import com.mango.mpplus.entity.User;
import com.mango.mpplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class MpplusApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        System.out.println("----- selectAll method test ------\"");
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    //添加用户
    @Test
    public void addUser() {
        User user = new User();
        user.setName("name1");
        user.setAge(100);
        user.setEmail("test1@qq.com");

        int insert = userMapper.insert(user);
        System.out.println("insert:" + insert);
    }

    //修改用户
    @Test
    public void updateUser() {
        User user = new User();
        user.setId(2L);
        user.setName("2222");
        userMapper.updateById(user);
    }

    //测试乐观锁修改成功
    @Test
    public void testOptimisticLocker() {
        User user = userMapper.selectById(9L);
        user.setName("helen");
        user.setEmail("helen@qq.com");
        userMapper.updateById(user);
    }

    //测试乐观锁修改失败
    @Test
    public void testOptimisticLockerFail() {
        //查询
        User user = userMapper.selectById(1L);
        //修改数据
        user.setName("Helen Yao1");
        user.setEmail("helen@qq.com1");
        //模拟取出数据后，数据库中version实际数据比取出的值大，即已被其它线程修改并更新了version
        user.setVersion(user.getVersion() - 1);
        //执行更新
        userMapper.updateById(user);
    }

}
