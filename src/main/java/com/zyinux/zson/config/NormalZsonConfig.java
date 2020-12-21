package com.zyinux.zson.config;

/**
 * NormalZsonConfig
 *
 * @author zyinux
 * @Desc
 * @date 2020/12/20
 */
public class NormalZsonConfig implements ZsonConfig{

    @Override
    public boolean ignoreKeyCase() {
        return false;
    }


}
