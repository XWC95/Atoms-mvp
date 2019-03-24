package org.vea.atoms.mvp.commonservice;


/**
 * ================================================
 * 向外提供数据传递接口服务
 * <p>
 * Created by Vea on 2018/8/30.
 * ================================================
 */
public interface IUserService {
    String getAvatarUrl();

    int getId();

    String getLogin();

    void setAvatarUrl(String avatar_url);

    void setId(int id);

    void setLogin(String login);
}
