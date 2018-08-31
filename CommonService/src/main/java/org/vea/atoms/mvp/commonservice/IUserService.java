package org.vea.atoms.mvp.commonservice;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * ================================================
 * 向外提供数据传递接口服务
 * <p>
 * Created by Vea on 2018/8/30.
 * ================================================
 */
public interface IUserService extends IProvider {

    String getAvatarUrl();

    int getId();

    String getLogin();

    void setAvatar_url(String avatar_url);

    void setId(int id);

    void setLogin(String login);
}
