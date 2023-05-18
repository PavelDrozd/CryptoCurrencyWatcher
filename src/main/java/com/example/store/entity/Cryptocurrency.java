package com.example.store.entity;

import com.example.store.entity.converter.CryptoSymbolConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cryptocurrencies")
public class Cryptocurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @Convert(converter = CryptoSymbolConverter.class)
    @Column(name = "symbol")
    private CryptoSymbol symbol;

    @Column(name = "name")
    private String name;

    @Column(name = "price_usd")
    private Double priceUSD;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cryptocurrency cryptoCurrency = (Cryptocurrency) o;
        return id != null && Objects.equals(id, cryptoCurrency.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
