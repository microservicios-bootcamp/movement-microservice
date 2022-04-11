package com.demo.app.product.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonPropertyOrder({"id","accountNumber","importe","origen","idcliente","movrestantes","createAt","updateAt"})
@Document(collection = "movement")
@Data
public class Movement extends Audit{

@Id
@Transient
public static final String SEQUENCE_NAME = "users_sequence";
private long id;

@Field(name = "account_number")
@Size(min = 16,max = 16)
private String accountNumber;

@Field(targetType = FieldType.DECIMAL128)
private BigDecimal importe;

private String origen;

@Field(name = "id_cliente")
private String idcliente;

private int movrestantes;
}
