package org.karsha.controler;

import org.karsha.data.FiboRelationDB;
import org.karsha.entities.FiboRelation;

import java.io.Serializable;
import java.util.*;

public class MappedTreeStructure <N extends Serializable> implements MutableTree<N> {

    private final Map<N, N> nodeParent = new HashMap<N, N>();
    private final LinkedHashSet<N> nodeList = new LinkedHashSet<N>();

    public MutableTree setFiboTree () {

        MutableTree<String> tree = new MappedTreeStructure<String>();
        ArrayList<FiboRelation> fiboRelationList= FiboRelationDB.getAllFiboRelations();
        for(int i=0;i<fiboRelationList.size();i++){
            tree.add(""+fiboRelationList.get(i).getFiboId(),""+fiboRelationList.get(i).getChildFiboId());
        }
        return tree;
    }
    public Map getNodeParent(){
        return nodeParent;
    }
    public LinkedHashSet getNodeList(){
        return nodeList;
    }

    @Override
    public boolean add (N parent, N node) {

        boolean added = nodeList.add(node);
        nodeList.add(parent);
        if (added) {
            nodeParent.put(node, parent);
        }
        return added;
    }

    @Override
    public boolean remove (N node, boolean cascade) {
        if (!nodeList.contains(node)) {
            return false;
        }
        if (cascade) {
            for (N child : getChildren(node)) {
                remove(child, true);
            }
        } else {
            for (N child : getChildren(node)) {
                nodeParent.remove(child);
            }
        }
        nodeList.remove(node);
        return true;
    }

    @Override
    public List<N> getRoots () {
        return getChildren(null);
    }

    @Override
    public N getParent (N node) {
        return nodeParent.get(node);
    }

    @Override
    public List<N> getChildren (N node) {
        List<N> children = new LinkedList<N>();
        for (N n : nodeList) {
            N parent = nodeParent.get(n);
            if (node == null && parent == null) {
                children.add(n);
            } else if (node != null && parent != null && parent.equals(node)) {
                children.add(n);
            }
        }
        return children;
    }

    @Override
    public String toString () {
        StringBuilder builder = new StringBuilder();
        dumpNodeStructure(builder, null, "- ");
        return builder.toString();
    }

    private void dumpNodeStructure (StringBuilder builder, N node, String prefix) {
        if (node != null) {
            builder.append(prefix);
            builder.append(node.toString());
            builder.append('\n');
            prefix = "    " + prefix;
        }
        for (N child : getChildren(node)) {
            dumpNodeStructure(builder, child, prefix);
        }
    }
}
interface Tree <N extends Serializable> extends Serializable {
    public List<N> getRoots ();
    public N getParent (N node);
    public List<N> getChildren (N node);
}

interface MutableTree <N extends Serializable> extends Tree<N> {
    public boolean add (N parent, N node);
    public boolean remove (N node, boolean cascade);
}