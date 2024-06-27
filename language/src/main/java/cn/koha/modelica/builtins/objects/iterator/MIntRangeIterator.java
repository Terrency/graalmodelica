package cn.koha.modelica.builtins.objects.iterator;

public final class MIntRangeIterator extends MIntegerIterator {
    private final int start;
    private final int stop;
    private final int step;

    public MIntRangeIterator(int start, int stop, int step) {
        this.start = start;
        this.stop = stop;
        this.step = step;
    }

    @Override
    public int next() {
        return start + (index++) * step;
    }

    @Override
    public boolean hasNext() {
        return start + (index + 1) * step <= stop;
    }

    public int getStart() {
        return start;
    }

    public int getStop() {
        return stop;
    }

    public int getStep() {
        return step;
    }
}
