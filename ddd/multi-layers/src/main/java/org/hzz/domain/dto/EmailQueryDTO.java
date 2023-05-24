package org.hzz.domain.dto;

import javax.validation.constraints.Email;

//@Validated
public class EmailQueryDTO {
    @Email(message = "邮箱格式不正确")
    private String email;

    public EmailQueryDTO() {
    }

    public @Email(message = "邮箱格式不正确") String getEmail() {
        return this.email;
    }

    public void setEmail(@Email(message = "邮箱格式不正确") String email) {
        this.email = email;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EmailQueryDTO)) return false;
        final EmailQueryDTO other = (EmailQueryDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EmailQueryDTO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        return result;
    }

    public String toString() {
        return "EmailQueryDTO(email=" + this.getEmail() + ")";
    }
}
