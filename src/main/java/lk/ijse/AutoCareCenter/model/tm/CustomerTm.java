package lk.ijse.AutoCareCenter.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private String contact;
}