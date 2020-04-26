package kz.iitu.swd.group34.FirstSpringBootIITU.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicePojo {

    private Long id;
    private String name;
    private String description;
    private float price;
}
