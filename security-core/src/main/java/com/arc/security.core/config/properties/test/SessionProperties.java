package com.arc.security.core.config.properties.test;

/**
 * @Author: JZH
 * @Date: 2018/11/4 22:08
 * @Description:
 */
public class SessionProperties {

    private int maxNum = 1; //同一用户session最大值


    private Boolean maxPrevent = false; //达到最大值后是否阻止后续登陆

    public SessionProperties() {
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public Boolean getMaxPrevent() {
        return maxPrevent;
    }

    public void setMaxPrevent(Boolean maxPrevent) {
        this.maxPrevent = maxPrevent;
    }

    @Override
    public String toString() {
        return "SessionProperties{" +
                "maxNum=" + maxNum +
                ", maxPrevent=" + maxPrevent +
                '}';
    }
}
