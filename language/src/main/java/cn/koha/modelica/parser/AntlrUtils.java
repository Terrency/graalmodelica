package cn.koha.modelica.parser;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class AntlrUtils {
    public static int getChildIndex(ParseTree child){
        ParseTree parent = child.getParent();
        if(parent == null) return -1;
        for(int i=0; i< parent.getChildCount(); i++){
            if(child == parent.getChild(i)) return i;
        }
        return -1;
    }
    public static List<ParseTree> getPreNodes(ParseTree child){
        ParseTree parent = child.getParent();
        int childIndex = getChildIndex(child);
        List<ParseTree> result = new ArrayList();
        if(parent == null) {
            return result;
        }
        for(int i=0; i< childIndex; i++){
            result.add(parent.getChild(i));
        }
        return result;
    }
    public static ParseTree getPreNode(ParseTree child){
        ParseTree parent = child.getParent();
        int childIndex = getChildIndex(child);
        if(childIndex == 0) return null;
        return parent.getChild(childIndex - 1);
    }
    public static ParseTree getAfterNode(ParseTree child){
        ParseTree parent = child.getParent();
        int childIndex = getChildIndex(child);
        if(childIndex + 1 >= parent.getChildCount()) return null;
        return parent.getChild(childIndex + 1);
    }
    public static TerminalNode getFirstTerminalNodeChild(ParseTree parent, String str){
        int index = getFirstTerminalNodeChildIndex(parent, str);
        if(index == -1) return null;
        return (TerminalNode) parent.getChild(index);
    }
    public static int getFirstTerminalNodeChildIndex(ParseTree parent, String str){
        for(int i=0; i< parent.getChildCount(); i++){
            ParseTree child = parent.getChild(i);
            if(child instanceof TerminalNode && str.equals(child.getText())){
                return i;
            }
        }
        return -1;
    }
    public static List<ParseTree> getAllTerminalNode(ParseTree parent, String str){
        List<ParseTree> result = new ArrayList<>();
        for(int i=0; i< parent.getChildCount(); i++){
            ParseTree child = parent.getChild(i);
            if(child instanceof TerminalNode && str.equals(child.getText())){
                result.add(child);
            }
        }
        return result;
    }
    public static boolean hasTerminalNodeChild(ParseTree parent, String str){
        return getFirstTerminalNodeChildIndex(parent, str) != -1;
    }
    public static <T extends ParseTree> T getParentNode(ParseTree node, Class<T> t){
        ParseTree parent = node.getParent();
        if(parent == null) return null;
        if(parent.getClass() == t) return (T) parent;
        return getParentNode(parent, t);
    }
}