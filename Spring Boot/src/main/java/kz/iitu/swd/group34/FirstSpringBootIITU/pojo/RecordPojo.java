package kz.iitu.swd.group34.FirstSpringBootIITU.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordPojo {

    private Long record_id;
    private Long service_id;
    private Long client_id;
    private Long master_id;
    private Date date;

}
