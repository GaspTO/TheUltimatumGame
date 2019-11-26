package com.ist.rc2;

import org.apache.commons.collections15.Factory;

class EdgeFactory implements Factory<Integer>{
    int count = 0;
    public Integer create(){
        return count++;
    }

}