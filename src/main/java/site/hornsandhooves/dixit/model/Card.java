package site.hornsandhooves.dixit.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Card {

    private UUID id;
    private String url;

}
