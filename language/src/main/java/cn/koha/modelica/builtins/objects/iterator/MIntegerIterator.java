package cn.koha.modelica.builtins.objects.iterator;

public abstract class MIntegerIterator {
    public int index = 0;

    public int getIndex() {
        return index;
    }

    public abstract int next();
    public abstract boolean hasNext();
}
