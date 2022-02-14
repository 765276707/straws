package com.gitee.pristine.web.service.impl;

import com.gitee.pristine.domain.base.TextCondition;
import com.gitee.pristine.domain.dto.ModifyPasswordDTO;
import com.gitee.pristine.domain.dto.UserDTO;
import com.gitee.pristine.domain.entity.Migrate;
import com.gitee.pristine.domain.entity.Script;
import com.gitee.pristine.domain.entity.User;
import com.gitee.pristine.security.enc.PasswordEncoder;
import com.gitee.pristine.security.enc.SaltGenerator;
import com.gitee.pristine.web.ex.ServiceException;
import com.gitee.pristine.web.mapper.UserMapper;
import com.gitee.pristine.web.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Pristine Xu
 * @date 2022/2/3 5:49
 * @description:
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    // 默认密码是： 666666
    private final static String DEFAULT_USER_PASSWORD = "666666";

    @Override
    public User getById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getByUsername(String username) {
        User condition = new User();
        condition.setUsername(username);
        return userMapper.selectOne(condition);
    }

    @Override
    public List<User> getList(TextCondition condition) {
        Example example = new Example(User.class);
        if (!StringUtils.isEmpty(condition.getSearchText())) {
            example.createCriteria()
                    .andLike("username", condition.like());
        }
        return userMapper.selectByExample(example);
    }

    @Override
    @Transactional
    public int insertEntity(UserDTO entity) {
        // 判断name是否存在
        User var = this.getByUsername(entity.getUsername());
        if (var != null) {
            throw new ServiceException("用户名已被使用");
        }
        // 初始化密码, 默认密码是： 666666
        String salt = SaltGenerator.generate(6);
        String encodedPwd = passwordEncoder.encode(DEFAULT_USER_PASSWORD, salt);
        // 赋值
        User user = new User();
        BeanUtils.copyProperties(entity, user);
        user.setPassword(encodedPwd);
        user.setSalt(salt);
        user.setCreateTime(new Date());
        // 插入数据库
        return userMapper.insertSelective(user);
    }

    @Override
    @Transactional
    public int updateEntity(UserDTO entity) {
        // 判断记录是否存在
        User var1 = this.getById(entity.getId());
        if (var1 == null) {
            throw new ServiceException("修改的用户不存在");
        }
        // 判断名称是否已被使用
        User var2 = this.getByUsername(entity.getUsername());
        if (var2!=null && !var2.getId().equals(entity.getId())) {
            throw new ServiceException("用户名已被使用");
        }
        // 赋值
        BeanUtils.copyProperties(entity, var1);
        var1.setUpdateTime(new Date());
        // 更新数据库
        return userMapper.updateByPrimaryKeySelective(var1);
    }

    @Override
    @Transactional
    public int modifyPassword(String username, ModifyPasswordDTO modifyPasswordDTO) {
        // 查询用户
        User user = this.getByUsername(username);
        if (user == null) {
            throw new ServiceException("当前用户不存在");
        }
        // 校验旧的密码
        if (!passwordEncoder.match(user.getPassword(), modifyPasswordDTO.getOldPassword(), user.getSalt())) {
            throw new ServiceException("用户原始密码错误");
        }
        // 变更密码
        String encodedPwd = passwordEncoder.encode(modifyPasswordDTO.getNewPassword(), user.getSalt());
        // 赋值
        user.setPassword(encodedPwd);
        user.setUpdateTime(new Date());
        // 插入数据库
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public int resetPassword(Long userId) {
        // 判断记录是否存在
        User user = this.getById(userId);
        if (user == null) {
            throw new ServiceException("修改的用户不存在");
        }
        // 重置初始化密码, 默认密码是： 666666
        String encodedPwd = passwordEncoder.encode(DEFAULT_USER_PASSWORD, user.getSalt());
        // 赋值
        user.setPassword(encodedPwd);
        user.setUpdateTime(new Date());
        // 插入数据库
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public int deleteById(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

}
