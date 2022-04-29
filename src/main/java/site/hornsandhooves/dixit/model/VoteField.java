package site.hornsandhooves.dixit.model;

import lombok.Data;

import java.util.List;

@Data
public class VoteField {
    private List<VoteItem> voteItems;
    private String riddledCardDescription;
}
