package io.github.talelin.merak.service;

import io.github.talelin.merak.model.UserIdentityDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pedro@TaleLin
 */
public interface UserIdentityService extends IService<UserIdentityDO> {

    /**
     * 新建用户认证信息
     *
     * @param userId       用户id
     * @param identityType 认证类型
     * @param identifier   标识
     * @param credential   凭证
     * @return 用户认证
     */
    UserIdentityDO createIdentity(Long userId,
                                  String identityType,
                                  String identifier,
                                  String credential);

    /**
     * 新建用户认证信息
     *
     * @param userIdentity 用户认证信息
     * @return 用户认证
     */
    UserIdentityDO createIdentity(UserIdentityDO userIdentity);

    /**
     * 新建用户认证信息 (USERNAME_PASSWORD)
     *
     * @param userId   用户id
     * @param username 用户名
     * @param password 密码
     * @return 用户认证
     */
    UserIdentityDO createUsernamePasswordIdentity(Long userId,
                                                  String username,
                                                  String password);


    /**
     * 验证用户认证信息 (USERNAME_PASSWORD)
     *
     * @param userId   用户id
     * @param username 用户名
     * @param password 密码
     * @return 是否验证成功
     */
    boolean verifyUsernamePassword(Long userId, String username, String password);

    /**
     * 修改密码
     *
     * @param userId   用户id
     * @param password 新密码
     * @return 是否成功
     */
    boolean changePassword(Long userId, String password);

    /**
     * 修改用户名
     *
     * @param userId   用户id
     * @param username 新用户名
     * @return 是否成功
     */
    boolean changeUsername(Long userId, String username);

    /**
     * 修改用户名密码
     *
     * @param userId   用户id
     * @param username 新用户名
     * @param password 新密码
     * @return 是否成功
     */
    boolean changeUsernamePassword(Long userId, String username, String password);
}
