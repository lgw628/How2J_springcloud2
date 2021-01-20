package qinyan.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
实现序列化接口，可以把对象存放字节流，然后可以恢复。便于网路传输和持久化（必须要字节流）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Index implements Serializable {
    String code;
    String name;
}
