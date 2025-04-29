package com.example.skinsMonkeyComparator.Items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<ItemBought,String> {

    List<ItemBought> findAllByOrderByIdDesc(); //getting boughtItems from database
}
