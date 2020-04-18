package kz.iitu.swd.group34.FirstSpringBootIITU.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordPojo {

    private Long service_id;
    private Long client_id;
    private Long master_id;
    private String date;


    public Long getService_id() {
        return service_id;
    }

    public void setService_id(Long service_id) {
        this.service_id = service_id;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
    }

    public Long getMaster_id() {
        return master_id;
    }

    public void setMaster_id(Long master_id) {
        this.master_id = master_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
