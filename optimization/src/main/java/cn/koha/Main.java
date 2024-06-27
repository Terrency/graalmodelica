package cn.koha;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class Main {
    public static void main(String[] args) {
        // 创建一个无向图对象
        UndirectedSparseGraph<String, String> graph = new UndirectedSparseGraph<>();

        // 添加顶点集合 A 和 B
        String[] verticesA = {"A1", "A2", "A3"};
        String[] verticesB = {"B1", "B2", "B3"};
        for (String vertex : verticesA) {
            graph.addVertex(vertex);
        }
        for (String vertex : verticesB) {
            graph.addVertex(vertex);
        }

        // 添加边，连接顶点集合 A 和 B
        for (String vertexA : verticesA) {
            for (String vertexB : verticesB) {
                String edge = vertexA + "-" + vertexB; // 边的标签可以是任意字符串
                graph.addEdge(edge, vertexA, vertexB);
            }
        }

        // 求解最大匹配

//        HopcroftKarpMaximumCardinalityBipartiteMatching<String, String> matching =
//                new HopcroftKarpMaximumCardinalityBipartiteMatching<>(graph, verticesA, verticesB);
//        matching.compute();

        // 输出最大匹配的边集合
//        System.out.println("Maximum matching edges:");
//        for (Pair<String> edge : matching.getMatching()) {
//            System.out.println(edge.getFirst() + " -- " + edge.getSecond());
//        }
    }
}