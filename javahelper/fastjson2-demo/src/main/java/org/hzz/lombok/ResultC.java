package org.hzz.lombok;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResultC {
    private int[] data;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ResultC)) return false;
        final ResultC other = (ResultC) o;
        if (!other.canEqual((Object) this)) return false;
        if (!java.util.Arrays.equals(this.data, other.data)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResultC;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + java.util.Arrays.hashCode(this.data);
        return result;
    }
}
