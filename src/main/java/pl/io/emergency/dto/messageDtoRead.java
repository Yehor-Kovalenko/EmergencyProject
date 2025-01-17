package pl.io.emergency.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class messageDtoRead {

    private long receiverId;

    public messageDtoRead() {}

    public messageDtoRead(long receiverId) {
        this.receiverId = receiverId;
    }
}
