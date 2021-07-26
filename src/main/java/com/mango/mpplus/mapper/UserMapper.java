package com.mango.mpplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mango.mpplus.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}