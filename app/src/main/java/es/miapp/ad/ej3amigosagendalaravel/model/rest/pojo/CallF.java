package es.miapp.ad.ej3amigosagendalaravel.model.rest.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CallF {
    private long id, idFriend;
    private String callDate;
}
