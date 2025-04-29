package com.example.skinsMonkeyComparator.Items;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.sql.ast.tree.expression.Format;

import java.time.LocalDate;
import java.util.Formatter;

@Entity
@Table(name="history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemBought extends Item{

    @Id
    private int id;
    private int quantity;
    @JsonProperty("buy_date")
    private String buy_date;

    public ItemBought(int quantity,String buy_date) {
        this.quantity = quantity;
        this.buy_date = buy_date;
    }
}
