package org.hzz.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("PROJECT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    private int id;
    private String name;
}
