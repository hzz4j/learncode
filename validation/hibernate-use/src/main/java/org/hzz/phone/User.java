package org.hzz.phone;

public class User {


    private String phonenumber;

    public User() {
    }

    public @MyPhone String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(@MyPhone String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        final User other = (User) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$phonenumber = this.getPhonenumber();
        final Object other$phonenumber = other.getPhonenumber();
        if (this$phonenumber == null ? other$phonenumber != null : !this$phonenumber.equals(other$phonenumber))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof User;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $phonenumber = this.getPhonenumber();
        result = result * PRIME + ($phonenumber == null ? 43 : $phonenumber.hashCode());
        return result;
    }

    public String toString() {
        return "User(phonenumber=" + this.getPhonenumber() + ")";
    }
}
