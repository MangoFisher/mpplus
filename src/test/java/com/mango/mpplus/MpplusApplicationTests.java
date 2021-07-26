package com.mango.mpplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mango.mpplus.entity.User;
import com.mango.mpplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //根据ID查询
    @Test
    public void testSelectById() {
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    //根据多个id批量查询
    @Test
    public void testSelectByIds() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    //通过map封装查询条件
    @Test
    public void testSelectByMap() {
        HashMap<String, Object> map = new HashMap<>();
        //注意：map中的key对应的是数据库中的列名。例如数据库user_id，实体类是userId，这时map的key需
        //要填写user_id
        map.put("id", 1);
        map.put("name", "helen");
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //测试分页
    @Test
    public void testSelectPage() {
        //两个参数相当于currentPage、pageSize
        Page<User> page = new Page<>(1,5);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

    @Test
    public void testSelectMapsPage() {
        Page<User> page = new Page<>(1, 5);
        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(page, null);
        mapIPage.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

}
