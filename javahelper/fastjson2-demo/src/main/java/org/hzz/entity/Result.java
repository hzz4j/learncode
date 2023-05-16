package org.hzz.entity;

public class Result<T>{
    T data;
    Status status;
    public Result(T t,Status status){
        this.data = t;
        this.status = status;
    }

    public T getData() {
        return this.data;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Result)) return false;
        final Result<?> other = (Result<?>) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$data = this.getData();
        final Object other$data = other.getData();
        if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Result;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $data = this.getData();
        result = result * PRIME + ($data == null ? 43 : $data.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        return result;
    }

    public String toString() {
        return "Result(data=" + this.getData() + ", status=" + this.getStatus() + ")";
    }
}
