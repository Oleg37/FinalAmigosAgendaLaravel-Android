package es.miapp.ad.ej3amigosagendalaravel.model.rest.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Friend {
    private long id;
    private String name;
    private String phNumber;
    private String dateOfBirth;
    private int times;
}
