package kz.iitu.swd.group34.FirstSpringBootIITU.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service")
    private Service service;

    @ManyToOne
    @JoinColumn(name = "client")
    private Users client;

    @ManyToOne
    @JoinColumn(name = "master")
    private Master master;

    @Column(name = "date")
    private java.util.Date date;

}
