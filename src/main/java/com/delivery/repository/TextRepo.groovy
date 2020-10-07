package com.delivery.repository

import com.delivery.entity.Text
import org.springframework.data.jpa.repository.JpaRepository

interface TextRepo extends JpaRepository<Text, Long> {

}