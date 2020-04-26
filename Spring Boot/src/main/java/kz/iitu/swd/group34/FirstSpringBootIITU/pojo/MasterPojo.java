package kz.iitu.swd.group34.FirstSpringBootIITU.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterPojo {

    private Long id;
    private String name;
    private String phone;
    private List<Long> services;
}
