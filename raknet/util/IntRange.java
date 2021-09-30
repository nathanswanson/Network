package network.raknet.util;

import network.common.util.Preconditions;

public class IntRange {
    public int start;
    public int end;

    public IntRange(int num) {
        this(num, num);
    }

    public IntRange(int start, int end) {
        Preconditions.checkArgument(start <= end, "start is greater than end");
        this.start = start;
        this.end = end;
    }
}