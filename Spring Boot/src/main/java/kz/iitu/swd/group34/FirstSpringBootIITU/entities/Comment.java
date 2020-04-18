package kz.iitu.swd.group34.FirstSpringBootIITU.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private Master master;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users author;

    @Column(name = "date")
    private Date date;

}

