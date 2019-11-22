package com.ist.rc2;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

class gf implements Factory<Graph<Player,Integer>>{

    public Graph<Player,Integer> create(){
        return new UndirectedSparseGraph<Player,Integer>();
    }

}