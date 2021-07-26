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

}
