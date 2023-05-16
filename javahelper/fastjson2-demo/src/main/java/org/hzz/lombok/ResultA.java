package org.hzz.lombok;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResultA {
    private Object data;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ResultA)) return false;
        final ResultA other = (ResultA) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$data = this.data;
        final Object other$data = other.data;
        if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResultA;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $data = this.data;
        result = result * PRIME + ($data == null ? 43 : $data.hashCode());
        return result;
    }
}
