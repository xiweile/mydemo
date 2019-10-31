package com.weiller.demo.sf.pathback;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: SxTree
 * @Description: TODO
 * @Author: wujiujian
 * @Date: 2019/10/31  10:48
 **/
public class SxTree {

    private Set<String> node;

    private int level;

    private List<SxTree> children;

    private Set<String> parentNode;

    public Set<String> getParentNode() {
        return parentNode;
    }

    public void setParentNode(Set<String> parentNode) {
        this.parentNode = parentNode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public List<SxTree> getChildren() {
        return children;
    }

    public void setChildren(List<SxTree> children) {
        this.children = children;
    }

    public Set<String> getNode() {
        return node;
    }

    public void setNode(Set<String> node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "SxTree{" +
                "node=" + node +
                ", level=" + level +
                ", children=" + children +
                '}';
    }
}
