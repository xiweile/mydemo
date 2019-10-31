package com.weiller.demo.sf.pathback;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: Demo
 * @Description: TODO
 * @Author: wujiujian
 * @Date: 2019/10/29  10:28
 **/
public class Demo {
    private static boolean isFind = false;
    private static Map<String, HashSet<String>> childMap = null;
    private static String lxStr = "";
    private static Map<String, String> tjMap = new HashMap<String, String>();

    public static void main(String[] args) {

        // 用于记录每个事项的位次总和.
        int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0, g = 0, m = 0;
        // 用于记录每个事项出现的次数
        int aa = 0, bb = 0, cc = 0, dd = 0, ee = 0, ff = 0, gg = 0, mm = 0;
        // 1.去重, 求所有事项并集 A, B, C, D, E, F, G, M
        HashSet<String> unionSet = new LinkedHashSet<>();
        List<String> list = Arrays.asList(
                  "A, B, C, D, E, F, G"
                , "A, C, D, F, E, G"
                , "A, C, B, F, G"
                , "A, D, C, E, G"
                , "A, M, G");
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            String[] strArr = str.split(",");
            for (int j = 0; j < strArr.length; j++) {
                String s = strArr[j].trim();
                unionSet.add(s);
                if (s.equals("A")) {
                    a += j + 1;
                    aa += 1;
                } else if (s.equals("B")) {
                    b += j + 1;
                    bb += 1;
                } else if (s.equals("C")) {
                    c += j + 1;
                    cc += 1;
                } else if (s.equals("D")) {
                    d += j + 1;
                    dd += 1;
                } else if (s.equals("E")) {
                    e += j + 1;
                    ee += 1;
                } else if (s.equals("F")) {
                    f += j + 1;
                    ff += 1;
                } else if (s.equals("G")) {
                    g += j + 1;
                    gg += 1;
                } else if (s.equals("M")) {
                    m += j + 1;
                    mm += 1;
                }
            }
        }
        System.out.println("所有事项并集:" + unionSet.toString());

        // 2. 求每个事项的位次的平均值
        Double aaa = new BigDecimal(Double.valueOf(a) / aa)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double bbb = new BigDecimal(Double.valueOf(b) / bb)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double ccc = new BigDecimal(Double.valueOf(c) / cc)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double ddd = new BigDecimal(Double.valueOf(d) / dd)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double eee = new BigDecimal(Double.valueOf(e) / ee)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double fff = new BigDecimal(Double.valueOf(f) / ff)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double ggg = new BigDecimal(Double.valueOf(g) / gg)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        Double mmm = new BigDecimal(Double.valueOf(m) / mm)
                .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        // 3. 根据步骤2对事项排序
        Map<String, Double> map = new HashMap<>();
        map.put("A", aaa);
        map.put("B", bbb);
        map.put("C", ccc);
        map.put("D", ddd);
        map.put("E", eee);
        map.put("F", fff);
        map.put("G", ggg);
        map.put("M", mmm);
        System.out.println("排序前:" + map.toString());
        // 排序
        int size = map.size();
        //通过map.entrySet()将map转换为"A=1"形式的list集合
        List<Map.Entry<String, Double>> mapList = new ArrayList<Map.Entry<String, Double>>(size);
        mapList.addAll(map.entrySet());
        List<String> keyList = mapList.stream()
                .sorted(Comparator.comparing(Map.Entry<String, Double>::getValue))
                .map(Map.Entry<String, Double>::getKey)
                .collect(Collectors.toList());

        System.out.println("排序后:" + keyList.toString());

        childMap = new HashMap<String, HashSet<String>>();
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i).trim();
            HashSet<String> childSet = new HashSet<String>();
            if (!"G".equals(key)) {
                for (String s : list) {
                    String[] strArr = s.split(",");
                    for (int j = 0; j < strArr.length; j++) {
                        if (!"G".equals(strArr[j].trim())) {
                            if (key.equals(strArr[j].trim())) {
                                childSet.add(strArr[j + 1].trim());
                            }
                        }
                    }
                }
                childMap.put(key, childSet);
            }
        }
        System.out.println("转换后:" + childMap.toString());

        // 4.对事项序列不断下探,遇到回路之间节点为同级节点,未遇到回路则为下一级节点.
        for (int i = 0; i < keyList.size(); i++) {
            String key = keyList.get(i).trim();
            HashSet<String> firstSet = childMap.get(key);
            if (firstSet == null) {
                break;
            }
            for (String firstStr : firstSet) {
                if (firstStr.equals("G")) {
                    // A->G  起始点直接到截止点, 将A设为第一级
                    break;
                } else {
                    // 递归下探, key作为下一个节点的起始探点
                    if (firstStr.equals("G")) {
                        break;
                    } else {
                        lxStr = "";
                        // 递归
                        dgMethod(firstStr, firstStr);
                    }
                }
            }
        }
        System.out.println("同级关系:" + tjMap);

        SxTree tree = new SxTree();
        Set<String> firstSet = new HashSet<>();
        firstSet.add(keyList.get(0));
        tree.setLevel(1);
        tree.setNode(firstSet);
        tree.setParentNode(new HashSet<>());

        // 5. 寻找上下级关系
        createTree(tree);
        System.out.println("最终结构:" + tree);

    }

    private static void createTree(SxTree tree){
        // 找下一个层级所有元素
        Set<String> nextCj = findNextCj(tree.getNode(), tree.getParentNode(), childMap);
        if(nextCj.isEmpty()){
            return;
        }
        //合并同级元素
        Set<Set<String>> sets = cjHb(nextCj, tjMap);
        // 构建树结构
        List<SxTree> childNodes = new ArrayList<>();
        for (Set<String> node : sets) {
            SxTree childTree = new SxTree();
            childTree.setNode(node);
            childTree.setParentNode(nextCj);
            childTree.setLevel(tree.getLevel()+1);
            childNodes.add(childTree);
        }
        tree.setChildren(childNodes);
        System.out.println("组合节点:" + tree.getNode());
        for(SxTree nextTree:tree.getChildren()){
            createTree(nextTree);
        }

    }


    private static Map<String, String> dgMethod(String firstStr, String td) {
        if (lxStr.indexOf(firstStr) >= 0) {
            return tjMap;
        } else {
            lxStr = lxStr += firstStr;
        }
        HashSet<String> set = childMap.get(firstStr);
        if (set == null) {
            return tjMap;
        }
        for (String secondStr : set) {
            if (secondStr.equals("G")) {
                break;
            } else if (secondStr.equals(td)) {
                // B->C->B型闭合回路, 可以说明B与C同级
                if (lxStr.length() > 1) {
                    tjMap.put(td, lxStr.split("")[1]);
                }
                //break ;
            } else {
                // 递归
                dgMethod(secondStr, td);
            }
        }
        return tjMap;
    }

    private static Set<String> findNextCj(Set<String> parentNode, Set<String> preCjSet, Map<String, HashSet<String>> gxMap) {
        Set<String> cjSet = new HashSet<>();

        for (String cj : parentNode) {
            HashSet<String> nextCjSet = gxMap.get(cj);
            if (nextCjSet!=null&&!nextCjSet.isEmpty()) {
                for (String s : nextCjSet) {
                    if (!preCjSet.contains(s)) {
                        cjSet.add(s);
                    }

                }
            }
        }
        return cjSet;
    }

    private static Set<Set<String>> cjHb(Set<String> cjSet, Map<String, String> tjMap) {
        Set<Set<String>> rSet = new HashSet<>();

        for (String cj : cjSet) {
            if (tjMap.get(cj) != null) {
                String tj2 = tjMap.get(cj);
                Set<String> findSet = null;
                for (Set<String> hbSet : rSet) {
                    if (hbSet.contains(cj) || hbSet.contains(tj2)) {
                        findSet = hbSet;
                        break;
                    }
                }
                if (findSet != null) {
                    findSet.add(cj);
                    findSet.add(tj2);
                } else {
                    Set<String> newSet = new HashSet<>();
                    newSet.add(cj);
                    newSet.add(tj2);
                    rSet.add(newSet);
                }
            } else {
                Set<String> newSet = new HashSet<>();
                newSet.add(cj);
                rSet.add(newSet);
            }

        }
        return rSet;
    }

}
