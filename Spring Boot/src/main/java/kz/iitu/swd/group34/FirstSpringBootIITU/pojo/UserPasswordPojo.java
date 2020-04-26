package kz.iitu.swd.group34.FirstSpringBootIITU.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordPojo {

    private Long id;
    private String oldPassword;
    private String newPassword;

}