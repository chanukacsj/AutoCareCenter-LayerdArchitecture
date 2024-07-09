package lk.ijse.AutoCareCenter.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Employee {
    private String id;
    private String name;
    private String address;
    private String contact;
}
