package com.weiller.demo.sf.treejob;

import lombok.Data;

import java.util.List;

@Data
public class TNode {

    private String id;

    private String title;

    private TNode parent;

    private List<TNode> childrens;


}
