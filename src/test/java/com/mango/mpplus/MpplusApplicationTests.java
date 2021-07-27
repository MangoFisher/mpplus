package com.mango.mpplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
    //测试条件选择
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

    //根据id删除
    @Test
    public void testDeleteById() {
        int result = userMapper.deleteById(9L);
        System.out.println(result);
    }

    //批量删除
    @Test
    public void testDeleteBatchIds() {
        int result = userMapper.deleteBatchIds(Arrays.asList(6L, 7L, 8L));
        System.out.println(result);
    }

    //简单的条件查询删除
    @Test
    public void testDeleteByMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "helen");
        map.put("age", 18);
        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }

    /**
     * 测试 逻辑删除
     */
    @Test
    public void testLogicDelete() {
        int result = userMapper.deleteById(10L);
        System.out.println(result);
    }

    /**
     * 实现一些复杂条件查询
     */
    @Test
    public void testDelete() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("name")
                    .ge("age", 20)
                    .isNotNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println(result);
    }

    //seletOne返回的是一条实体记录，当出现多条时会报错
    @Test
    public void testSelectOne() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "Tom");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void testSelectCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age", 20, 30);
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    @Test
    public void testSelectList02() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 4);
        map.put("name", "Sandy");
        map.put("age", 21);
        queryWrapper.allEq(map);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectMaps() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.notLike("name", "2")
                    .likeRight("email", "test3");
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    //子查询
    @Test
    public void testSelectObjs() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //queryWrapper.in("id", 1, 2, 3);
        queryWrapper.between("age", 21, 25)
                    .inSql("id", "select id from user where id > 4");
        List<Object> objects = userMapper.selectObjs(queryWrapper);//返回值是Object列表
        objects.forEach(System.out::println);
    }

    @Test
    public void testUpdate1() {
        //修改值
        User user = new User();
        user.setAge(99);
        user.setName("Andy");
        //修改条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name", "h")
                .or()
                .between("age", 20, 30);
        int result = userMapper.update(user, userUpdateWrapper);
        System.out.println(result);
    }

    @Test
    public void testUpdate2() {
        //修改值
        User user = new User();
        user.setAge(100);
        user.setName("testname001");
        //修改条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name", "h")
                .or(i -> i.eq("name", "李白").ne("age", 20));
        int result = userMapper.update(user, userUpdateWrapper);
        System.out.println(result);
    }

    @Test
    public void testSelectListOrderBy() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testSelectListLast() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("limit 1");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    //指定要查询的列
    @Test
    public void testSelectListColumn() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("name", "age");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void testUpdateSet() {
        //修改值
        User user = new User();
        user.setAge(99);
        //修改条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name", "h")
                .set("name", "老李头")//除了可以查询还可以使用set设置修改的字段
                .setSql(" email = '123@qq.com'");//可以有子查询
        int result = userMapper.update(user, userUpdateWrapper);
    }
}
