package org.karsha.entities;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: randula
 * Date: 12/11/13
 * Time: 9:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class FiboRelation implements Serializable {
    private int fiboId;
    private int childFiboId;

    public int getFiboId() {
        return fiboId;
    }

    public void setFiboId(int fiboId) {
        this.fiboId = fiboId;
    }

    public int getChildFiboId() {
        return childFiboId;
    }

    public void setChildFiboId(int childFiboId) {
        this.childFiboId = childFiboId;
    }
}
