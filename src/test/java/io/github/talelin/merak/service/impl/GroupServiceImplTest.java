package io.github.talelin.merak.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.talelin.merak.bo.GroupPermissionBO;
import io.github.talelin.merak.mapper.*;
import io.github.talelin.merak.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
@Slf4j
@ActiveProfiles("test")
public class GroupServiceImplTest {

    @Autowired
    private GroupServiceImpl groupService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private GroupPermissionMapper groupPermissionMapper;

    @Autowired
    private SqlSession sqlSession;

    @Value("${group.root.id}")
    private Long rootGroupId;

    public Long mockData() {
        UserDO user = UserDO.builder().nickname("pedro大大").username("pedro大大").build();
        GroupDO group1 = GroupDO.builder().name("测试分组12").info("just for test").build();
        GroupDO group2 = GroupDO.builder().name("测试分组11").info("just for test").build();
        userMapper.insert(user);
        groupMapper.insert(group1);
        groupMapper.insert(group2);
        List<UserGroupDO> relations = new ArrayList<>();
        UserGroupDO relation1 = new UserGroupDO(user.getId(), group1.getId());
        UserGroupDO relation2 = new UserGroupDO(user.getId(), group2.getId());
        relations.add(relation1);
        relations.add(relation2);
        userGroupMapper.insertBatch(relations);
        return user.getId();
    }

    public GroupDO mockData1() {
        GroupDO group = GroupDO.builder().name("测试分组12").info("just for test").build();
        groupMapper.insert(group);
        return group;
    }

    public Long mockData2() {
        GroupDO group = GroupDO.builder().name("测试分组1").info("just for test").build();
        PermissionDO permission1 = PermissionDO.builder().name("权限1").module("炉石传说").build();
        PermissionDO permission2 = PermissionDO.builder().name("权限2").module("炉石传说").build();
        groupMapper.insert(group);
        permissionMapper.insert(permission1);
        permissionMapper.insert(permission2);
        List<GroupPermissionDO> relations = new ArrayList<>();
        GroupPermissionDO relation1 = new GroupPermissionDO(group.getId(), permission1.getId());
        GroupPermissionDO relation2 = new GroupPermissionDO(group.getId(), permission2.getId());
        relations.add(relation1);
        relations.add(relation2);
        groupPermissionMapper.insertBatch(relations);
        return group.getId();
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getUserGroupsByUserId() {
        Long id = mockData();
        List<GroupDO> groups = groupService.getUserGroupsByUserId(id);
        assertTrue(groups.size() > 0);
        boolean anyMatch = groups.stream().anyMatch(it -> it.getName().equals("测试分组12"));
        assertTrue(anyMatch);
    }

    @Test
    public void getUserGroupIdsByUserId() {
        Long id = mockData();
        List<Long> ids = groupService.getUserGroupIdsByUserId(id);
        assertTrue(ids.size() > 0);
    }

    @Test
    public void findGroupsByPage() {
        Long id = mockData();
        IPage<GroupDO> groups = groupService.getGroupPage(0, 10);
        assertTrue(groups.getTotal() > 0);
        assertTrue(groups.getRecords().size() > 0);
        assertTrue(groups.getCurrent() == 0);
        boolean anyMatch = groups.getRecords().stream().anyMatch(it -> it.getName().equals("测试分组12"));
        assertTrue(anyMatch);
    }

    @Test
    public void checkGroupExistById() {
        GroupDO group = mockData1();
        boolean exist = groupService.checkGroupExistById(group.getId());
        assertTrue(exist);
    }

    @Test
    public void getGroupAndPermissions() {
        Long id = mockData2();
        GroupPermissionBO groupAndPermissions = groupService.getGroupAndPermissions(id);
        assertTrue(groupAndPermissions.getName().equals("测试分组1"));
        boolean anyMatch = groupAndPermissions.getPermissions().stream().anyMatch(permission -> {
            PermissionDO permission1 = (PermissionDO) permission;
            return permission1.getName().equals("权限2");
        });
        assertTrue(anyMatch);
    }

    @Test
    public void checkGroupExistByName() {
        GroupDO group = mockData1();
        boolean exist = groupService.checkGroupExistByName(group.getName());
        assertTrue(exist);
    }

    @Test
    public void checkIsRootByUserId() {
        long userId = mockData();
        boolean exist = groupService.checkIsRootByUserId(userId);
        assertFalse(exist);
    }
}