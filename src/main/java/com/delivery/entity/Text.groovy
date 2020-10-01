package com.delivery.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "text_table")
class Text {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    final Long id

    @ManyToOne
    User owner

    String text
}
